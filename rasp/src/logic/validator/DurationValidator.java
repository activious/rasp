package logic.validator;

public class DurationValidator {
   public boolean validate(String duration) {
      // Allow "123" (seconds) and "12:34"
      return duration.trim().matches("\\d+|\\d+[:.][0-5][0-9]");
   }
}
