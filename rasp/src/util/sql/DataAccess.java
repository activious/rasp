package util.sql;

import java.sql.SQLException;
import java.util.List;

public interface DataAccess<T, K> {
   void create(T item, Query<T> q) throws SQLException;

   void create(T item, Object sql, StatementPreparer<T> statementPreparer) throws SQLException;

   K create(T item, KeyQuery<K, T> q) throws SQLException;

   K create(T item, Object sql, StatementPreparer<T> statementPreparer,
            KeyCreator<K, T> keyCreator) throws SQLException;

   void createAll(List<T> items, Query<T> q) throws SQLException;

   void createAll(List<T> items, Object sql,
                  StatementPreparer<T> statementPreparer) throws SQLException;

   List<K> createAll(List<T> items, KeyQuery<K, T> q) throws SQLException;

   List<K> createAll(List<T> items, Object sql,
                     StatementPreparer<T> statementPreparer,
                     KeyCreator<K, T> keyCreator) throws SQLException;

   T read(K key, ResultQuery<T, K> q) throws SQLException;

   T read(K key, Object sql, StatementPreparer<K> statementPreparer,
          ItemCreator<T> itemCreator) throws SQLException;

   List<T> readAll(List<K> keys, ResultQuery<T, K> q) throws SQLException;

   List<T> readAll(List<K> keys, Object sql,
                   StatementPreparer<K> statementPreparer,
                   ItemCreator<T> itemCreator) throws SQLException;

   List<T> listAll(ResultQuery<T, K> q) throws SQLException;

   List<T> listAll(Object sql, ItemCreator<T> itemCreator) throws SQLException;

   List<T> listAll(Object sql, StatementPreparer<K> statementPreparer,
                   ItemCreator<T> itemCreator) throws SQLException;

   int update(T item, Query<T> q) throws SQLException;

   int update(T item, Object sql, StatementPreparer<T> statementPreparer) throws SQLException;

   int updateAll(List<T> items, Query<T> q) throws SQLException;

   int updateAll(List<T> items, Object sql,
                 StatementPreparer<T> statementPreparer) throws SQLException;

   int delete(T item, Query<K> q) throws SQLException;

   int delete(T item, Object sql, StatementPreparer<K> statementPreparer) throws SQLException;

   int deleteAll(List<T> items, Query<K> q) throws SQLException;

   int deleteAll(List<T> items, Object sql,
                 StatementPreparer<K> statementPreparer) throws SQLException;

   int deleteByKey(K key, Query<K> q) throws SQLException;

   int deleteByKey(K key, Object sql, StatementPreparer<K> statementPreparer) throws SQLException;

   int deleteAllByKey(List<K> keys, Query<K> q) throws SQLException;

   int deleteAllByKey(List<K> keys, Object sql,
                      StatementPreparer<K> statementPreparer) throws SQLException;
}
