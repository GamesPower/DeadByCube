package deadbycube.command.value;

import org.apache.commons.lang.math.NumberUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.function.Function;

public enum CommandValueType {

    STRING(CommandStringValue.class, s -> true),
    BOOL(CommandBoolValue.class, s -> s.equals("true") || s.equals("false")),
    INT(CommandIntValue.class, NumberUtils::isNumber);

    private final Class<? extends CommandValue> valueClass;
    private final Function<String, Boolean> function;

    CommandValueType(Class<? extends CommandValue> valueClass, Function<String, Boolean> function) {
        this.valueClass = valueClass;
        this.function = function;
    }

    public CommandValue newInstance(String value) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        CommandValue commandValue = valueClass.getConstructor().newInstance();
        commandValue.parse(value);
        return commandValue;
    }

    public boolean match(String str) {
        return function.apply(str);
    }

    public static CommandValueType fromValue(Class<?> valueClass) {
        for (CommandValueType valueType : CommandValueType.values()) {
            if (valueType.valueClass.equals(valueClass))
                return valueType;
        }
        throw new IllegalArgumentException("Invalid command value type");
    }

}
