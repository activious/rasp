package ui.album;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Model;

import org.sqlite.core.CoreDatabaseMetaData.PrimaryKeyFinder;

import ui.ComponentFactory;
import ui.TextValueField;
import domain.AlbumEntity;
import domain.ArtistEntity;

public class AlbumDetailsPanel extends JPanel {
   private static final long serialVersionUID = 1L;

   AlbumEntity currentAlbum, editedAlbum;

   private JTextField titleField;
   private TextValueField<ArtistEntity> albumArtistField;

   public AlbumDetailsPanel() {
      setLayout(new GridBagLayout());

      initComponents();
      layoutComponents();
   }

   private void initComponents() {
      titleField = ComponentFactory.createDefaultTextField(20);
      albumArtistField =
            new TextValueField<>(20, true, false, false, a -> a.getName());
   }

   private void layoutComponents() {
      GridBagConstraints gbc = new GridBagConstraints();
      gbc.gridy = 0;

      addField(ComponentFactory.createDefaultLabel("Titel:"), titleField, gbc,
               new Insets(0, 8, 0, 0));
      addField(ComponentFactory.createDefaultLabel("Primær kunstner:"),
               albumArtistField, gbc, new Insets(0, 0, 0, 0));
   }

   private void addField(JLabel label, JComponent field,
                         GridBagConstraints gbc, Insets insets) {
      gbc.insets = new Insets(0, 0, 0, 0);
      gbc.anchor = GridBagConstraints.LINE_END;
      gbc.gridx = 0;
      add(label, gbc);

      gbc.insets = insets;
      gbc.anchor = GridBagConstraints.LINE_START;
      gbc.gridx++;
      add(field, gbc);

      gbc.gridy++;
   }

   private void applyAlbumInfo() {
      if (currentAlbum.getTitle() != null)
         titleField.setText(currentAlbum.getTitle());

      if (currentAlbum.getAlbumArtist() != null)
         albumArtistField.setValue(currentAlbum.getAlbumArtist());
   }

   public void setAlbum(AlbumEntity album) {
      currentAlbum = album;
      editedAlbum = new AlbumEntity(album);

      applyAlbumInfo();
   }

   public void saveEdit() {
      saveFields();
      
      currentAlbum.setTitle(editedAlbum.getTitle());
      currentAlbum.setAlbumArtist(editedAlbum.getAlbumArtist());
   }
   
   private void saveFields() {
      editedAlbum.setTitle(titleField.getText());
      
      String artistName = albumArtistField.getText();
      editedAlbum.setAlbumArtist(Model.getInstance().getArtistByName(artistName));
   }
}
