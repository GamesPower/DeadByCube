package deadbycube.command.function;

import deadbycube.command.node.CommandNode;
import deadbycube.command.value.CommandValueType;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class CommandFunction {

    private final CommandNode commandNode;
    private final String name;
    private final CommandValueType[] valueTypes;
    private final Method method;

    public CommandFunction(CommandNode commandNode, String name, CommandValueType[] valueTypes, Method method) {
        this.commandNode = commandNode;
        this.name = name;
        this.valueTypes = valueTypes;
        this.method = method;
    }

    public boolean execute(Player player, String[] arguments) {
        if (arguments.length < valueTypes.length)
            return false;

        ArrayList<Object> objectList = new ArrayList<>();
        for (int i = 0; i < valueTypes.length; i++) {
            try {
                objectList.add(valueTypes[i].newInstance(arguments[i]));
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
                player.sendMessage("ERR_COMMAND_PROCESS");
                return true;
            }
        }

        objectList.add(0, player);

        try {
            method.invoke(commandNode, objectList.toArray(new Object[objectList.size()]));
            return true;
        } catch (IllegalAccessException | InvocationTargetException e) {
            player.sendMessage("ERR_COMMAND_PROCESS");
            return true;
        }
    }

    public boolean match(String[] arguments) {
        for (int i = 0; i < valueTypes.length; i++) {
            if (i >= arguments.length)// not enough arguments
                return false;
            if (!valueTypes[i].match(arguments[i])) // argument didn't match with the type
                return false;
        }
        return true;
    }

    public String getName() {
        return name;
    }

    public CommandValueType[] getValueTypes() {
        return valueTypes;
    }

    public Method getMethod() {
        return method;
    }

}
