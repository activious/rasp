package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SwingUtilities;

import ui.album.AlbumCover;
import logic.command.FetchAlbumsCommand;
import logic.command.SearchCommand;
import model.Model;
import domain.AlbumEntity;
import domain.ArtistEntity;
import domain.TrackEntity;

public class Window extends JFrame {
   private static Window INSTANCE;
   
   private static final long serialVersionUID = 1L;

   private static final String TITLE = "Rasp v0.1-alpha";

   private JToggleButton btnViewAlbums, btnViewArtists;
   private JToggleButton btnLocalScope, btnOnlineScope;
   private JTextField tfSearch;
   
   private JScrollPane viewPane;
   private JPanel albumList;

   private Window() {
      super(TITLE);
      setLocation(200, 100);
      setSize(880, 650);
      setMinimumSize(new Dimension(760, 400));
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setJMenuBar(new TopMenuBar());

      List<Image> icons = new ArrayList<>();
      icons.add(new ImageIcon("assets/rasp-icon-32.png").getImage());
      icons.add(new ImageIcon("assets/rasp-icon-16.png").getImage());
      setIconImages(icons);

      initComponents();
      layoutComponents();
   }
   
   public static Window getInstance() {
      if (INSTANCE == null)
         INSTANCE = new Window();
      
      return INSTANCE;
   }

   private void initComponents() {
      btnViewAlbums = ComponentFactory.createDefaultToggleButton("Albums");
      btnViewAlbums.addActionListener(e -> {
      });

      btnViewArtists = ComponentFactory.createDefaultToggleButton("Kunstnere");
      btnViewArtists.addActionListener(e -> {
      });

      btnLocalScope = ComponentFactory.createDefaultToggleButton("Mine");
      btnLocalScope.addActionListener(e -> {
         tfSearch.requestFocusInWindow();
      });

      btnOnlineScope = ComponentFactory.createDefaultToggleButton("Online");
      btnOnlineScope.addActionListener(e -> {
         tfSearch.requestFocusInWindow();
      });

      tfSearch = ComponentFactory.createDefaultTextField(18);
      tfSearch.addKeyListener(new KeyAdapter() {
         @Override
         public void keyReleased(KeyEvent e) {
            //if (e.getKeyCode() == KeyEvent.VK_ENTER)
               new SearchCommand(tfSearch.getText()).execute();
         }
      });

      albumList = new JPanel(new GridLayout(0, 4, 8, 8));
      JPanel albumListWrapper = new JPanel();
      albumListWrapper.add(albumList);
      viewPane = new JScrollPane(albumListWrapper, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
   }

   private void layoutComponents() {
      JPanel topPanel = new JPanel();
      topPanel.setLayout(new GridBagLayout());

      GridBagConstraints gbc = new GridBagConstraints();
      gbc.gridx = 0;
      gbc.gridy = 0;

      try {
         BufferedImage imgLogo = ImageIO.read(new File("assets/rasp-logo.png"));
         gbc.insets = new Insets(4, 6, 4, 12);
         topPanel.add(new JLabel(new ImageIcon(imgLogo)), gbc);
         gbc.insets = new Insets(0, 0, 0, 0);
         gbc.gridx++;
      } catch (IOException e) {
         e.printStackTrace();
      }

      JPanel viewSettingsPanel = new JPanel();
      viewSettingsPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 1, 0));
      topPanel.add(viewSettingsPanel, gbc);
      gbc.gridx++;

      viewSettingsPanel.add(ComponentFactory.createDefaultLabel("Vis: "));

      ButtonGroup btnViewGroup = new ButtonGroup();
      btnViewGroup.add(btnViewAlbums);
      btnViewGroup.add(btnViewArtists);

      JPanel pViewGroup = new JPanel();
      viewSettingsPanel.add(pViewGroup);
      pViewGroup.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
      pViewGroup.add(btnViewAlbums, gbc);
      pViewGroup.add(btnViewArtists, gbc);

      JPanel searchPanel = new JPanel();
      searchPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 1, 0));
      gbc.weightx = 1.0;
      gbc.anchor = GridBagConstraints.LINE_END;
      gbc.insets = new Insets(0, 0, 0, 4);
      topPanel.add(searchPanel, gbc);
      
      searchPanel.add(ComponentFactory.createDefaultLabel("Søg: "));

      JPanel pSearch = new JPanel();
      pSearch.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
      searchPanel.add(pSearch);
      
      ButtonGroup grpScope = new ButtonGroup();
      grpScope.add(btnLocalScope);
      grpScope.add(btnOnlineScope);
      
      pSearch.add(btnLocalScope);
      pSearch.add(btnOnlineScope);
      searchPanel.add(tfSearch);

      setLayout(new BorderLayout());
      add(topPanel, BorderLayout.NORTH);
      add(viewPane, BorderLayout.CENTER);
   }
   
   public void updateAlbumList() {
      try {
         new FetchAlbumsCommand().execute();
         displayAlbums(Model.getInstance().getAlbums());
      } catch (SQLException | IOException e) {
         e.printStackTrace();
      }
   }
   
   public void displayAlbums(List<AlbumEntity> albums) {
      albumList.removeAll();
      
      for (AlbumEntity album : albums)
         albumList.add(new AlbumCover(album));
      
      albumList.revalidate();
      albumList.repaint();
   }
}
