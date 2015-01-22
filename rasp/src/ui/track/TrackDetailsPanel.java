package ui.track;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Model;
import ui.ComponentFactory;
import ui.TextValueField;
import ui.editor.Editor;
import ui.event.ItemEvent;
import domain.ArtistEntity;
import domain.TrackEntity;

public class TrackDetailsPanel extends JPanel implements Editor {
   private static final long serialVersionUID = 1L;

   private boolean editable = false;

   private TrackEntity currentTrack, editedTrack;

   private TextValueField<ArtistEntity> primaryArtistField;

   private JPanel pGuestArtists, pComposers, pPerformers;
   private final List<TextValueField<ArtistEntity>> guestArtistFields,
         composerFields, performerFields, newFields;

   public TrackDetailsPanel() {
      guestArtistFields = new ArrayList<>();
      composerFields = new ArrayList<>();
      performerFields = new ArrayList<>();
      newFields = new ArrayList<>();

      setOpaque(false);
      setLayout(new GridBagLayout());

      initComponents();
      layoutComponents();

      // setBackground(new Color(0, 0, 255));
   }

   private void initComponents() {
      primaryArtistField =
            new TextValueField<ArtistEntity>(15, false, true, false,
                                             a -> a.getName());

      pGuestArtists = createMultiValuePanel();
      pComposers = createMultiValuePanel();
      pPerformers = createMultiValuePanel();
   }

   private void layoutComponents() {
      removeAll();

      GridBagConstraints gbc = new GridBagConstraints();
      gbc.insets = new Insets(1, 10, 1, 1);
      gbc.gridy = 0;

      if (editable || currentTrack != null &&
          currentTrack.getPrimaryArtist() != null)
         addToLayout(ComponentFactory.createDefaultLabel("Primær kunstner:"),
                     primaryArtistField, gbc);

      if (editable || currentTrack != null &&
          !currentTrack.getGuestArtists().isEmpty())
         addToLayout(ComponentFactory.createDefaultLabel("Gæstekunstnere:"),
                     pGuestArtists, gbc);

      if (editable || currentTrack != null &&
          !currentTrack.getComposers().isEmpty())
         addToLayout(ComponentFactory.createDefaultLabel("Komponister:"),
                     pComposers, gbc);

      if (editable || currentTrack != null &&
          !currentTrack.getPerformers().isEmpty())
         addToLayout(ComponentFactory.createDefaultLabel("Performere:"),
                     pPerformers, gbc);
   }

   private void addToLayout(JLabel label, JComponent field,
                            GridBagConstraints gbc) {
      gbc.anchor = GridBagConstraints.FIRST_LINE_END;
      gbc.gridx = 0;
      add(label, gbc);

      gbc.anchor = GridBagConstraints.FIRST_LINE_START;
      gbc.gridx++;
      add(field, gbc);

      gbc.gridy++;
   }

   public TrackEntity getTrack() {
      return currentTrack;
   }

   public void setTrack(TrackEntity track) {
      currentTrack = track;

      applyTrackInfo();
      layoutComponents();
   }

   private void applyTrackInfo() {
      if (currentTrack.getPrimaryArtist() != null)
         setPrimaryArtist(currentTrack.getPrimaryArtist());

      if (currentTrack.getGuestArtists() != null)
         setGuestArtists(currentTrack.getGuestArtists());

      if (currentTrack.getComposers() != null)
         setComposers(currentTrack.getComposers());

      if (currentTrack.getPerformers() != null)
         setPerformers(currentTrack.getPerformers());
   }

   public void setPrimaryArtist(ArtistEntity artist) {
      primaryArtistField.setValue(artist);
   }

   public void setGuestArtists(Set<ArtistEntity> artists) {
      setNamesTo(artists, guestArtistFields, pGuestArtists);
      for (TextValueField<ArtistEntity> f : guestArtistFields) {
         f.addItemAddedListener(e -> guestArtistAdded(e));
         f.addItemRemovedListener(e -> guestArtistRemoved(e));
      }
   }

   public void setComposers(Set<ArtistEntity> artists) {
      setNamesTo(artists, composerFields, pComposers);
      for (TextValueField<ArtistEntity> f : composerFields) {
         f.addItemAddedListener(e -> composerAdded(e));
         f.addItemRemovedListener(e -> composerRemoved(e));
      }
   }

   public void setPerformers(Set<ArtistEntity> artists) {
      setNamesTo(artists, performerFields, pPerformers);
      for (TextValueField<ArtistEntity> f : performerFields) {
         f.addItemAddedListener(e -> performerAdded(e));
         f.addItemRemovedListener(e -> performerRemoved(e));
      }
   }

   private JPanel createMultiValuePanel() {
      JPanel p = new JPanel();
      p.setOpaque(false);
      p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
      return p;
   }

