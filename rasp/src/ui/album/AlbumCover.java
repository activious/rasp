package ui.album;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.event.MouseInputAdapter;

import logic.command.EditAlbumCommand;
import ui.ComponentFactory;
import domain.AlbumEntity;

public class AlbumCover extends JPanel {
   private static final long serialVersionUID = 1L;

   private final AlbumEntity album;
   
   public AlbumCover(AlbumEntity album) {
      this.album = album;
      
      setLayout(new BorderLayout(5,5));
      addMouseListener(new MouseAdapter() {
         @Override
         public void mouseClicked(MouseEvent e) {
            new EditAlbumCommand(album).execute();
         }
      });
      
      layoutComponents();
   }
   
   private void layoutComponents() {
      JPanel cover = new JPanel();
      cover.setBackground(new Color(200,200,200));
      cover.setPreferredSize(new Dimension(200,200));
      add(cover, BorderLayout.CENTER);
      
      JPanel info = new JPanel();
      info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
      info.add(ComponentFactory.createDefaultLabel(album.getTitle()));
      
      JPanel by = new JPanel();
      by.add(ComponentFactory.createDefaultLabel("af"));
      by.add(ComponentFactory.createDefaultLabel(album.getAlbumArtist().getName()));
      info.add(by);
      
      add(info, BorderLayout.SOUTH);
   }
}
