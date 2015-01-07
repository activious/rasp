package data.sql;

public enum DDL {
   CREATE_TABLE_ARTIST("CREATE TABLE artist (\n"
                       + "  artistid INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                       + "  artistname TEXT NOT NULL);"),

   CREATE_TABLE_ALBUM("CREATE TABLE album (\n"
                      + "  albumid INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                      + "  albumtitle TEXT NOT NULL,\n"
                      + "  albumartist INTEGER NOT NULL,\n"
                      + "  FOREIGN KEY(albumartist) REFERENCES artist(artistid));"),

   CREATE_TABLE_TRACK("CREATE TABLE track (\n"
                      + "  trackid INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                      + "  tracktitle TEXT NOT NULL,\n"
                      + "  trackalbum INTEGER NOT NULL,\n"
                      + "  trackartist INTEGER NOT NULL,\n"
                      + "  trackduration INTEGER,\n"
                      + "  FOREIGN KEY(trackalbum) REFERENCES album(albumid),\n"
                      + "  FOREIGN KEY(trackartist) REFERENCES artist(artistid));");

   private final String sql;

   private DDL(String sql) {
      this.sql = sql;
   }

   @Override
   public String toString() {
      return sql;
   }
}
