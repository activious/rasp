package data.sql;

import java.util.HashMap;

import domain.ArtistEntity;
import domain.GenreEntity;
import util.sql.DefaultItemCreator;
import util.sql.KeyQuery;
import util.sql.Query;
import util.sql.ResultQuery;

public class GenreQueries {
   private static HashMap<String, String> map;
   
   static {
      map = new HashMap<>();
      map.put("genreid", "key");
      map.put("genrename", "name");
   }
   
   public static KeyQuery<Integer, GenreEntity> create() {
      return new KeyQuery<>(
            "INSERT INTO genre (genrename) VALUES (?)",
            (stmt, ent) -> {
               stmt.setString(1, ent.getName());
            },
            (rs, ent) -> {
               ent.setKey(rs.getInt(1));
               return ent.getKey();
            });
   }
   
   public static ResultQuery<GenreEntity, Integer> read() {
      return new ResultQuery<>(
            "SELECT * FROM genre WHERE genreid = ?",
            (stmt, key) -> {
               stmt.setInt(1, key);
            },
            new DefaultItemCreator<GenreEntity>(GenreEntity.class, map));
   }
   
   public static ResultQuery<GenreEntity, Integer> list() {
      return new ResultQuery<>(
            "SELECT * FROM genre",
            new DefaultItemCreator<GenreEntity>(GenreEntity.class, map));
   }
   
   public static Query<GenreEntity> update() {
      return new Query<>(
            "UPDATE genre SET genrename = ? WHERE genreid = ?",
            (stmt, ent) -> {
               stmt.setString(1, ent.getName());
               stmt.setInt(2, ent.getKey());
            });
   }
   
   public static Query<Integer> delete() {
      return new Query<>(
            "DELETE FROM genre WHERE genreid = ?",
            (stmt, key) -> {
               stmt.setInt(1, key);
            });
   }
}
