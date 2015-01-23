package util.domain;

import util.sql.HasKey;

public abstract class PersistedEntity<K> implements HasKey<K> {
   private K key;
   
   private boolean changed = false;

   public K getKey() {
      return key;
   }

   public void setKey(K key) {
      this.key = key;
   }
   
   public boolean isPersisted() {
      return (key != null);
   }
   
   public boolean hasChanged() {
      return changed;
   }
   
   public void setChanged(boolean b) {
      changed = b;
   }
}
