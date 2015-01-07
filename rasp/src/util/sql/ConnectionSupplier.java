package util.sql;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionSupplier {
   Connection get() throws SQLException;
}
