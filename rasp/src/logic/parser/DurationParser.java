package logic.parser;

public class DurationParser {
   public int parse(String duration) {
      String[] parts = duration.trim().split("[:.]", 2);
      
      if (parts.length == 1)
         return Integer.parseInt(parts[0]);
      else
         return Integer.parseInt(parts[0]) * 60 + Integer.parseInt(parts[1]);
   }
}
