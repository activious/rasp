package logic.command;

import java.io.IOException;
import java.sql.SQLException;

import util.function.Command;
import util.sql.DataAccess;
import util.sql.DefaultDataAccess;
import data.DataAccessConnection;
import domain.ArtistEntity;

public class AddArtistCommand implements Command {
   private final DataAccessConnection dac;

   private final ArtistEntity artist;

   public AddArtistCommand(String projectName, ArtistEntity artist) throws IOException {
      dac = new DataAccessConnection(projectName);
      this.artist = artist;
   }

   @Override
   public void execute() throws SQLException {
      try {
         addArtist();
         dac.commit();
      } catch (SQLException e) {
         dac.rollback();
         throw e;
      } finally {
         dac.close();
      }
   }

   private void addArtist() throws SQLException {
      String sql = "INSERT INTO artist (artistname) VALUES (?)";
      
      DataAccess<ArtistEntity, Integer> dao =
            new DefaultDataAccess<ArtistEntity, Integer>(dac);

      dao.create(artist, sql,
            (s, t) -> {
               try {
                  s.setString(1, t.getName());
               } catch (SQLException e) {
                  e.printStackTrace();
               }
            },
            (r, t) -> {
               t.setKey(r.getInt(1));
               return t.getKey();
            });
   }
}
