package ui.album;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
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

      setOpaque(false);
      setLayout(new BorderLayout(5, 5));
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
      cover.setBackground(new Color(200, 200, 200));
      cover.setPreferredSize(new Dimension(200, 200));
      add(cover, BorderLayout.CENTER);

      JPanel info = new JPanel();
      info.setOpaque(false);
      info.setLayout(new GridBagLayout());
      
      GridBagConstraints gbc = new GridBagConstraints();
      gbc.gridx = 0;
      gbc.gridy = 0;
      gbc.anchor = GridBagConstraints.CENTER;
      
      JLabel lblTitle = ComponentFactory.createDefaultLabel(album.getTitle());
      lblTitle.setPreferredSize(new Dimension(200, 20));
      lblTitle.setHorizontalAlignment(JLabel.CENTER);
      info.add(lblTitle, gbc);

      gbc.gridy++;
      info.add(ComponentFactory.createDefaultLabel("af"), gbc);
      
      gbc.gridy++;
      JLabel lblArtist = ComponentFactory.createDefaultLabel(album.getAlbumArtist()
                                                             .getName());
      lblArtist.setPreferredSize(new Dimension(200, 20));
      lblArtist.setHorizontalAlignment(JLabel.CENTER);
      info.add(lblArtist, gbc);

      add(info, BorderLayout.SOUTH);
   }
}
