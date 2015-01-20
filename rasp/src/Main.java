import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import config.Config;
import radams.gracenote.webapi.GracenoteException;
import radams.gracenote.webapi.GracenoteMetadata;
import radams.gracenote.webapi.GracenoteWebAPI;
import ui.Window;
import ui.album.AlbumDialog;
import logic.command.CreateAlbumCommand;
import logic.command.CreateProjectCommand;
import logic.command.ExitCommand;
import logic.command.FetchArtistsCommand;
import model.Model;
import domain.AlbumEntity;
import domain.ArtistEntity;
import domain.TrackEntity;

public class Main {
   private static final String
         GRACENOTE_USER_ID_FILENAME = "GracenoteUserId",
         GRACENOTE_CLIENT_ID = "13028352-D385447287B9C9C716F9784EA66CE3FD",
         GRACENOTE_CLIENT_TAG = "D385447287B9C9C716F9784EA66CE3FD";

   public static void main(String[] args) {
      try {
         UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      } catch (ClassNotFoundException |
               InstantiationException |
               IllegalAccessException |
               UnsupportedLookAndFeelException e) {
         e.printStackTrace();
      }
      
      //testDatabase();
      //testGracenote();
      testWindow();
   }

   private static void testGracenote() {
      System.out.println("*** TESTING GRACENOTE ***");
      
      Path uidFile = Paths.get(GRACENOTE_USER_ID_FILENAME);
      Charset uidCharset = Charset.forName("US-ASCII");

      try {
         GracenoteWebAPI gn;

         // Read uid from uidFile or register and save a new uid
         // if uidFile does not exist.
         String uid;
         if (Files.notExists(uidFile)) {
            System.out.println("Registering for Gracenote UID...");
            gn = new GracenoteWebAPI(GRACENOTE_CLIENT_ID, GRACENOTE_CLIENT_TAG);
            uid = gn.register();
            System.out.println("Gracenote UID assigned: " + uid);
            writeToFile(uidFile, uid, uidCharset);
         } else {
            uid = readFromFile(uidFile, uidCharset);
         }
         
         gn = new GracenoteWebAPI(GRACENOTE_CLIENT_ID, GRACENOTE_CLIENT_TAG, uid);
         
         // Search for Black albums
         String q = "dizzy mizz";
         System.out.println("Searching for albums containing tracks by artists whose names contain '" + q + "'...");
         GracenoteMetadata results = gn.searchArtist(q);
         System.out.println("RESULTS:");
         for (Map<String, Object> album : results.getAlbums()) {
            System.out.println(album.get("album_title") + " by " + album.get("album_artist_name"));
         }
         //System.out.println("RAW RESULTS:");
         //results.print();
      } catch (GracenoteException | IOException e) {
         e.printStackTrace();
      }
   }

   private static void testDatabase() {
      System.out.println("*** TESTING DATABASE ***");

      try {
         // Create project db file if it does not exist
         if (Files.notExists(Paths.get(Config.PROJECT_NAME + ".db")))
            new CreateProjectCommand(Config.PROJECT_NAME).execute();

         // Add artists
         // ArtistEntity artist = new ArtistEntity();
         // artist.setName("Kaj Ove");
         // new AddArtistCommand(PROJECT, artist).execute();

         // List all artists
         List<ArtistEntity> artists = new ArrayList<>();
         new FetchArtistsCommand(Config.PROJECT_NAME, artists).execute();

         System.out.println("ARTISTS:");
         for (ArtistEntity artist : artists)
            System.out.println(artist.getName());
      } catch (SQLException | IOException e) {
         e.printStackTrace();
      }
   }
   
   private static String readFromFile(Path file, Charset charset) throws IOException {
      StringBuilder sb = new StringBuilder();
      try (BufferedReader in = Files.newBufferedReader(file, charset)) {
         String line;
         while ((line = in.readLine()) != null) {
            sb.append(line);
         }
      }
      return sb.toString();
   }
   
   private static void writeToFile(Path file, String data, Charset charset) throws IOException {
      try (BufferedWriter out = Files.newBufferedWriter(file, charset)) {
         out.write(data);
      }
   }
   
   private static void testWindow() {
      Window win = Window.getInstance();
      win.setVisible(true);
      win.updateAlbumList();
   }
}
