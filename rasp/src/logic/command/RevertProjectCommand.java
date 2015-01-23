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
      albums.add(createAlbum1());
      albums.add(createAlbum2());
      albums.add(createAlbum3());
      albums.add(createAlbum4());
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
   
   private AlbumEntity createAlbum1() {
     DurationParser dp = new DurationParser();
     
     ArtistEntity artist = createArtist("Jan Vermeulen");
     ArtistEntity composer = createArtist("Franz Peter Schubert");
     AlbumEntity a = createAlbum("Works for Fortepiano", artist);
     
     a.addTrack(createTrack(1, "Allegro", dp.parse("  11:40"), artist, composer));
     a.addTrack(createTrack(2, "Adagio", dp.parse("08:02"), artist, composer));
     a.addTrack(createTrack(3, "Menuetto - Allegro", dp.parse("03:04"), artist, composer));
     a.addTrack(createTrack(4, "Allegro", dp.parse("09:49"), artist, composer));
     a.addTrack(createTrack(5, "Allegretto in B flat major", dp.parse("04:51"), artist, composer));
     a.addTrack(createTrack(6, "Allegretto moderato in D flat major", dp.parse("05:15"), artist, composer));
     a.addTrack(createTrack(7, "Moderato", dp.parse("01:26"), artist, composer));
     a.addTrack(createTrack(8, "Andante", dp.parse("10:05"), artist, composer));
     a.addTrack(createTrack(9, "Allegro", dp.parse("15:19"), artist, composer));
     a.addTrack(createTrack(10, "Andantino", dp.parse("08:22"), artist, composer));
     
     return a;
  }
   
   private AlbumEntity createAlbum2() {
     DurationParser dp = new DurationParser();
     
     ArtistEntity artist = createArtist("Kim Larsen");
     AlbumEntity a = createAlbum("Værsgo", artist);
     
     a.addTrack(createTrack(1, "Nanna", dp.parse("1:50"), artist));
     a.addTrack(createTrack(2, "Hubertus", dp.parse("3:08"), artist));
     a.addTrack(createTrack(3, "Joanna", dp.parse("2:52"), artist));
     a.addTrack(createTrack(4, "Det er i dag et vejr", dp.parse("2:08"), artist));
     a.addTrack(createTrack(5, "Byens hotel", dp.parse("2:51"), artist));
     a.addTrack(createTrack(6, "Det rager mig en bønne", dp.parse("0:58"), artist));
     a.addTrack(createTrack(7, "Hvis din far gir dig lov", dp.parse("3:21"), artist));
     a.addTrack(createTrack(8, "Guleroden", dp.parse("4:08"), artist));
     a.addTrack(createTrack(9, "Maria", dp.parse("1:50"), artist));
     a.addTrack(createTrack(10, "Er du Jol Mon!", dp.parse("2:08"), artist));
     a.addTrack(createTrack(11, "Den rige og den fattige pige", dp.parse("2:57"), artist));
     a.addTrack(createTrack(12, "De Fjorten Astronauter", dp.parse("2:19"), artist));
     a.addTrack(createTrack(13, "På en gren I Vort Kvarter", dp.parse("2:40"), artist));
     a.addTrack(createTrack(14, "Jacob Den Glade", dp.parse("3:34"), artist));
     a.addTrack(createTrack(15, "Christianshavn Kanal", dp.parse("2:08"), artist));
     
     
     
     
     return a;
  }
   
   private AlbumEntity createAlbum3() {
     DurationParser dp = new DurationParser();
     
     ArtistEntity artist = createArtist("Niels Hausgaard");
     AlbumEntity a = createAlbum("Værsgo", artist);
     
     a.addTrack(createTrack(1, "Som Grise", dp.parse("2:45"), artist));
     a.addTrack(createTrack(2, "Kyklikyy", dp.parse("3:11"), artist));
     a.addTrack(createTrack(3, "Sommerflirt", dp.parse("3:25"), artist));
     a.addTrack(createTrack(4, "Blå Bog", dp.parse("3:03"), artist));
     a.addTrack(createTrack(5, "Egon", dp.parse("4:28"), artist));
     a.addTrack(createTrack(6, "Far", dp.parse("4:28"), artist));
     a.addTrack(createTrack(7, "Godt De Er Gift", dp.parse("2:49"), artist));
     a.addTrack(createTrack(8, "Habitter", dp.parse("3:25"), artist));
     a.addTrack(createTrack(9, "Gammel Lene", dp.parse("4:49"), artist));
     a.addTrack(createTrack(10, "Bette Mørch Hansen", dp.parse("3:26"), artist));
     a.addTrack(createTrack(11, "Che Guevara", dp.parse("3:31"), artist));
     a.addTrack(createTrack(12, "Min Brors Kæreste", dp.parse("2:36"), artist));
     a.addTrack(createTrack(13, "Jeg Trækker Det Tilbage", dp.parse("3:12"), artist));
     a.addTrack(createTrack(14, "Undskyld", dp.parse("4:46"), artist));
     a.addTrack(createTrack(15, "Bette Annie", dp.parse("4:11"), artist));
     
     
     return a;
  }
   
   private AlbumEntity createAlbum4() {
     DurationParser dp = new DurationParser();
     
     ArtistEntity artist = createArtist("P.O.D");
     AlbumEntity a = createAlbum("Satellite", artist);
     
     a.addTrack(createTrack(1, "Set It Off", dp.parse("4:16"), artist));
     a.addTrack(createTrack(2, "Alive", dp.parse("3:23"), artist));
     a.addTrack(createTrack(3, "Broom", dp.parse("3:08"), artist));
     a.addTrack(createTrack(4, "Youth of the Nation", dp.parse("3:03"), artist));
     a.addTrack(createTrack(5, "Celestial", dp.parse("4:19"), artist));
     a.addTrack(createTrack(6, "Satellite", dp.parse("1:24"), artist));
     a.addTrack(createTrack(7, "Ridiculous", dp.parse("3:30"), artist));
     a.addTrack(createTrack(8, "The Messenjah", dp.parse("4:17"), artist));
     a.addTrack(createTrack(9, "Guitarras de Amor", dp.parse("4:19"), artist));
     a.addTrack(createTrack(10, "Anything Right", dp.parse("1:14"), artist));
     a.addTrack(createTrack(11, "Ghetto", dp.parse("4:17"), artist));
     a.addTrack(createTrack(12, "Masterpiece Conspiracy", dp.parse("3:37"), artist));
     a.addTrack(createTrack(13, "Without Jah, Nothin", dp.parse("3:11"), artist));
     a.addTrack(createTrack(14, "Thinking About Forever", dp.parse("3:46"), artist));
     a.addTrack(createTrack(15, "Portrait", dp.parse("4:32"), artist));
     
     
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
     
     
     return createTrack(number, title, duration, artist, null);
     
   }
   
   private TrackEntity createTrack(int number, String title, int duration, ArtistEntity artist, ArtistEntity composer) {
     TrackEntity t = new TrackEntity();
     t.setNumber(number);
     t.setTitle(title);
     t.setDuration(duration);
     t.setPrimaryArtist(artist);
    if (composer != null)
        t.addComposer( composer );
     return t;
    
  }
   
}
