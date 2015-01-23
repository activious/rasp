package ui.menu;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;

import ui.State;
import ui.Window;
import logic.command.CreateAlbumCommand;
import logic.command.RevertProjectCommand;
import config.Config;

public class TopMenuBar extends JMenuBar {
   private static final long serialVersionUID = 1L;

   private static final String
         MENU_FILE = "Filer",
         ITEM_FILE_EXIT = "Afslut",
         
         MENU_ADD = "Tilføj",
         ITEM_ADD_NEW_ALBUM = "Nyt album...",
         ITEM_ADD_NEW_ARTIST = "Ny kunstner...",
         
         MENU_VIEW = "Vis",
         ITEM_VIEW_ALBUMS = "Albums",
         ITEM_VIEW_ARTISTS = "Kunstnere",
         ITEM_VIEW_DETAILS = "Detaljer",
         
         MENU_DEV = "DEV",
         ITEM_DEV_REVERT_PROJECT = "Revert project";
   
   private static final char
         MN_FILE = 'F',
         MN_FILE_EXIT = 'u',
         MN_ADD = 'T',
         MN_ADD_NEW_ALBUM = 'a',
         MN_ADD_NEW_ARTIST = 'k',
         MN_VIEW = 'V';
   
   public TopMenuBar(Window win) {
      /**
       * File menu
       */
      JMenu mFile = new JMenu(MENU_FILE);
      mFile.setMnemonic(MN_FILE);
      add(mFile);

      JMenuItem iFileExit = new JMenuItem(ITEM_FILE_EXIT, MN_FILE_EXIT);
      iFileExit.addActionListener(e -> {
         System.exit(0);
      });
      mFile.add(iFileExit);
      
      /**
       * Add menu
       */
      JMenu mAdd = new JMenu(MENU_ADD);
      mAdd.setMnemonic(MN_ADD);
      add(mAdd);

      JMenuItem iAddNewAlbum = new JMenuItem(ITEM_ADD_NEW_ALBUM, MN_ADD_NEW_ALBUM);
      iAddNewAlbum.addActionListener(e -> {
         new CreateAlbumCommand().execute();
      });
      mAdd.add(iAddNewAlbum);

      JMenuItem iAddNewArtist = new JMenuItem(ITEM_ADD_NEW_ARTIST, MN_ADD_NEW_ARTIST);
      iAddNewArtist.addActionListener(e -> {});
      iAddNewArtist.setEnabled(false); // TODO: Not yet implemented
      mAdd.add(iAddNewArtist);
      
      /**
       * View menu
       */
      JMenu mView = new JMenu(MENU_VIEW);
      mView.setMnemonic(MN_VIEW);
      add(mView);

      State state = win.getGlobalState();
      
      JRadioButtonMenuItem iViewAlbums = new JRadioButtonMenuItem(ITEM_VIEW_ALBUMS);
      iViewAlbums.addActionListener(e -> {
         state.setView(State.VIEW_ARTISTS);
         win.updateState();
      });
      iViewAlbums.setSelected(state.getView() == State.VIEW_ALBUMS);
      mView.add(iViewAlbums);

      JRadioButtonMenuItem iViewArtists = new JRadioButtonMenuItem(ITEM_VIEW_ARTISTS);
      iViewArtists.addActionListener(e -> {
         state.setView(State.VIEW_ARTISTS);
         win.updateState();
      });
      iViewArtists.setSelected(state.getView() == State.VIEW_ARTISTS);
      iViewArtists.setEnabled(false); // TODO: Not yet implemented
      mView.add(iViewArtists);
      
      ButtonGroup menuViewSettingsGroup = new ButtonGroup();
      menuViewSettingsGroup.add(iViewAlbums);
      menuViewSettingsGroup.add(iViewArtists);
      
      mView.addSeparator();

      JCheckBoxMenuItem iViewDetails = new JCheckBoxMenuItem(ITEM_VIEW_DETAILS);
      iViewDetails.addActionListener(e -> {
         state.setDetailedView(iViewDetails.isSelected());
         win.updateState();
      });
      iViewDetails.setSelected(state.isDetailedView());
      iViewDetails.setEnabled(false); // TODO: Not yet implemented
      mView.add(iViewDetails);
      
      /**
       * DEV menu
       */
      JMenu mDev = new JMenu(MENU_DEV);
      add(mDev);

      JMenuItem iDevRevert = new JMenuItem(ITEM_DEV_REVERT_PROJECT);
      iDevRevert.addActionListener(event -> {
         try {
            new RevertProjectCommand(Config.PROJECT_NAME).execute();
            Window.getInstance().updateAlbumList();
         } catch (Exception e) {
            e.printStackTrace();
         }
      });
      mDev.add(iDevRevert);
   }
}
