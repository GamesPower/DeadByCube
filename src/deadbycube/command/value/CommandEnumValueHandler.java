package deadbycube.command.value;

import deadbycube.command.exception.CommandParseException;

import java.util.ArrayList;
import java.util.List;

public class CommandEnumValueHandler extends CommandValueHandler<Enum> {

    public CommandEnumValueHandler() {
        super(Enum.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public Enum fromString(Class<Enum> enumClass, String value) throws CommandParseException {
        try {
            return Enum.valueOf(enumClass, value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new CommandParseException(e);
        }
    }

    @Override
    public List<String> tabComplete(Class<Enum> enumClass) {
        ArrayList<String> stringList = new ArrayList<>();
        for (Enum t : enumClass.getEnumConstants())
            stringList.add(t.name().toLowerCase());
        return stringList;
    }
}
