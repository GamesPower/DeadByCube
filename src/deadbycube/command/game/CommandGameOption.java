package deadbycube.command.game;

import deadbycube.command.CommandManager;
import deadbycube.command.function.FunctionInfo;
import deadbycube.command.node.CommandNode;
import org.bukkit.command.CommandSender;

class CommandGameOption extends CommandNode {

    CommandGameOption(CommandManager commandManager) {
        super(commandManager, "option");
    }

    @FunctionInfo(name = "generator", requiredArgs = 1)
    private void generator(CommandSender commandSender, Integer generator) {
    }

}
