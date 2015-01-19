package domain;

import java.util.HashSet;
import java.util.Set;

public class TrackEntity extends PersistedEntity<Integer> {
   private Integer albumKey, primaryArtistKey, number, duration;
   private String title;
   private ArtistEntity primaryArtist;
   private Set<ArtistEntity> guestArtists, composers, performers;
   
   public TrackEntity() {
      guestArtists = new HashSet<>();
      composers = new HashSet<>();
      performers = new HashSet<>();
   }
   
   public TrackEntity(TrackEntity other) {
      number = other.getNumber();
      title = other.getTitle();
      duration = other.getDuration();
      primaryArtist = other.getPrimaryArtist();
      guestArtists = other.getGuestArtists();
      composers = other.getComposers();
      performers = other.getPerformers();
   }
   
   public Integer getAlbumKey() {
      return albumKey;
   }

   public void setAlbumKey(Integer albumKey) {
      this.albumKey = albumKey;
   }

   public Integer getPrimaryArtistKey() {
      return primaryArtistKey;
   }

   public void setPrimaryArtistKey(Integer primaryArtistKey) {
      this.primaryArtistKey = primaryArtistKey;
   }

   public Integer getNumber() {
      return number;
   }
   
   public void setNumber(Integer number) {
      this.number = number;
      setChanged(true);
   }
   
   public String getTitle() {
      return title;
   }
   
   public void setTitle(String title) {
      this.title = title;
      setChanged(true);
   }
   
   public Integer getDuration() {
      return duration;
   }
   
   public void setDuration(Integer duration) {
      this.duration = duration;
      setChanged(true);
   }
   
   public ArtistEntity getPrimaryArtist() {
      return primaryArtist;
   }
   
   public void setPrimaryArtist(ArtistEntity primaryArtist) {
      this.primaryArtist = primaryArtist;
      setChanged(true);
   }
   
   public Set<ArtistEntity> getGuestArtists() {
      return new HashSet<>(guestArtists);
   }
   
   public void setGuestArtists(Set<ArtistEntity> guestArtists) {
      this.guestArtists = new HashSet<>(guestArtists);
      setChanged(true);
   }
   
   public void addGuestArtist(ArtistEntity guestArtist) {
      guestArtists.add(guestArtist);
      setChanged(true);
   }
   
   public void removeGuestArtist(ArtistEntity guestArtist) {
      guestArtists.remove(guestArtist);
      setChanged(true);
   }
   
   public Set<ArtistEntity> getComposers() {
      return new HashSet<>(composers);
   }
   
   public void setComposers(Set<ArtistEntity> composers) {
      this.composers = new HashSet<>(composers);
      setChanged(true);
   }
   
   public void addComposer(ArtistEntity composer) {
      composers.add(composer);
      setChanged(true);
   }
   
   public void removeComposer(ArtistEntity composer) {
      composers.remove(composer);
      setChanged(true);
   }
   
   public Set<ArtistEntity> getPerformers() {
      return new HashSet<>(performers);
   }
   
   public void setPerformers(Set<ArtistEntity> performers) {
      this.performers = new HashSet<>(performers);
      setChanged(true);
   }
   
   public void addPerformer(ArtistEntity performer) {
      performers.add(performer);
      setChanged(true);
   }
   
   public void removePerformer(ArtistEntity performer) {
      performers.remove(performer);
      setChanged(true);
   }
}
