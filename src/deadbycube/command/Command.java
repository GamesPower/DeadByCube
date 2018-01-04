package deadbycube.command;

import deadbycube.DeadByCube;
import deadbycube.command.exception.CommandException;
import deadbycube.command.exception.CommandExecutionException;
import deadbycube.command.exception.CommandSyntaxException;
import deadbycube.command.function.CommandFunction;
import deadbycube.command.node.CommandNode;
import net.md_5.bungee.api.chat.TextComponent;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

public abstract class Command extends CommandNode implements CommandExecutor, TabCompleter {

    public Command(DeadByCube plugin, String name, CommandNode... commandNodes) {
        super(plugin, name, commandNodes);
    }

    private TextComponent generateSyntax(CommandSender commandSender, org.bukkit.command.Command command, String commandName, String[] args) {
        // TODO Create a real syntax generator system
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < args.length - 1; i++)
            builder.append(' ').append(args[i]);

        List<String> arguments = this.onTabComplete(commandSender, command, commandName, (String[]) ArrayUtils.add(args, ""));
        builder.append(' ');
        if (arguments.size() > 1)
            builder.append('<');
        for (int i = 0; i < arguments.size(); i++) {
            builder.append(args[i]);
            if (i < arguments.size() - 1)
                builder.append('|');
        }
        if (arguments.size() > 1)
            builder.append('>');

        return new TextComponent("Syntax error: /" + commandName + builder.toString());
    }

    @Override
    public boolean onCommand(CommandSender commandSender, org.bukkit.command.Command command, String commandName, String[] args) {
        CommandNode node = this;
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];

            CommandNode commandNode = node.getNode(arg);
            if (commandNode != null) {
                node = commandNode;
                continue;
            }

            CommandFunction function = node.getFunction(arg);
            String[] arguments = i + 1 < args.length ? Arrays.copyOfRange(args, i + 1, args.length) : new String[0];
            try {
                if (function != null)
                    return function.execute(commandSender, arguments);
            } catch (CommandSyntaxException e) {
                commandSender.spigot().sendMessage(generateSyntax(commandSender, command, commandName, args));
                return true;
            } catch (CommandExecutionException e) {
                commandSender.spigot().sendMessage(e.getError());
                return true;
            } catch (CommandException e) {
                commandSender.spigot().sendMessage(e.getError());
                plugin.getLogger().log(Level.WARNING, e.getMessage(), e);
                return true;
            }
        }

        commandSender.spigot().sendMessage(generateSyntax(commandSender, command, commandName, args));
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, org.bukkit.command.Command command, String commandName, String[] args) {
        ArrayList<String> arrayList = new ArrayList<>();

        CommandNode commandNode = this;
        boolean updated = true;
        for (String arg : args) {
            arrayList.clear();

            if (!updated)
                break;
            updated = false;

            for (CommandNode node : commandNode.getNodes()) {
                String nodeName = node.getName();
                if (nodeName.equals(arg)) {
                    commandNode = node;
                    updated = true;
                    break;
                } else if (nodeName.startsWith(arg))
                    arrayList.add(nodeName);
            }

            for (CommandFunction function : commandNode.getFunctions()) {
                String functionName = function.getName();
                if (functionName.startsWith(arg))
                    arrayList.add(functionName);
            }
        }

        return arrayList;
    }

}
