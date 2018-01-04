package deadbycube.command.exception;

public class CommandParseException extends CommandException {

    public CommandParseException(String message) {
        super(message);
    }

    public CommandParseException(Throwable cause) {
        super(cause);
    }

}
