package logic.command;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;

import config.Config;
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

public class SaveAlbumCommand implements Command {
   private final DataAccessConnection dac;
   
   private final AlbumEntity album;

   public SaveAlbumCommand(AlbumEntity album) throws IOException, SQLException {
      this.album = album;
      
      // Get connection
      dac = new DataAccessConnection(Config.PROJECT_NAME);
   }

   @Override
   public void execute() throws SQLException {
      try {
         saveAlbum();
         dac.commit();
      } catch (SQLException e) {
         dac.rollback();
         throw e;
      } finally {
         dac.close();
      }
   }

   private void saveAlbum() throws SQLException {
      DataAccess<ArtistEntity, Integer> artistDao =
            new DefaultDataAccess<ArtistEntity, Integer>(dac);
      
      DataAccess<AlbumEntity, Integer> albumDao =
            new DefaultDataAccess<AlbumEntity, Integer>(dac);
      
      DataAccess<TrackEntity, Integer> trackDao =
            new DefaultDataAccess<TrackEntity, Integer>(dac);
      
      ArtistEntity albumArtist = album.getAlbumArtist();
      if (albumArtist.isPersisted()) {
         artistDao.update(albumArtist, ArtistQueries.update());
      } else {
         artistDao.create(albumArtist, ArtistQueries.create());
      }
      
      if (album.getAlbumArtist() != null) {
         for (TrackEntity t : album.getTrackList()) {
            if (t.getPrimaryArtist() == null)
               t.setPrimaryArtist(album.getAlbumArtist());
         }
      }
      
      if (album.isPersisted()) {
         albumDao.update(album, AlbumQueries.update());
         trackDao.updateAll(album.getTrackList(), TrackQueries.update());
      } else {
         albumDao.create(album, AlbumQueries.create());
         trackDao.createAll(album.getTrackList(), TrackQueries.create(album.getKey()));
      }
   }
}