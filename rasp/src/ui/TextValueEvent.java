package ui;

import java.util.EventObject;

public class TextValueEvent<T> extends EventObject {
   private static final long serialVersionUID = 1L;

   private final String text;
   private final T value;
   
   public TextValueEvent(Object source, String text, T value) {
      super(source);
      this.text = text;
      this.value = value;
   }
   
   public String getText() {
      return text;
   }
   
   public T getValue() {
      return value;
   }
}
