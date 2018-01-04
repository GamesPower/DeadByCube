package deadbycube.command.game;

import deadbycube.DeadByCube;
import deadbycube.command.function.FunctionInfo;
import deadbycube.command.node.CommandNode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

class CommandGameOption extends CommandNode {

    CommandGameOption(DeadByCube plugin) {
        super(plugin, "option");
    }

    @FunctionInfo(name = "generator")
    private void generator(CommandSender commandSender, Integer generator) {
    }

}
