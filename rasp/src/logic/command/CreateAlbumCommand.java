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
      
      d.addOkListener(e -> {
         try {
            new SaveAlbumCommand(album).execute();
         } catch (Exception e1) {
            e1.printStackTrace();
         }
         Window.getInstance().updateAlbumList();
      });
      
      d.setVisible(true);
   }
}
