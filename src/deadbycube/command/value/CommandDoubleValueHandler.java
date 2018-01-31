package deadbycube.command.value;

import deadbycube.command.exception.CommandParseException;

import java.util.List;

public class CommandDoubleValueHandler extends CommandValueHandler<Double>{

    public CommandDoubleValueHandler() {
        super(double.class);
    }

    @Override
    public Double fromString(Class<Double> doubleClass, String value) throws CommandParseException {
        try {
            return Double.valueOf(value);
        } catch (NumberFormatException e) {
            throw new CommandParseException('"' + value + "\" isn't a double");
        }
    }

    @Override
    public List<String> tabComplete(Class<Double> doubleClass) {
        return null;
    }

}
