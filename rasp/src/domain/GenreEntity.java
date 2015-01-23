package domain;

import util.domain.PersistedEntity;

public class GenreEntity extends PersistedEntity<Integer> {
   private String name;

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
      setChanged(true);
   }
}
