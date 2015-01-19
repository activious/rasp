package ui.album;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

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
   JButton btnOk, btnCancel;
   JScrollPane scroller;

   public AlbumDialog(JFrame owner) {
      this(owner, new AlbumEntity());
   }

   public AlbumDialog(JFrame owner, AlbumEntity album) {
      super(owner, (album.isPersisted() ? "Rediger" : "Opret") + " album", true);
      setSize(600, 500);
      setLocationRelativeTo(owner);
      setLayout(new BorderLayout());

      initComponents();
      layoutComponents();

      setAlbum(album);
   }

   private void initComponents() {
      detailsPanel = new AlbumDetailsPanel();

      trackList = new JPanel();
      trackList.setLayout(new BoxLayout(trackList, BoxLayout.Y_AXIS));
      
      btnOk = ComponentFactory.createDefaultButton("OK");
      btnOk.addActionListener(e -> {});
      
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
      int i = 1;
      for (TrackEntity t : currentAlbum.getTrackList()) {
         ListItemPanel<TrackPanel> p = new ListItemPanel<>(new TrackPanel(t));
         p.setNumber(i++);
         trackList.add(p);
      }
   }

   public void saveEdit() {
      detailsPanel.saveEdit();
   }
}
