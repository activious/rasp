package ui.edit;

public interface Editor {
   boolean isEditable();
   void setEditable(boolean b);
   void saveEdit();
   void revert();
}
