package ui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.FocusEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import ui.event.ItemAddedListener;
import ui.event.ItemEvent;
import ui.event.ItemRemovedListener;

public class TextValueField<T> extends ValueComponent<T> {
   private static final long serialVersionUID = 1L;

   private boolean editable, canDelete, canAdd;

   private final int columns;
   private final Function<T, String> representer;

   private JLabel lblStatic;
   private JTextField tfEditable;
   private JButton btnAdd, btnDelete;

   private List<ItemAddedListener<String>> addListeners;
   private List<ItemRemovedListener<T>> removeListeners;

   public TextValueField(int columns, boolean editable, boolean canAdd,
                         boolean canDelete, Function<T, String> representer) {
      this.columns = columns;
      this.editable = editable;
      this.canAdd = canAdd;
      this.canDelete = canDelete;
      this.representer = representer;

      addListeners = new ArrayList<>();
      removeListeners = new ArrayList<>();

      setOpaque(false);
      setLayout(new FlowLayout(FlowLayout.LEFT, 8, 0));

      initComponents();
      layoutComponents();
   }

   public void addItemAddedListener(ItemAddedListener<String> listener) {
      addListeners.add(listener);
   }

   public void removeItemAddedListener(ItemAddedListener<String> listener) {
      addListeners.remove(listener);
   }

   public void addItemRemovedListener(ItemRemovedListener<T> listener) {
      removeListeners.add(listener);
   }

   public void removeItemRemovedListener(ItemRemovedListener<T> listener) {
      removeListeners.remove(listener);
   }

   private void initComponents() {
      lblStatic = ComponentFactory.createDefaultLabel("");
      tfEditable = ComponentFactory.createDefaultTextField(columns);

      btnAdd = ComponentFactory.createDefaultButton("+");
      btnAdd.setMargin(new Insets(0, 2, 0, 2));
      btnAdd.setPreferredSize(new Dimension(22, 22));
      btnAdd.addActionListener(e -> {
         ItemEvent<String> event = new ItemEvent<>(this, tfEditable.getText());
         for (ItemAddedListener<String> l : addListeners)
            l.itemAdded(event);
      });

      btnDelete = ComponentFactory.createDefaultButton("–");
      btnDelete.setMargin(new Insets(0, 2, 0, 2));
      btnDelete.setPreferredSize(new Dimension(22, 22));
      btnDelete.addActionListener(e -> {
         ItemEvent<T> event = new ItemEvent<>(this, getValue());
         for (ItemRemovedListener<T> l : removeListeners)
            l.itemRemoved(event);
      });
   }

   private void layoutComponents() {
      removeAll();

      if (editable) {
         add(tfEditable);
         if (canAdd)
            add(btnAdd);
      } else {
         add(lblStatic);
         if (canDelete)
            add(btnDelete);
      }
   }

   public void setCanDelete(boolean b) {
      canDelete = b;
   }

   public void update() {
      layoutComponents();
   }

   @Override
   public void setValue(T value) {
      super.setValue(value);
      if (value != null) {
         lblStatic.setText(representer.apply(value));
         tfEditable.setText(representer.apply(value));
      }
   }
   
   public String getText() {
      return tfEditable.getText();
   }

   public void setBold(boolean b) {
      lblStatic.setFont(b
            ? ComponentFactory.DEFAULT_BOLD_FONT
            : ComponentFactory.DEFAULT_PLAIN_FONT);
   }

   public void setAlignment(int align) {
      tfEditable.setHorizontalAlignment(align);
   }

   public boolean isEditable() {
      return editable;
   }

   public void setEditable(boolean editable) {
      if (this.editable == editable)
         return;

      this.editable = editable;

      if (editable && getValue() != null)
         tfEditable.setText(representer.apply(getValue()));

      removeAll();
      layoutComponents();
   }

   @Override
   public void processFocusEvent(FocusEvent e) {
      if (editable)
         tfEditable.requestFocusInWindow();
   }

   @Override
   public boolean requestFocusInWindow() {
      return tfEditable.requestFocusInWindow();
   }
}
