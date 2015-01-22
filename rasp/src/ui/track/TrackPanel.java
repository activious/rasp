package ui.track;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JPanel;
import javax.swing.JTextField;

import logic.formatter.DurationFormatter;
import logic.parser.DurationParser;
import logic.validator.DurationValidator;
import ui.TextValueField;
import ui.editor.Editor;
import domain.TrackEntity;

public class TrackPanel extends JPanel implements Editor {
   private static final long serialVersionUID = 1L;

   private boolean editable = false;

   private TrackEntity currentTrack, editedTrack;

   private TextValueField<String> titleField;
   private TextValueField<Integer> durationField;
   private TrackDetailsPanel detailsPanel;

   public TrackPanel() {
      this(new TrackEntity());
   }

   public TrackPanel(TrackEntity track) {
      setOpaque(false);
      setLayout(new GridBagLayout());
      // setBackground(new Color(200,200,255));

      initComponents();
      layoutComponents();

      setTrack(track);
   }

   private void initComponents() {
      titleField = new TextValueField<String>(20, false, false, false, s -> s);
      titleField.setBold(true);
      durationField =
            new TextValueField<Integer>(4, false, false, false,
                                        n -> new DurationFormatter().format(n));
      durationField.setAlignment(JTextField.RIGHT);
      detailsPanel = new TrackDetailsPanel();
   }

   private void layoutComponents() {
      GridBagConstraints gbc = new GridBagConstraints();
      gbc.weightx = 1.0;
      gbc.anchor = GridBagConstraints.FIRST_LINE_START;
      gbc.gridx = 0;
      gbc.gridy = 0;
      gbc.insets = new Insets(0, 0, 0, 0);
      add(titleField, gbc);

      gbc.gridx++;
      gbc.anchor = GridBagConstraints.FIRST_LINE_END;
      add(durationField, gbc);

      gbc.anchor = GridBagConstraints.FIRST_LINE_START;
      gbc.gridx = 0;
      gbc.gridy++;
      gbc.gridwidth = 2;
      gbc.insets = new Insets(4, 30, 0, 0);
      add(detailsPanel, gbc);
   }

   public TrackEntity getTrack() {
      return currentTrack;
   }

   public void setTrack(TrackEntity track) {
      currentTrack = track;
      applyTrackInfo();
      detailsPanel.setTrack(track);
   }

   private void applyTrackInfo() {
      if (currentTrack.getTitle() != null)
         setTitle(currentTrack.getTitle());

      if (currentTrack.getDuration() != null)
         setDuration(currentTrack.getDuration());
   }

   public void setTitle(String title) {
      titleField.setValue(title);
   }

   public void setDuration(int seconds) {
      durationField.setValue(seconds);
   }

   @Override
   public boolean isEditable() {
      return editable;
   }

   @Override
   public void setEditable(boolean b) {
      if (editable == b)
         return;

      editable = b;
      titleField.setEditable(b);
      durationField.setEditable(b);
      detailsPanel.setEditable(b);

      editedTrack = (editable ? new TrackEntity(currentTrack) : null);
   }

   @Override
   public void saveEdit() {
      if (!editable)
         return;

      saveFields();

      currentTrack.setTitle(editedTrack.getTitle());
      currentTrack.setDuration(editedTrack.getDuration());
      detailsPanel.saveEdit();
   }

   private void saveFields() {
      editedTrack.setTitle(titleField.getText());

      String duration = durationField.getText();
      if (new DurationValidator().validate(duration))
         editedTrack.setDuration(new DurationParser().parse(duration));
   }

   @Override
   public void revert() {
      applyTrackInfo();
      detailsPanel.revert();
   }
}
