package util.sql;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ResultQuery<T, K> extends Query<K> {
   private final ItemCreator<T> itemCreator;

   public ResultQuery(Object sql, ItemCreator<T> itemCreator) {
      this(sql, (s, k) -> {}, itemCreator);
   }

   public ResultQuery(Object sql,
                      StatementPreparer<K> statementPreparer,
                      ItemCreator<T> itemCreator) {
      super(sql, statementPreparer);
      this.itemCreator = itemCreator;
   }
   
   public ItemCreator<T> getItemCreator() {
      return itemCreator;
   }

   public T createItemFrom(ResultSet rs) throws SQLException {
      return itemCreator.createFrom(rs);
   }
}
