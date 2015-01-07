import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import domain.ArtistEntity;
import logic.command.AddArtistCommand;
import logic.command.CreateProjectCommand;
import logic.command.ExitCommand;
import logic.command.FetchArtistsCommand;

public class Main {
   private static final String PROJECT = "raspv1";

   public static void main(String[] args) {
      System.out.println("Hello, World! From RASP");

      try {
         // Create project db file if it does not exist
         if (Files.notExists(Paths.get(PROJECT + ".db")))
            new CreateProjectCommand(PROJECT).execute();

         // Add artists
//         ArtistEntity artist = new ArtistEntity();
//         artist.setName("Kaj Ove");
//         new AddArtistCommand(PROJECT, artist).execute();
         
         // List all artists
         List<ArtistEntity> artists = new ArrayList<>();
         new FetchArtistsCommand(PROJECT, artists).execute();
         
         System.out.println("ARTISTS:");
         for (ArtistEntity artist : artists)
            System.out.println(artist.getName());
      } catch (SQLException | IOException e) {
         e.printStackTrace();
      }

      new ExitCommand().execute();
   }
}
