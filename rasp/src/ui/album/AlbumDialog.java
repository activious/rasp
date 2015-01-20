package ui.album;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import java.util.ArrayList;
import java.util.List;


import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;



import ui.ComponentFactory;
import ui.TrackPanel;
import ui.list.ListItemPanel;
import domain.AlbumEntity;
import domain.TrackEntity;

public class AlbumDialog extends JDialog {
   private static final long serialVersionUID = 1L;

   AlbumEntity currentAlbum, editedAlbum;

   AlbumDetailsPanel detailsPanel;
   JPanel trackList;
   List<ListItemPanel<TrackPanel>> trackItems;
   JButton btnAddTrack, btnOk, btnCancel;
   JScrollPane scroller;

   public AlbumDialog(JFrame owner) {
      this(owner, new AlbumEntity());
   }

   public AlbumDialog(JFrame owner, AlbumEntity album) {
      super(owner, (album.isPersisted() ? "Rediger" : "Opret") + " album", true);
      setSize(600, 500);
      setLocationRelativeTo(owner);
      setLayout(new BorderLayout());

      trackItems = new ArrayList<>();
      
      initComponents();
      layoutComponents();

      setAlbum(album);
   }

   private void initComponents() {
      detailsPanel = new AlbumDetailsPanel();

      trackList = new JPanel();
      trackList.setLayout(new BoxLayout(trackList, BoxLayout.Y_AXIS));
      
      btnAddTrack = ComponentFactory.createDefaultButton("Tilføj track");
      btnAddTrack.addActionListener(e -> {
         TrackEntity track = new TrackEntity();
         editedAlbum.addTrack(track);
         updateTrackList();
         
         ListItemPanel<TrackPanel> item = trackItems.get(trackItems.size() - 1);
         item.setEditable(true);
         item.addCancelListener(event -> {
            editedAlbum.removeTrack(track);
            updateTrackList();
         });
         item.addOkListener(event -> {
            updateTrackList();
         });
      });
      
      btnOk = ComponentFactory.createDefaultButton("OK");
      btnOk.addActionListener(e -> {
      });
      
      btnCancel = ComponentFactory.createDefaultButton("Annuller");
      btnCancel.addActionListener(e -> {
         dispose();
      });
      
      scroller = new JScrollPane(trackList);
   }

   private void layoutComponents() {
      add(detailsPanel, BorderLayout.NORTH);
      add(scroller, BorderLayout.CENTER);
      
      JPanel p = new JPanel();
      p.setLayout(new FlowLayout(FlowLayout.RIGHT));
      p.add(btnAddTrack);
      p.add(btnOk);
      p.add(btnCancel);
      add(p, BorderLayout.SOUTH);
   }

   private void setAlbum(AlbumEntity album) {
      currentAlbum = album;
      editedAlbum = new AlbumEntity(album);

      detailsPanel.setAlbum(album);
      updateTrackList();
   }
   
   private void updateTrackList() {
      trackList.removeAll();
      trackItems.clear();
      
      int i = 1;
      for (TrackEntity t : editedAlbum.getTrackList()) {
         ListItemPanel<TrackPanel> p = new ListItemPanel<>(new TrackPanel(t));
         p.setNumber(i++);
         trackList.add(p);
         trackItems.add(p);
      }
      
      if (trackList.getComponents().length == 0)
         trackList.add(ComponentFactory.createDefaultLabel("Track list is empty"));
      
      trackList.repaint();
      trackList.revalidate();
   }

   public void saveEdit() {
      detailsPanel.saveEdit();
   }
}
