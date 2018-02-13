package deadbycube.command.exception;

import net.md_5.bungee.api.chat.BaseComponent;

public class CommandExecutionException extends CommandException {

    public CommandExecutionException(String message) {
        super(message);
    }

    public CommandExecutionException(BaseComponent error) {
        super(error);
    }

}
