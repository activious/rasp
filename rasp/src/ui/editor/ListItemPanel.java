package ui.editor;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import ui.ComponentFactory;

public class ListItemPanel<E extends JComponent & Editor> extends JPanel {
   private static final long serialVersionUID = 1L;

   private JLabel numberLabel;
   private JPanel bottomPanel;
   private JButton btnEdit, btnOk, btnCancel;
   private final E editor;
   
   private List<ActionListener> okListeners;
   private List<ActionListener> cancelListeners;

   public ListItemPanel(E editor) {
      this.editor = editor;

      setLayout(new GridBagLayout());

      okListeners = new ArrayList<>();
      cancelListeners = new ArrayList<>();

      initComponents();
      layoutComponents();

      // setBackground(new Color(255,100,100));
   }
   
   public void addOkListener(ActionListener l) {
      okListeners.add(l);
   }
   
   public void addCancelListener(ActionListener l) {
      cancelListeners.add(l);
   }
   
   private void initComponents() {
      numberLabel = ComponentFactory.createDefaultLabel("");
      bottomPanel = new JPanel();
      bottomPanel.setOpaque(false);
      bottomPanel.setVisible(false);

      btnEdit = ComponentFactory.createDefaultButton("E");
      btnEdit.addActionListener(e -> {
         setEditable(true);
      });

      btnOk = ComponentFactory.createDefaultButton("OK");
      btnOk.addActionListener(e -> {
         editor.saveEdit();
         setEditable(false);
         editor.revert();
         
         for (ActionListener l : okListeners)
            l.actionPerformed(e);
      });

      btnCancel = ComponentFactory.createDefaultButton("Annuller");
      btnCancel.addActionListener(e -> {
         setEditable(false);
         editor.revert();
         
         for (ActionListener l : cancelListeners)
            l.actionPerformed(e);
      });
   }

   private void layoutComponents() {
      GridBagConstraints gbc = new GridBagConstraints();

      gbc.weightx = 0.0;
      gbc.gridx = 0;
      gbc.gridy = 0;
      gbc.anchor = GridBagConstraints.FIRST_LINE_END;
      gbc.insets = new Insets(10, 10, 0, 0);
      add(numberLabel, gbc);

      gbc.insets = new Insets(10, 0, 0, 0);
      gbc.weightx = 1.0;
      gbc.fill = GridBagConstraints.HORIZONTAL;
      gbc.anchor = GridBagConstraints.FIRST_LINE_START;
      gbc.gridx++;
      add(editor, gbc);

      gbc.insets = new Insets(10, 10, 0, 10);
      gbc.weightx = 0.0;
      gbc.gridx++;
      add(btnEdit, gbc);

      bottomPanel.add(btnOk);
      bottomPanel.add(btnCancel);

      gbc.insets = new Insets(10, 0, 10, 0);
      gbc.gridx = 0;
      gbc.gridy++;
      gbc.gridwidth = 3;
      gbc.anchor = GridBagConstraints.CENTER;
      add(bottomPanel, gbc);
   }

   public void setNumber(int n) {
      numberLabel.setText(n + ".");
   }

   public void setEditable(boolean b) {
      editor.setEditable(b);
      bottomPanel.setVisible(b);

      btnEdit.setEnabled(!b);
      setBackground(b ? new Color(200, 255, 200) : null);
   }
}
