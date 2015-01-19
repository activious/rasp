package data.sql;

import java.util.HashMap;

import domain.ArtistEntity;
import util.sql.DefaultItemCreator;
import util.sql.KeyQuery;
import util.sql.Query;
import util.sql.ResultQuery;

public class ArtistQueries {
   private static HashMap<String, String> map;
   
   static {
      map = new HashMap<>();
      map.put("artistid", "key");
      map.put("artistname", "name");
   }
   
   public static KeyQuery<Integer, ArtistEntity> create() {
      return new KeyQuery<>(
            "INSERT INTO artist (artistname) VALUES (?)",
            (stmt, ent) -> {
               stmt.setString(1, ent.getName());
            },
            (rs, ent) -> {
               ent.setKey(rs.getInt(1));
               return ent.getKey();
            });
   }
   
   public static ResultQuery<ArtistEntity, Integer> read() {
      return new ResultQuery<>(
            "SELECT * FROM artist WHERE artistid = ?",
            (stmt, key) -> {
               stmt.setInt(1, key);
            },
            new DefaultItemCreator<ArtistEntity>(ArtistEntity.class, map));
   }
   
   public static ResultQuery<ArtistEntity, Integer> list() {
      return new ResultQuery<>(
            "SELECT * FROM artist",
            new DefaultItemCreator<ArtistEntity>(ArtistEntity.class, map));
   }
   
   public static Query<ArtistEntity> update() {
      return new Query<>(
            "UPDATE artist SET artistname = ? WHERE artistid = ?",
            (stmt, ent) -> {
               stmt.setString(1, ent.getName());
               stmt.setInt(2, ent.getKey());
            });
   }
   
   public static Query<Integer> delete() {
      return new Query<>(
            "DELETE FROM artist WHERE artistid = ?",
            (stmt, key) -> {
               stmt.setInt(1, key);
            });
   }
}
