package ui.event;

import java.util.EventObject;

public class ItemEvent<T> extends EventObject {
   private static final long serialVersionUID = 1L;

   T item;
   
   public ItemEvent(Object source, T item) {
      super(source);
      this.item = item;
   }
   
   public T getItem() {
      return item;
   }
}
