package data.sql;

public enum DDL {
   CREATE_TABLE_ARTIST("CREATE TABLE artist (\n"
                       + "  artistid INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                       + "  artistname TEXT NOT NULL);"),

   CREATE_TABLE_ALBUM("CREATE TABLE album (\n"
                      + "  albumid INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                      + "  albumtitle TEXT NOT NULL,\n"
                      + "  albumartist INTEGER NOT NULL,\n"
                      + "  FOREIGN KEY(albumartist) REFERENCES artist(artistid) ON DELETE CASCADE);"),

   CREATE_TABLE_TRACK("CREATE TABLE track (\n"
                      + "  trackid INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                      + "  trackalbum INTEGER NOT NULL,\n"
                      + "  tracknumber INTEGER NOT NULL,\n"
                      + "  tracktitle TEXT NOT NULL,\n"
                      + "  trackduration INTEGER,\n"
                      + "  trackartist INTEGER NOT NULL,\n"
                      + "  FOREIGN KEY(trackalbum) REFERENCES album(albumid) ON DELETE CASCADE,\n"
                      + "  FOREIGN KEY(trackartist) REFERENCES artist(artistid) ON DELETE CASCADE);"),
   
   CREATE_TABLE_GUEST_ARTIST("CREATE TABLE guestartist (\n"
                             + "  trackid INTEGER NOT NULL,\n"
                             + "  guestartist INTEGER NOT NULL,\n"
                             + "  FOREIGN KEY(trackid) REFERENCES track(trackid) ON DELETE CASCADE,\n"
                             + "  FOREIGN KEY(guestartist) REFERENCES artist(artistid) ON DELETE CASCADE);"),
   
   CREATE_TABLE_COMPOSER("CREATE TABLE composer (\n"
                         + "  trackid INTEGER NOT NULL,\n"
                         + "  composer INTEGER NOT NULL,\n"
                         + "  FOREIGN KEY(trackid) REFERENCES track(trackid) ON DELETE CASCADE,\n"
                         + "  FOREIGN KEY(composer) REFERENCES artist(artistid) ON DELETE CASCADE);"),
   
   CREATE_TABLE_PERFORMER("CREATE TABLE performer (\n"
                          + "  trackid INTEGER NOT NULL,\n"
                          + "  performer INTEGER NOT NULL,\n"
                          + "  FOREIGN KEY(trackid) REFERENCES track(trackid) ON DELETE CASCADE,\n"
                          + "  FOREIGN KEY(performer) REFERENCES artist(artistid) ON DELETE CASCADE);");

   private final String sql;

   private DDL(String sql) {
      this.sql = sql;
   }

   @Override
   public String toString() {
      return sql;
   }
}
