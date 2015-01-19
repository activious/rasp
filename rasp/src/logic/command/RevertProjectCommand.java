package logic.command;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;

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

public class RevertProjectCommand implements Command {
   private final DataAccessConnection dac;

   public RevertProjectCommand(String projectName) throws IOException, SQLException {
      // Delete database if exists
      Files.deleteIfExists(Paths.get(projectName + ".db"));
      // Create new project
      new CreateProjectCommand(projectName).execute();
      // Get connection
      dac = new DataAccessConnection(projectName);
   }

   @Override
   public void execute() throws SQLException {
      try {
         addData();
         dac.commit();
      } catch (SQLException e) {
         dac.rollback();
         throw e;
      } finally {
         dac.close();
      }
   }

   private void addData() throws SQLException {
      DataAccess<ArtistEntity, Integer> artistDao =
            new DefaultDataAccess<ArtistEntity, Integer>(dac);
      
      DataAccess<AlbumEntity, Integer> albumDao =
            new DefaultDataAccess<AlbumEntity, Integer>(dac);
      
      DataAccess<TrackEntity, Integer> trackDao =
            new DefaultDataAccess<TrackEntity, Integer>(dac);
      
      AlbumEntity album = createAlbumByTool();
      artistDao.create(album.getAlbumArtist(), ArtistQueries.create());
      albumDao.create(album, AlbumQueries.create());
      trackDao.createAll(album.getTrackList(), TrackQueries.create(album.getKey()));
   }
   
   private AlbumEntity createAlbumByTool() {
      ArtistEntity artist = createArtist("Tool");
      AlbumEntity album = createAlbum("10,000 Days", artist);
      
      album.addTrack(createTrack(1, "Vicarious", 411, artist));
      album.addTrack(createTrack(2, "Jambi", 411, artist));
      album.addTrack(createTrack(3, "Wings for Marie (Part 1)", 411, artist));
      album.addTrack(createTrack(4, "10,000 Days (Wings Part 2)", 411, artist));
      album.addTrack(createTrack(5, "The Pot", 411, artist));
      album.addTrack(createTrack(6, "Lipan Conjuring", 411, artist));
      album.addTrack(createTrack(7, "Lost Keys (Blame Hofmann)", 411, artist));
      album.addTrack(createTrack(8, "Rosetta Stoned", 411, artist));
      album.addTrack(createTrack(9, "Intension", 411, artist));
      album.addTrack(createTrack(10, "Right in Two", 411, artist));
      album.addTrack(createTrack(11, "Viginti Tres", 411, artist));
      
      return album;
   }
   
   private ArtistEntity createArtist(String name) {
      ArtistEntity a = new ArtistEntity();
      a.setName(name);
      return a;
   }
   
   private AlbumEntity createAlbum(String title, ArtistEntity artist) {
      AlbumEntity a = new AlbumEntity();
      a.setTitle(title);
      a.setAlbumArtist(artist);
      return a;
   }
   
   private TrackEntity createTrack(int number, String title, int duration, ArtistEntity artist) {
      TrackEntity t = new TrackEntity();
      t.setNumber(number);
      t.setTitle(title);
      t.setDuration(duration);
      t.setPrimaryArtist(artist);
      return t;
   }
}
