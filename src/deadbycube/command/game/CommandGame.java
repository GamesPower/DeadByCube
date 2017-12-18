package deadbycube.command.game;

import deadbycube.DeadByCube;
import deadbycube.command.Command;
import deadbycube.command.function.FunctionInfo;
import deadbycube.command.value.CommandIntValue;
import org.bukkit.entity.Player;

public class CommandGame extends Command {

    public CommandGame(DeadByCube plugin) {
        super(plugin, "game", new CommandGameOption(plugin));
    }

    @FunctionInfo(name = "start")
    private void start(Player player, CommandIntValue generator) {
        player.sendMessage("Start the game with " + generator.getValue() + " generator");
        this.plugin.startGame();
    }

    @FunctionInfo(name = "stop")
    private void stop(Player player) {
        this.plugin.stopGame();
    }

}
