package data;

import util.sql.DefaultConnectionSupplier;

public class DataAccessConnection extends DefaultConnectionSupplier {
   public DataAccessConnection(String projectName) {
      super("jdbc:sqlite:" + projectName + ".db");
   }
}
