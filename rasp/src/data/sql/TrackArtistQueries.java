package data.sql;

public class TrackArtistQueries {
   public static String getCreateSQL(String artistType) {
      return "INSERT INTO " + artistType + " VALUES (?, ?)";
   }
   
   public static String getSelectSQL(String artistType) {
      return "SELECT * FROM " + artistType + " WHERE trackid = ?";
   }
   
   public static String getDeleteSQL(String artistType) {
      return "DELETE FROM " + artistType + " WHERE trackid = ?";
   }
}
