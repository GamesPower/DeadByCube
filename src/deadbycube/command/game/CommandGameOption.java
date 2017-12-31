package deadbycube.command.game;

import deadbycube.DeadByCube;
import deadbycube.command.function.FunctionInfo;
import deadbycube.command.node.CommandNode;
import deadbycube.command.value.CommandIntValue;
import org.bukkit.entity.Player;

class CommandGameOption extends CommandNode {

    CommandGameOption(DeadByCube plugin) {
        super(plugin, "option");
    }

    @FunctionInfo(name = "generator")
    private void generator(Player player, CommandIntValue generator) {
    }

}
