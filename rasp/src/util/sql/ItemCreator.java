package util.sql;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface ItemCreator<T> {
   T createFrom(ResultSet rs) throws SQLException;
}
