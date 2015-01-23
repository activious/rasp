package ui;

public class State {
   public static final int
         VIEW_ALBUMS = 1,
         VIEW_ARTISTS = 2,
         SEARCH_LOCAL = 1,
         SEARCH_ONLINE = 2;
   
   private int view;
   private int searchScope;
   private boolean detailedView;
   
   public State() {
      view = VIEW_ALBUMS;
      searchScope = SEARCH_LOCAL;
      detailedView = false;
   }
   
   public int getView() {
      return view;
   }
   
   public void setView(int view) {
      this.view = view;
   }
   
   public int getSearchScope() {
      return searchScope;
   }
   
   public void setSearchScope(int scope) {
      searchScope = scope;
   }
   
   public boolean isDetailedView() {
      return detailedView;
   }
   
   public void setDetailedView(boolean b) {
      detailedView = b;
   }
}
