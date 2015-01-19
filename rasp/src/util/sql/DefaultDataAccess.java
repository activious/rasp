package util.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DefaultDataAccess<T extends HasKey<K>, K> implements
      DataAccess<T, K> {
   private final ConnectionSupplier conn;

   public DefaultDataAccess(ConnectionSupplier connectionSupplier) {
      conn = connectionSupplier;
   }

   @Override
   public void create(T item, Query<T> q) throws SQLException {
      create(item, q.getSQL(), q.getStatementPreparer());
   }

   @Override
   public void create(T item, Object sql, StatementPreparer<T> statementPreparer) throws SQLException {
      createAll(Arrays.asList(item), sql, statementPreparer);
   }

   @Override
   public K create(T item, KeyQuery<K, T> q) throws SQLException {
      return create(item, q.getSQL(), q.getStatementPreparer(),
                    q.getKeyCreator());
   }

   @Override
   public K create(T item, Object sql, StatementPreparer<T> statementPreparer,
                   KeyCreator<K, T> keyCreator) throws SQLException {
      List<K> keys =
            createAll(Arrays.asList(item), sql, statementPreparer, keyCreator);
      return (keys.isEmpty() ? null : keys.get(0));
   }

   @Override
   public void createAll(List<T> items, Query<T> q) throws SQLException {
      createAll(items, q.getSQL(), q.getStatementPreparer());
   }

   @Override
   public void createAll(List<T> items, Object sql,
                         StatementPreparer<T> statementPreparer) throws SQLException {
      PreparedStatement s = null;
      try {
         s = conn.get().prepareStatement(sql.toString());
         for (T item : items) {
            statementPreparer.prepare(s, item);
            s.executeUpdate();
         }
      } finally {
         if (s != null)
            s.close();
      }
   }

   @Override
   public List<K> createAll(List<T> items, KeyQuery<K, T> q) throws SQLException {
      return createAll(items, q.getSQL(), q.getStatementPreparer(),
                       q.getKeyCreator());
   }

   @Override
   public List<K> createAll(List<T> items, Object sql,
                            StatementPreparer<T> statementPreparer,
                            KeyCreator<K, T> keyCreator) throws SQLException {
      List<K> keys = new ArrayList<>();
      PreparedStatement s = null;
      ResultSet r = null;
      try {
         s =
               conn.get()
                   .prepareStatement(sql.toString(),
                                     PreparedStatement.RETURN_GENERATED_KEYS);
         for (T item : items) {
            statementPreparer.prepare(s, item);
            s.executeUpdate();
            r = s.getGeneratedKeys();
            if (r.next())
               keys.add(keyCreator.createFrom(r, item));
         }
         return keys;
      } finally {
         if (r != null)
            r.close();
         if (s != null)
            s.close();
      }
   }

   @Override
   public T read(K key, ResultQuery<T, K> q) throws SQLException {
      return read(key, q.getSQL(), q.getStatementPreparer(), q.getItemCreator());
   }

   @Override
   public T read(K key, Object sql, StatementPreparer<K> statementPreparer,
                 ItemCreator<T> itemCreator) throws SQLException {
      List<T> items =
            readAll(Arrays.asList(key), sql, statementPreparer, itemCreator);
      return (items.isEmpty() ? null : items.get(0));
   }

   @Override
   public List<T> readAll(List<K> keys, ResultQuery<T, K> q) throws SQLException {
      return readAll(keys, q.getSQL(), q.getStatementPreparer(),
                     q.getItemCreator());
   }

   @Override
   public List<T> readAll(List<K> keys, Object sql,
                          StatementPreparer<K> statementPreparer,
                          ItemCreator<T> itemCreator) throws SQLException {
      List<T> items = new ArrayList<>();
      PreparedStatement s = null;
      ResultSet r = null;
      try {
         s = conn.get().prepareStatement(sql.toString());
         for (K key : keys) {
            statementPreparer.prepare(s, key);
            r = s.executeQuery();
            if (r.next())
               items.add(itemCreator.createFrom(r));
         }
         return items;
      } finally {
         if (r != null)
            r.close();
         if (s != null)
            s.close();
      }
   }

   @Override
   public List<T> listAll(ResultQuery<T, K> q) throws SQLException {
      return listAll(q.getSQL(), q.getStatementPreparer(), q.getItemCreator());
   }

   @Override
   public List<T> listAll(Object sql, ItemCreator<T> itemCreator) throws SQLException {
      return listAll(sql, (s, t) -> {}, itemCreator);
   }

   @Override
   public List<T> listAll(Object sql, StatementPreparer<K> statementPreparer,
                          ItemCreator<T> itemCreator) throws SQLException {
      List<T> items = new ArrayList<>();
      PreparedStatement s = null;
      ResultSet r = null;
      try {
         s = conn.get().prepareStatement(sql.toString());
         statementPreparer.prepare(s, null);
         r = s.executeQuery();
         while (r.next()) {
            items.add(itemCreator.createFrom(r));
         }
         return items;
      } finally {
         if (r != null)
            r.close();
         if (s != null)
            s.close();
      }
   }

   @Override
   public int update(T item, Query<T> q) throws SQLException {
      return update(item, q.getSQL(), q.getStatementPreparer());
   }

   @Override
   public int update(T item, Object sql, StatementPreparer<T> statementPreparer) throws SQLException {
      return updateAll(Arrays.asList(item), sql, statementPreparer);
   }

   @Override
   public int updateAll(List<T> items, Query<T> q) throws SQLException {
      return updateAll(items, q.getSQL(), q.getStatementPreparer());
   }

   @Override
   public int updateAll(List<T> items, Object sql,
                        StatementPreparer<T> statementPreparer) throws SQLException {
      return executeUpdate(items, sql, statementPreparer);
   }

   @Override
   public int delete(T item, Query<K> q) throws SQLException {
      return delete(item, q.getSQL(), q.getStatementPreparer());
   }

   @Override
   public int delete(T item, Object sql, StatementPreparer<K> statementPreparer) throws SQLException {
      return deleteAll(Arrays.asList(item), sql, statementPreparer);
   }

   @Override
   public int deleteAll(List<T> items, Query<K> q) throws SQLException {
      return deleteAll(items, q.getSQL(), q.getStatementPreparer());
   }

   @Override
   public int deleteAll(List<T> items, Object sql,
                        StatementPreparer<K> statementPreparer) throws SQLException {
      return deleteAllByKey(items.stream().map(item -> item.getKey())
                                 .collect(Collectors.toList()), sql,
                            statementPreparer);
   }

   @Override
   public int deleteByKey(K key, Query<K> q) throws SQLException {
      return deleteByKey(key, q.getSQL(), q.getStatementPreparer());
   }

   @Override
   public int deleteByKey(K key, Object sql,
                          StatementPreparer<K> statementPreparer) throws SQLException {
      return deleteAllByKey(Arrays.asList(key), sql, statementPreparer);
   }

   @Override
   public int deleteAllByKey(List<K> keys, Query<K> q) throws SQLException {
      return deleteAllByKey(keys, q.getSQL(), q.getStatementPreparer());
   }

   @Override
   public int deleteAllByKey(List<K> keys, Object sql,
                             StatementPreparer<K> statementPreparer) throws SQLException {
      return executeUpdate(keys, sql, statementPreparer);
   }

   private <E> int executeUpdate(List<E> elms, Object sql,
                                 StatementPreparer<E> sp) throws SQLException {
      PreparedStatement s = null;
      try {
         s = conn.get().prepareStatement(sql.toString());
         int i = 0;
         for (E e : elms) {
            sp.prepare(s, e);
            i += s.executeUpdate();
         }
         return i;
      } finally {
         if (s != null)
            s.close();
      }
   }

   public static int executeUpdate(Object sql, ConnectionSupplier conn) throws SQLException {
      PreparedStatement s = null;
      try {
         s = conn.get().prepareStatement(sql.toString());
         return s.executeUpdate();
      } finally {
         if (s != null)
            s.close();
      }
   }
}
