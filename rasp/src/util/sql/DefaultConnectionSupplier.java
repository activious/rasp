package util.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DefaultConnectionSupplier implements ConnectionSupplier, AutoCloseable {
   private final String dbUrl;

   private Connection conn = null;

   public DefaultConnectionSupplier(String dbUrl) {
      this.dbUrl = dbUrl;
   }

   @Override
   public Connection get() throws SQLException {
      if (conn == null) {
         conn = DriverManager.getConnection(dbUrl);
         conn.setAutoCommit(false);
      }

      return conn;
   }

   public void commit() throws SQLException {
      if (conn != null)
         conn.commit();
   }

   public void rollback() throws SQLException {
      if (conn != null)
         conn.rollback();
   }

   @Override
   public void close() throws SQLException {
      if (conn != null)
         conn.close();
   }
}
