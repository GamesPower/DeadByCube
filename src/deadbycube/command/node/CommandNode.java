package deadbycube.command.node;

import deadbycube.DeadByCube;
import deadbycube.command.CommandManager;
import deadbycube.command.exception.CommandExecutionException;
import deadbycube.command.function.CommandFunction;
import deadbycube.command.function.FunctionInfo;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ProxiedCommandSender;
import org.bukkit.entity.Player;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

public abstract class CommandNode {

    protected final DeadByCube plugin;
    private final String name;
    private final CommandNode[] commandNodes;
    private final ArrayList<CommandFunction> commandFunctions = new ArrayList<>();

    public CommandNode(CommandManager commandManager, String name, CommandNode... commandNodes) {
        this.plugin = commandManager.getPlugin();
        this.name = name;
        this.commandNodes = commandNodes;

        for (Method method : this.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(FunctionInfo.class)) {
                method.setAccessible(true);

                Class<?>[] parameterTypes = method.getParameterTypes();
                if (parameterTypes.length == 0)
                    throw new IllegalArgumentException("Missing parameters");
                if (parameterTypes[0] != CommandSender.class)
                    throw new IllegalArgumentException("Invalid command function player parameter");

                for (int i = 1; i < parameterTypes.length; i++) {
                    if (!commandManager.isValidValue(parameterTypes[i]))
                        throw new IllegalArgumentException("Invalid command parameter: " + parameterTypes[i].getName());
                }

                FunctionInfo functionInfo = method.getAnnotation(FunctionInfo.class);
                commandFunctions.add(new CommandFunction(
                        commandManager, this,
                        functionInfo.name(),
                        Arrays.copyOfRange(parameterTypes, 1, parameterTypes.length),
                        method
                ));
            }
        }
    }

    protected Player getPlayer(CommandSender commandSender) throws CommandExecutionException {
        if(commandSender instanceof ProxiedCommandSender)
            return getPlayer(((ProxiedCommandSender)commandSender).getCallee());
        if (!(commandSender instanceof Player))
            throw new CommandExecutionException("The command executor need to be player");
        return (Player) commandSender;
    }

    protected void sendMessage(CommandSender commandSender, String message, ChatColor chatColor) {
        TextComponent textComponent = new TextComponent(message);
        textComponent.setColor(chatColor);
        commandSender.spigot().sendMessage(textComponent);
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
