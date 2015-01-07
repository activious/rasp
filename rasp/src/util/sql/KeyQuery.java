package util.sql;

import java.sql.ResultSet;
import java.sql.SQLException;

public class KeyQuery<K, T> extends Query<T> {
   private final KeyCreator<K, T> keyCreator;

   public KeyQuery(Object sql, KeyCreator<K, T> keyCreator) {
      this(sql, (s, t) -> {}, keyCreator);
   }

   public KeyQuery(Object sql,
                      StatementPreparer<T> statementPreparer,
                      KeyCreator<K, T> keyCreator) {
      super(sql, statementPreparer);
      this.keyCreator = keyCreator;
   }
   
   public KeyCreator<K, T> getKeyCreator() {
      return keyCreator;
   }

   public K createKeyFrom(ResultSet rs, T item) throws SQLException {
      return keyCreator.createFrom(rs, item);
   }
}
