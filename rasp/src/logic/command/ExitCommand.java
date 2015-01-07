package logic.command;

import util.function.Command;

public class ExitCommand implements Command {
   @Override
   public void execute() {
      System.exit(0);
   }
}
