package util.sql;

import java.sql.PreparedStatement;

@FunctionalInterface
public interface StatementPreparer<T> {
   void prepare(PreparedStatement s, T t);
}
