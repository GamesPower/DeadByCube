package deadbycube.command;

import deadbycube.DeadByCube;
import deadbycube.command.function.CommandFunction;
import deadbycube.command.node.CommandNode;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class Command extends CommandNode implements CommandExecutor, TabCompleter {

    public Command(DeadByCube plugin, String name, CommandNode... commandNodes) {
        super(plugin, name, commandNodes);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, org.bukkit.command.Command command, String commandName, String[] args) {
        if (!(commandSender instanceof Player))
            return false;

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
            if (function != null && function.match(arguments))
                return function.execute((Player) commandSender, arguments);

            break;
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, org.bukkit.command.Command command, String commandName, String[] args) {



        return Collections.singletonList("Soon™");
    }

}
