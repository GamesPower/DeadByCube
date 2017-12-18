package deadbycube.command.node;

import deadbycube.DeadByCube;
import deadbycube.command.function.CommandFunction;
import deadbycube.command.function.FunctionInfo;
import deadbycube.command.value.CommandValueType;
import org.bukkit.entity.Player;

import java.lang.reflect.Method;
import java.util.ArrayList;

public abstract class CommandNode {

    protected final DeadByCube plugin;
    private final String name;
    private final CommandNode[] commandNodes;
    private final ArrayList<CommandFunction> commandFunctions = new ArrayList<>();

    public CommandNode(DeadByCube plugin, String name, CommandNode... commandNodes) {
        this.plugin = plugin;
        this.name = name;
        this.commandNodes = commandNodes;

        for (Method method : this.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(FunctionInfo.class)) {
                method.setAccessible(true);

                Class<?>[] parameterClasses = method.getParameterTypes();
                if (parameterClasses.length == 0)
                    throw new IllegalArgumentException("Missing parameters");
                if (parameterClasses[0] != Player.class)
                    throw new IllegalArgumentException("Invalid command function player parameter");

                CommandValueType[] valueTypes = new CommandValueType[method.getParameterCount() - 1];
                for (int i = 1; i < parameterClasses.length; i++)
                    valueTypes[i - 1] = CommandValueType.fromValue(parameterClasses[i]);

                FunctionInfo functionInfo = method.getAnnotation(FunctionInfo.class);
                commandFunctions.add(new CommandFunction(
                        this,
                        functionInfo.name(),
                        valueTypes,
                        method
                ));
            }
        }
    }

    public CommandNode getNode(String name) {
        for (CommandNode commandNode : commandNodes) {
            if (commandNode.name.equals(name))
                return commandNode;
        }
        return null;
    }

    public CommandFunction getFunction(String name) {
        for (CommandFunction commandFunction : commandFunctions) {
            if (commandFunction.getName().equals(name))
                return commandFunction;
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public CommandNode[] getNodes() {
        return commandNodes;
    }

    public ArrayList<CommandFunction> getFunctions() {
        return commandFunctions;
    }


}
