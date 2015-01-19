package logic.command;

import java.io.IOException;
import java.sql.SQLException;

import util.function.Command;
import util.sql.DefaultDataAccess;
import data.DataAccessConnection;
import data.sql.DDL;

public class CreateProjectCommand implements Command {
   private final DataAccessConnection dac;

   public CreateProjectCommand(String projectName) throws IOException {
      dac = new DataAccessConnection(projectName);
   }

   @Override
   public void execute() throws SQLException {
      try {
         createProjectTables();
         dac.commit();
      } catch (SQLException e) {
         dac.rollback();
         throw e;
      } finally {
         dac.close();
      }
   }

   private void createProjectTables() throws SQLException {
      createTable(DDL.CREATE_TABLE_ARTIST);
      createTable(DDL.CREATE_TABLE_ALBUM);
      createTable(DDL.CREATE_TABLE_TRACK);
      createTable(DDL.CREATE_TABLE_GUEST_ARTIST);
      createTable(DDL.CREATE_TABLE_COMPOSER);
      createTable(DDL.CREATE_TABLE_PERFORMER);
   }

   private void createTable(Object sql) throws SQLException {
      DefaultDataAccess.executeUpdate(sql, dac);
   }
}
