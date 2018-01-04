package deadbycube.command.function;

import deadbycube.command.exception.CommandException;
import deadbycube.command.exception.CommandExecutionException;
import deadbycube.command.exception.CommandParseException;
import deadbycube.command.exception.CommandSyntaxException;
import deadbycube.command.node.CommandNode;
import org.bukkit.command.CommandSender;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class CommandFunction {

    private final CommandNode commandNode;
    private final String name;
    private final Class<?>[] parametersClass;
    private final Method method;

    public CommandFunction(CommandNode commandNode, String name, Class<?>[] parametersClass, Method method) {
        this.commandNode = commandNode;
        this.name = name;
        this.parametersClass = parametersClass;
        this.method = method;
    }

    public boolean execute(CommandSender commandSender, String[] arguments) throws CommandException {
        if (arguments.length < parametersClass.length)
            throw new CommandSyntaxException("Missing parameter(s)");

        ArrayList<Object> objectList = new ArrayList<>();
        objectList.add(commandSender);
        for (int i = 0; i < parametersClass.length; i++) {
            try {
                Constructor<?> constructor = parametersClass[i].getConstructor(String.class);
                objectList.add(constructor.newInstance(arguments[i]));
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
                throw new CommandParseException(e);
            }
        }

        try {
            method.invoke(commandNode, objectList.toArray(new Object[objectList.size()]));
            return true;
        } catch (IllegalAccessException | InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause != null) {
                if (cause instanceof CommandExecutionException)
                    throw (CommandExecutionException) cause;
                else
                    throw new CommandExecutionException(cause.getMessage());
            } else
                throw new CommandExecutionException(e.getMessage());
        }
    }

    public String getName() {
        return name;
    }

}
