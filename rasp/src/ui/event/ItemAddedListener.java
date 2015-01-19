package ui.event;

@FunctionalInterface
public interface ItemAddedListener<T> {
   void itemAdded(ItemEvent<T> item);
}
