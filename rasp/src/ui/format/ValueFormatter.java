package ui.format;

import java.util.ArrayList;
import java.util.List;

import domain.HasName;

public class ValueFormatter {
   public static String formatNamesOf(List<? extends HasName> elements) {
      List<String> names = new ArrayList<>();
      for (HasName e : elements)
         names.add(e.getName());

      return String.join(", ", names);
   }
   
   public static String formatDuration(int seconds) {
      int s = (seconds % 60);
      return (seconds / 60) + ":" + (s < 10 ? "0" + s : s);
   }
}
