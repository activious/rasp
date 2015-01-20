package logic.command;

import ui.Window;
import ui.album.AlbumDialog;
import util.function.Command;
import domain.AlbumEntity;

public class CreateAlbumCommand implements Command {
   @Override
   public void execute() {
      AlbumEntity album = new AlbumEntity();
      AlbumDialog d = new AlbumDialog(Window.getInstance(), album);
      d.setVisible(true);
   }
}
