package ui.editor;

public interface Editor {
   boolean isEditable();
   void setEditable(boolean b);
   void saveEdit();
   void revert();
}
