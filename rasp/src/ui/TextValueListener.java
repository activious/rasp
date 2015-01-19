package ui;

import java.util.EventListener;

public interface TextValueListener<T> extends EventListener {
   void valueAdded(TextValueEvent<T> event);
   
   void valueRemoved(TextValueEvent<T> event);
}
