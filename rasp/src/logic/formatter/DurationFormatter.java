package logic.formatter;

public class DurationFormatter {
   public String format(int seconds) {
      int s = (seconds % 60);
      return (seconds / 60) + ":" + (s < 10 ? "0" + s : s);
   }
}
