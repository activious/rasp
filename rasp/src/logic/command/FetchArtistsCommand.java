package logic.command;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.function.Command;
import util.sql.DataAccess;
import util.sql.DefaultDataAccess;
import util.sql.DefaultItemCreator;
import util.sql.ItemCreator;
import data.DataAccessConnection;
import domain.ArtistEntity;

public class FetchArtistsCommand implements Command {
   private final DataAccessConnection dac;

   private final List<ArtistEntity> list;

   public FetchArtistsCommand(String projectName, List<ArtistEntity> list) throws IOException {
      dac = new DataAccessConnection(projectName);
      this.list = list;
   }

   @Override
   public void execute() throws SQLException {
      try {
         fetchArtists();
         // dac.commit();
      } catch (SQLException e) {
         // dac.rollback();
         throw e;
      } finally {
         dac.close();
      }
   }

   private void fetchArtists() throws SQLException {
      String sql = "SELECT * FROM artist";

      DataAccess<ArtistEntity, Integer> dao =
            new DefaultDataAccess<ArtistEntity, Integer>(dac);

      Map<String, String> map = new HashMap<>();
      map.put("artistname", "name");

      ItemCreator<ArtistEntity> creator =
            new DefaultItemCreator<>(ArtistEntity.class, map);
      list.addAll(dao.listAll(sql, creator));
      
//      list.addAll(dao.listAll(sql, r -> {
//         ArtistEntity t = new ArtistEntity();
//         t.setKey(r.getInt("artistid"));
//         t.setName(r.getString("artistname"));
//         return t;
//      }));
   }
}
