package logic.command;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import config.Config;
import util.function.Command;
import util.sql.DataAccess;
import util.sql.DefaultDataAccess;
import util.sql.ItemCreator;
import util.sql.StatementPreparer;
import data.DataAccessConnection;
import data.sql.AlbumQueries;
import data.sql.ArtistQueries;
import data.sql.TrackArtistQueries;
import data.sql.TrackQueries;
import domain.AlbumEntity;
import domain.ArtistEntity;
import domain.Model;
import domain.TrackEntity;

public class FetchAlbumsCommand implements Command {
   private final DataAccessConnection dac;

   public FetchAlbumsCommand() throws IOException {
      // Get connection
      dac = new DataAccessConnection(Config.PROJECT_NAME);
   }

   @Override
   public void execute() throws SQLException {
      try {
         fetchAlbums();
         dac.commit();
      } catch (SQLException e) {
         dac.rollback();
         throw e;
      } finally {
         dac.close();
      }
   }

   private void fetchAlbums() throws SQLException {
      DataAccess<ArtistEntity, Integer> artistDao =
            new DefaultDataAccess<ArtistEntity, Integer>(dac);

      DataAccess<AlbumEntity, Integer> albumDao =
            new DefaultDataAccess<AlbumEntity, Integer>(dac);

      DataAccess<TrackEntity, Integer> trackDao =
            new DefaultDataAccess<TrackEntity, Integer>(dac);

      Model model = Model.getInstance();

      model.setArtists(artistDao.listAll(ArtistQueries.list()));
      model.setAlbums(albumDao.listAll(AlbumQueries.list()));
      
      // ArtistEntity creator fetching entity from model
      ItemCreator<ArtistEntity> creator = rs -> {
         return model.getArtistByKey(rs.getInt(2));
      };

      for (AlbumEntity a : model.getAlbums()) {
         a.setAlbumArtist(model.getArtistByKey(a.getAlbumArtistKey()));
         a.setTrackList(trackDao.listAll(TrackQueries.list(a.getKey())));

         for (TrackEntity t : a.getTrackList()) {
            t.setPrimaryArtist(model.getArtistByKey(t.getPrimaryArtistKey()));

            StatementPreparer<Void> prep = (s, v) -> s.setInt(1, t.getKey());

            // Set guest artists
            t.setGuestArtists(
                  DefaultDataAccess.executeSelect(TrackArtistQueries.getSelectSQL("guestartist"),
                                                  dac, prep, creator));
            // Set composers
            t.setComposers(
                  DefaultDataAccess.executeSelect(TrackArtistQueries.getSelectSQL("composer"),
                                                  dac, prep, creator));
            // Set performers
            t.setPerformers(
                  DefaultDataAccess.executeSelect(TrackArtistQueries.getSelectSQL("performer"),
                                                  dac, prep, creator));
         }
      }
   }
}
