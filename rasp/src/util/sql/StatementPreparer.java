package util.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface StatementPreparer<T> {
   void prepare(PreparedStatement s, T t) throws SQLException;
}
