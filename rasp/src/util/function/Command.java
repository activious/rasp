package util.function;

@FunctionalInterface
public interface Command {
   void execute() throws Exception;
}
