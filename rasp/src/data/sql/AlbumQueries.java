package data.sql;

import java.util.HashMap;

import util.sql.DefaultItemCreator;
import util.sql.KeyQuery;
import util.sql.Query;
import util.sql.ResultQuery;
import domain.AlbumEntity;

public class AlbumQueries {
   private static HashMap<String, String> map;
   
   static {
      map = new HashMap<>();
      map.put("albumid", "key");
      map.put("albumtitle", "title");
      map.put("albumartist", "albumArtistKey");
   }
   
   public static KeyQuery<Integer, AlbumEntity> create() {
      return new KeyQuery<>(
            "INSERT INTO album (albumtitle, albumartist) VALUES (?, ?)",
            (stmt, ent) -> {
               stmt.setString(1, ent.getTitle());
               stmt.setInt(2, ent.getAlbumArtist().getKey());
            },
            (rs, ent) -> {
               ent.setKey(rs.getInt(1));
               return ent.getKey();
            });
   }
   
   public static ResultQuery<AlbumEntity, Integer> read() {
      return new ResultQuery<>(
            "SELECT * FROM album WHERE albumid = ?",
            (stmt, key) -> {
               stmt.setInt(1, key);
            },
            new DefaultItemCreator<AlbumEntity>(AlbumEntity.class, map));
   }
   
   public static ResultQuery<AlbumEntity, Integer> list() {
      return new ResultQuery<>(
            "SELECT * FROM album",
            new DefaultItemCreator<AlbumEntity>(AlbumEntity.class, map));
   }
   
   public static Query<AlbumEntity> update() {
      return new Query<>(
            "UPDATE album SET albumtitle = ?, albumartist = ? WHERE albumid = ?",
            (stmt, ent) -> {
               stmt.setString(1, ent.getTitle());
               stmt.setInt(2, ent.getAlbumArtist().getKey());
               stmt.setInt(3, ent.getKey());
            });
   }
   
   public static Query<Integer> delete() {
      return new Query<>(
            "DELETE FROM album WHERE albumid = ?",
            (stmt, key) -> {
               stmt.setInt(1, key);
            });
   }
}
