package util.sql;

import java.sql.PreparedStatement;

public class Query<T> {
   private final Object sql;

   private final StatementPreparer<T> stmtPrep;

   public Query(Object sql) {
      this(sql, (s, t) -> {});
   }

   public Query(Object sql, StatementPreparer<T> statementPreparer) {
      this.sql = sql;
      stmtPrep = statementPreparer;
   }
   
   public Object getSQL() {
      return sql;
   }
   
   public StatementPreparer<T> getStatementPreparer() {
      return stmtPrep;
   }

   public void prepareToExecute(PreparedStatement s, T t) {
      stmtPrep.prepare(s, t);
   }

   @Override
   public String toString() {
      return sql.toString();
   }
}
