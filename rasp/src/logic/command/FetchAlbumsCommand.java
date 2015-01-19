package logic.command;

import java.io.IOException;
import java.sql.SQLException;

import config.Config;
import model.Model;
import util.function.Command;
import util.sql.DataAccess;
import util.sql.DefaultDataAccess;
import data.DataAccessConnection;
import data.sql.AlbumQueries;
import data.sql.ArtistQueries;
import data.sql.TrackQueries;
import domain.AlbumEntity;
import domain.ArtistEntity;
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
      
      for (AlbumEntity a : model.getAlbums()) {
         a.setAlbumArtist(model.getArtistByKey(a.getAlbumArtistKey()));
         a.setTrackList(trackDao.listAll(TrackQueries.list(a.getKey())));
         
         for (TrackEntity t : a.getTrackList())
            t.setPrimaryArtist(model.getArtistByKey(t.getPrimaryArtistKey()));
      }
   }
}
