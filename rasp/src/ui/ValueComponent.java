package ui;

import javax.swing.JPanel;

public class ValueComponent<T> extends JPanel {
   private static final long serialVersionUID = 1L;
   
   private T value;
   
   public T getValue() {
      return value;
   }
   
   public void setValue(T value) {
      this.value = value;
   }
}
