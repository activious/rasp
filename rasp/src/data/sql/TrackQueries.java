package data.sql;

import java.util.HashMap;

import util.sql.DefaultItemCreator;
import util.sql.KeyQuery;
import util.sql.Query;
import util.sql.ResultQuery;
import domain.TrackEntity;

public class TrackQueries {
   private static HashMap<String, String> map;
   
   static {
      map = new HashMap<>();
      map.put("trackid", "key");
      map.put("trackalbum", "albumKey");
      map.put("tracknumber", "number");
      map.put("tracktitle", "title");
      map.put("trackduration", "duration");
      map.put("trackartist", "primaryArtistKey");
   }
   
   public static KeyQuery<Integer, TrackEntity> create(int albumKey) {
      return new KeyQuery<>(
            "INSERT INTO track (trackalbum, tracknumber, tracktitle, trackduration, trackartist) VALUES (?, ?, ?, ?, ?)",
            (stmt, ent) -> {
               stmt.setInt(1, albumKey);
               stmt.setInt(2, ent.getNumber());
               stmt.setString(3, ent.getTitle());
               stmt.setInt(4, ent.getDuration());
               stmt.setInt(5, ent.getPrimaryArtist().getKey());
            },
            (rs, ent) -> {
               ent.setKey(rs.getInt(1));
               return ent.getKey();
            });
   }
   
   public static ResultQuery<TrackEntity, Integer> read() {
      return new ResultQuery<>(
            "SELECT * FROM track WHERE trackid = ?",
            (stmt, key) -> {
               stmt.setInt(1, key);
            },
            new DefaultItemCreator<TrackEntity>(TrackEntity.class, map));
   }
   
   public static ResultQuery<TrackEntity, Integer> list(int albumKey) {
      return new ResultQuery<>(
            "SELECT * FROM track WHERE trackalbum = ?",
            (stmt, key) -> {
               stmt.setInt(1, albumKey);
            },
            new DefaultItemCreator<TrackEntity>(TrackEntity.class, map));
   }
   
   public static Query<TrackEntity> update() {
      return new Query<>(
            "UPDATE track SET tracknumber = ?, tracktitle = ?, trackduration = ?, trackartist = ? WHERE trackid = ?",
            (stmt, ent) -> {
               stmt.setInt(1, ent.getNumber());
               stmt.setString(2, ent.getTitle());
               stmt.setInt(3, ent.getDuration());
               stmt.setInt(4, ent.getPrimaryArtist().getKey());
               stmt.setInt(5, ent.getKey());
            });
   }
   
   public static Query<Integer> delete() {
      return new Query<>(
            "DELETE FROM track WHERE trackid = ?",
            (stmt, key) -> {
               stmt.setInt(1, key);
            });
   }
}
