package util.sql;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface KeyCreator<K, T> {
   K createFrom(ResultSet rs, T item) throws SQLException;
}
