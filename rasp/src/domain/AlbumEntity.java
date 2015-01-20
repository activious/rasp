package domain;

import java.util.ArrayList;
import java.util.List;

public class AlbumEntity extends PersistedEntity<Integer> {
   private String title;
   private Integer albumArtistKey;
   private ArtistEntity albumArtist;
   private List<TrackEntity> trackList;
   
   public AlbumEntity() {
      trackList = new ArrayList<>();
   }
   
   public AlbumEntity(AlbumEntity other) {
      title = other.getTitle();
      albumArtist = other.getAlbumArtist();
      trackList = other.getTrackList();
   }

   public String getTitle() {
      return title;
   }

   public void setTitle(String title) {
      this.title = title;
      setChanged(true);
   }
   
   public Integer getAlbumArtistKey() {
      return albumArtistKey;
   }

   public void setAlbumArtistKey(Integer albumArtistKey) {
      this.albumArtistKey = albumArtistKey;
   }

   public ArtistEntity getAlbumArtist() {
      return albumArtist;
   }

   public void setAlbumArtist(ArtistEntity albumArtist) {
      this.albumArtist = albumArtist;
      setChanged(true);
   }
   
   public List<TrackEntity> getTrackList() {
      return new ArrayList<TrackEntity>(trackList);
   }
   
   public void setTrackList(List<TrackEntity> trackList) {
      this.trackList = new ArrayList<>(trackList);
      setChanged(true);
   }
   
   public void addTrack(TrackEntity track) {
      trackList.add(track);
      setChanged(true);
   }
   
   public void removeTrack(TrackEntity track) {
      trackList.remove(track);
      setChanged(true);
   }
}
