package ui;

import java.awt.Font;
import java.awt.Insets;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

public class ComponentFactory {
   private static final int TEXT_FIELD_PADDING = 3;
   
   public static final Font DEFAULT_PLAIN_FONT = new Font("SansSerif", Font.PLAIN, 16);
   public static final Font DEFAULT_BOLD_FONT = new Font("SansSerif", Font.BOLD, 16);

   public static JTextField createDefaultTextField(int columns) {
      JTextField tf = new JTextField(columns);
      tf.setFont(DEFAULT_PLAIN_FONT);
      tf.setBorder(BorderFactory.createCompoundBorder(tf.getBorder(),
                                                      BorderFactory.createEmptyBorder(TEXT_FIELD_PADDING,
                                                                                      TEXT_FIELD_PADDING,
                                                                                      TEXT_FIELD_PADDING,
                                                                                      TEXT_FIELD_PADDING)));
      return tf;
   }
   
   public static JButton createDefaultButton(String text) {
      JButton btn = new JButton(text);
      initButton(btn);
      return btn;
   }
   
   public static JToggleButton createDefaultToggleButton(String text) {
      JToggleButton btn = new JToggleButton(text);
      initButton(btn);
      return btn;
   }
   
   public static JLabel createDefaultLabel(String text) {
      return createDefaultLabel(text, false);
   }
   
   public static JLabel createDefaultLabel(String text, boolean bold) {
      JLabel lbl = new JLabel(text);
      lbl.setFont(bold ? DEFAULT_BOLD_FONT : DEFAULT_PLAIN_FONT);
      return lbl;
   }
   
   private static void initButton(AbstractButton btn) {
      btn.setFont(DEFAULT_PLAIN_FONT);
      btn.setMargin(new Insets(5,12,5,12));
   }
}
