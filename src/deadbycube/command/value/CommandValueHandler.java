package deadbycube.command.value;

import deadbycube.command.exception.CommandParseException;

import java.util.List;

public abstract class CommandValueHandler<T> {

    private final Class<T> tClass;

    CommandValueHandler(Class<T> tClass) {
        this.tClass = tClass;
    }

    public abstract T fromString(Class<T> tClass, String value) throws CommandParseException;

    public abstract List<String> tabComplete(Class<T> tClass);

    public Class<T> getTClass() {
        return tClass;
    }
}