   private void setNamesTo(Set<ArtistEntity> artists,
                           List<TextValueField<ArtistEntity>> fieldList,
                           Container c) {
      fieldList.clear();
      c.removeAll();

      for (ArtistEntity artist : artists) {
         TextValueField<ArtistEntity> f =
               new TextValueField<ArtistEntity>(15, false, true, editable,
                                                a -> a.getName());
         f.setValue(artist);

         fieldList.add(f);
         c.add(f);
      }
   }

   @Override
   public void saveEdit() {
      if (!editable)
         return;
      
      saveFields();

      currentTrack.setNumber(editedTrack.getNumber());
      currentTrack.setPrimaryArtist(editedTrack.getPrimaryArtist());
      currentTrack.setGuestArtists(editedTrack.getGuestArtists());
      currentTrack.setComposers(editedTrack.getComposers());
      currentTrack.setPerformers(editedTrack.getPerformers());
   }
   
   private void saveFields() {
      // TODO
   }

   @Override
   public void revert() {
      applyTrackInfo();
   }

   @Override
   public boolean isEditable() {
      return editable;
   }

   @Override
   public void setEditable(boolean editable) {
      if (this.editable == editable)
         return;

      this.editable = editable;

      primaryArtistField.setEditable(editable);

      for (TextValueField<ArtistEntity> f : guestArtistFields) {
         f.setCanDelete(editable);
         f.update();
      }

      for (TextValueField<ArtistEntity> f : composerFields) {
         f.setCanDelete(editable);
         f.update();
      }

      for (TextValueField<ArtistEntity> f : performerFields) {
         f.setCanDelete(editable);
         f.update();
      }

      if (editable) {
         TextValueField<ArtistEntity> f;
         f = createGuestArtistField();
         newFields.add(f);
         pGuestArtists.add(f);

         f = createComposerField();
         newFields.add(f);
         pComposers.add(f);

         f = createPerformerField();
         newFields.add(f);
         pPerformers.add(f);
      } else {
         for (TextValueField<ArtistEntity> f : newFields)
            f.getParent().remove(f);

         newFields.clear();
      }

      editedTrack = (editable ? new TrackEntity(currentTrack) : null);

      layoutComponents();
   }

   private TextValueField<ArtistEntity> createGuestArtistField() {
      TextValueField<ArtistEntity> f = createField();
      f.addItemAddedListener(e -> guestArtistAdded(e));
      f.addItemRemovedListener(e -> guestArtistRemoved(e));
      return f;
   }

   private TextValueField<ArtistEntity> createComposerField() {
      TextValueField<ArtistEntity> f = createField();
      f.addItemAddedListener(e -> composerAdded(e));
      f.addItemRemovedListener(e -> composerRemoved(e));
      return f;
   }

   private TextValueField<ArtistEntity> createPerformerField() {
      TextValueField<ArtistEntity> f = createField();
      f.addItemAddedListener(e -> performerAdded(e));
      f.addItemRemovedListener(e -> performerRemoved(e));
      return f;
   }

   private TextValueField<ArtistEntity> createField() {
      return new TextValueField<>(15, true, true, false, a -> a.getName());
   }

   private void guestArtistAdded(ItemEvent<String> e) {
      ArtistEntity a = Model.getInstance().createArtist();
      a.setName(e.getItem());
      editedTrack.addGuestArtist(a);
      itemAdded(e);
   }

   private void guestArtistRemoved(ItemEvent<ArtistEntity> e) {
      editedTrack.removeGuestArtist(e.getItem());
      itemRemoved(e);
   }

   private void composerAdded(ItemEvent<String> e) {
      ArtistEntity a = Model.getInstance().createArtist();
      a.setName(e.getItem());
      editedTrack.addComposer(a);
      itemAdded(e);
   }

   private void composerRemoved(ItemEvent<ArtistEntity> e) {
      editedTrack.removeComposer(e.getItem());
      itemRemoved(e);
   }

   private void performerAdded(ItemEvent<String> e) {
      ArtistEntity a = Model.getInstance().createArtist();
      a.setName(e.getItem());
      editedTrack.addPerformer(a);
      itemAdded(e);
   }

   private void performerRemoved(ItemEvent<ArtistEntity> e) {
      editedTrack.removePerformer(e.getItem());
      itemRemoved(e);
   }

   private void itemAdded(ItemEvent<String> event) {
      @SuppressWarnings("unchecked")
      TextValueField<ArtistEntity> f =
            (TextValueField<ArtistEntity>) event.getSource();
      f.setEditable(false);
      newFields.remove(f);

      Container p = f.getParent();
      f = createField();
      newFields.add(f);
      p.add(f);
   }

   private void itemRemoved(ItemEvent<ArtistEntity> event) {
      @SuppressWarnings("unchecked")
      TextValueField<ArtistEntity> f =
            (TextValueField<ArtistEntity>) event.getSource();
      Container p = f.getParent();
      p.remove(f);
      p.validate();
   }
}
