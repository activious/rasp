package ui.event;

@FunctionalInterface
public interface ItemRemovedListener<T> {
   void itemRemoved(ItemEvent<T> item);
}
