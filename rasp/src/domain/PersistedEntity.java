package domain;

import util.sql.HasKey;

public abstract class PersistedEntity<K> implements HasKey<K> {
   private K key;

   public K getKey() {
      return key;
   }

   public void setKey(K key) {
      this.key = key;
   }
   
   public boolean isPersisted() {
      return (key != null);
   }
}
