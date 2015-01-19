package logic.command;

import ui.Window;
import ui.album.AlbumDialog;
import util.function.Command;
import domain.AlbumEntity;

public class EditAlbumCommand implements Command {
   private final AlbumEntity album;

   public EditAlbumCommand(AlbumEntity album) {
      this.album = album;
   }

   @Override
   public void execute() {
      AlbumDialog d = new AlbumDialog(Window.getInstance(), album);
      d.setVisible(true);
   }
}
