package logic.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JDialog;

import radams.gracenote.webapi.GracenoteMetadata;
import domain.AlbumEntity;
import domain.ArtistEntity;
import logic.gracenote.Gracenote;
import ui.Window;
import util.function.Command;

public class OnlineSearchCommand implements Command {
   private String query;
   
   public OnlineSearchCommand(String query) {
      this.query = query.trim();
   }
   
   @Override
   public void execute() {
      GracenoteMetadata results = new Gracenote().searchArtist(query);
      
      List<AlbumEntity> albums = new ArrayList<>();
      for (Map<String, Object> info : results.getAlbums()) {
         AlbumEntity album = new AlbumEntity();
         album.setTitle(info.get("album_title").toString());
         
         ArtistEntity artist = new ArtistEntity();
         artist.setName(info.get("album_artist_name").toString());
         
         album.setAlbumArtist(artist);
         albums.add(album);
      }
      
      Window.getInstance().displayAlbums(albums);
   }
}
