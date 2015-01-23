package logic.gracenote;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import radams.gracenote.webapi.GracenoteException;
import radams.gracenote.webapi.GracenoteMetadata;
import radams.gracenote.webapi.GracenoteWebAPI;

public class Gracenote {
   private static final String
         GRACENOTE_USER_ID_FILENAME = "GracenoteUserId",
         GRACENOTE_CLIENT_ID = "13028352-D385447287B9C9C716F9784EA66CE3FD",
         GRACENOTE_CLIENT_TAG = "D385447287B9C9C716F9784EA66CE3FD";

   public GracenoteMetadata searchArtist(String q) {
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
         
         System.out.println("Searching for artist '" + q + "' on Gracenote...");
         GracenoteMetadata results = gn.searchArtist(q);
         System.out.println("RESULTS:");
         results.print();
         return results;
      } catch (GracenoteException | IOException e) {
         e.printStackTrace();
      }
      
      return null;
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
}
