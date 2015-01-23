package domain;

import java.util.ArrayList;
import java.util.List;

public class Model {
   private static Model INSTANCE;

   private List<ArtistEntity> artists;
   private List<AlbumEntity> albums;

   private Model() {
      artists = new ArrayList<>();
      albums = new ArrayList<>();
   }

   public List<ArtistEntity> getArtists() {
      return artists;
   }
   
   public List<ArtistEntity> getUnsavedArtists() {
      List<ArtistEntity> res = new ArrayList<>();
      for (ArtistEntity a : artists) {
         if (!a.isPersisted())
            res.add(a);
      }
      return res;
   }
   
   public void setArtists(List<ArtistEntity> artists) {
      this.artists = artists;
   }
   
   public ArtistEntity getArtistByKey(int key) {
      for (ArtistEntity a : artists) {
         if (a.getKey() == key)
            return a;
      }
      
      return null;
   }
   
   public boolean hasArtistName(String name) {
      for (ArtistEntity a : artists) {
         if (a.getName().equals(name))
            return true;
      }
      
      return false;
   }
   
   public ArtistEntity getArtistByName(String name) {
      for (ArtistEntity a : artists) {
         if (a.getName().equals(name))
            return a;
      }

      // Create a new ArtistEntity if name is new
      ArtistEntity a = new ArtistEntity();
      a.setName(name);
      artists.add(a);
      return a;
   }

   public List<AlbumEntity> getAlbums() {
      return albums;
   }
   
   public void setAlbums(List<AlbumEntity> albums) {
      this.albums = albums;
   }
   
   public AlbumEntity getAlbumByKey(int key) {
      for (AlbumEntity a : albums) {
         if (a.getKey() == key)
            return a;
      }
      
      return null;
   }

   public ArtistEntity createArtist() {
      ArtistEntity a = new ArtistEntity();
      artists.add(a);
      return a;
   }

   public AlbumEntity createAlbum() {
      AlbumEntity a = new AlbumEntity();
      albums.add(a);
      return a;
   }

   public static Model getInstance() {
      if (INSTANCE == null)
         INSTANCE = new Model();

      return INSTANCE;
   }
}
