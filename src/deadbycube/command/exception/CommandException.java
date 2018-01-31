package deadbycube.command.exception;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;

public class CommandException extends Exception {

    private final BaseComponent error;

    CommandException(String message) {
        this.error = new TextComponent(message);
        this.initStyle();
    }

    CommandException(Throwable cause) {
        super(cause);
        this.error = new TextComponent(cause.getMessage());
        this.initStyle();
    }

    private void initStyle() {
        this.error.setColor(ChatColor.RED);
    }

    public BaseComponent getError() {
        return error;
    }

}
