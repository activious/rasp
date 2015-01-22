package logic.command;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import domain.AlbumEntity;
import domain.ArtistEntity;
import domain.TrackEntity;
import model.Model;
import ui.Window;
import util.Collections;
import util.function.Command;

public class SearchCommand implements Command {
   private String query;
   
   public SearchCommand(String query) {
      this.query = query.trim().toLowerCase();
   }
   
   @Override
   public void execute() {
      Model model = Model.getInstance();
      List<ArtistEntity> artists = model.getArtists();
      List<AlbumEntity> albums = model.getAlbums();
      
      Set<AlbumEntity> resByAlbum = new HashSet<>();
      Set<AlbumEntity> resByArtist = new HashSet<>();
      Set<AlbumEntity> resByTrack = new HashSet<>();

      // Find all artists whose name contains query
      Set<ArtistEntity> ar = new HashSet<ArtistEntity>();
      for (ArtistEntity a : artists) {
         if (a.getName().toLowerCase().contains(query))
            ar.add(a);
      }
      
      // Find all albums whose title contains query
      // and/or are by matching artists
      // and/or have tracks whose
      //   1) title contains query
      //   2) are associated with matching artists
      for (AlbumEntity a : albums) {
         if (a.getTitle().toLowerCase().contains(query)) {
            resByAlbum.add(a);
         } else if (ar.contains(a.getAlbumArtist())) {
            resByArtist.add(a);
         } else {
            for (TrackEntity t : a.getTrackList()) {
               if (t.getTitle().toLowerCase().contains(query)
                     || ar.contains(t.getPrimaryArtist())
                     || Collections.containsAny(ar, t.getGuestArtists())
                     || Collections.containsAny(ar, t.getComposers())
                     || Collections.containsAny(ar, t.getPerformers())) {
                  resByTrack.add(a);
                  break;
               }
            }
         }
      }
      
      List<AlbumEntity> res = new ArrayList<>();
      res.addAll(resByAlbum);
      res.addAll(resByArtist);
      res.addAll(resByTrack);
      Window.getInstance().displayAlbums(res);
   }
}
