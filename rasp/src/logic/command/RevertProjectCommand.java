package logic.command;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import logic.parser.DurationParser;
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
      
      List<AlbumEntity> albums = new ArrayList<>();
      albums.add(createAlbumByTool());
      albums.add(createAlbumByMetallica());
      for (AlbumEntity a : albums) {
         artistDao.create(a.getAlbumArtist(), ArtistQueries.create());
         albumDao.create(a, AlbumQueries.create());
         trackDao.createAll(a.getTrackList(), TrackQueries.create(a.getKey()));
      }
   }
   
   private AlbumEntity createAlbumByTool() {
      DurationParser dp = new DurationParser();
      
      ArtistEntity artist = createArtist("Tool");
      AlbumEntity a = createAlbum("10,000 Days", artist);
      
      a.addTrack(createTrack(1, "Vicarious", dp.parse("7:06"), artist));
      a.addTrack(createTrack(2, "Jambi", dp.parse("7:28"), artist));
      a.addTrack(createTrack(3, "Wings for Marie (Part 1)", dp.parse("6:11"), artist));
      a.addTrack(createTrack(4, "10,000 Days (Wings Part 2)", dp.parse("11:14"), artist));
      a.addTrack(createTrack(5, "The Pot", dp.parse("6:22"), artist));
      a.addTrack(createTrack(6, "Lipan Conjuring", dp.parse("1:11"), artist));
      a.addTrack(createTrack(7, "Lost Keys (Blame Hofmann)", dp.parse("3:46"), artist));
      a.addTrack(createTrack(8, "Rosetta Stoned", dp.parse("11:11"), artist));
      a.addTrack(createTrack(9, "Intension", dp.parse("7:21"), artist));
      a.addTrack(createTrack(10, "Right in Two", dp.parse("8:55"), artist));
      a.addTrack(createTrack(11, "Viginti Tres", dp.parse("5:02"), artist));
      
      return a;
   }
   
   private AlbumEntity createAlbumByMetallica() {
      DurationParser dp = new DurationParser();
      
      ArtistEntity artist = createArtist("Metallica");
      AlbumEntity a = createAlbum("The Black Album", artist);
      
      a.addTrack(createTrack(1, "Enter Sandman", dp.parse("5:29"), artist));
      a.addTrack(createTrack(2, "Sad but True", dp.parse("5:24"), artist));
      a.addTrack(createTrack(3, "Holier Than Thou", dp.parse("3:47"), artist));
      a.addTrack(createTrack(4, "The Unforgiven", dp.parse("6:26"), artist));
      a.addTrack(createTrack(5, "Wherever I May Roam", dp.parse("6:42"), artist));
      a.addTrack(createTrack(6, "Don't Tread on Me", dp.parse("3:59"), artist));
      a.addTrack(createTrack(7, "Through the Never", dp.parse("4:01"), artist));
      a.addTrack(createTrack(8, "Nothing Else Matters", dp.parse("6:29"), artist));
      a.addTrack(createTrack(9, "Of Wolf and Man", dp.parse("4:16"), artist));
      a.addTrack(createTrack(10, "The God That Failed", dp.parse("5:05"), artist));
      a.addTrack(createTrack(11, "My Friend of Misery", dp.parse("6:47"), artist));
      a.addTrack(createTrack(12, "The Struggle Within", dp.parse("3:51"), artist));
      
      return a;
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
