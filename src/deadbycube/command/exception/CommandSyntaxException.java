package deadbycube.command.exception;

public class CommandSyntaxException extends CommandException {

    public CommandSyntaxException(String message) {
        super(message);
    }

    CommandSyntaxException(Throwable cause) {
        super(cause);
    }

}
