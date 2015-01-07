package domain;

public class ArtistEntity extends PersistedEntity<Integer> {
   private String name;

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }
}
