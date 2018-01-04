package deadbycube.command.game;

import deadbycube.DeadByCube;
import deadbycube.command.Command;
import deadbycube.command.exception.CommandExecutionException;
import deadbycube.command.function.FunctionInfo;
import deadbycube.util.GameStatus;
import org.bukkit.command.CommandSender;

public class CommandGame extends Command {

    public CommandGame(DeadByCube plugin) {
        super(plugin, "game", new CommandGameOption(plugin));
    }

    @FunctionInfo(name = "start")
    private void start(CommandSender commandSender) {
        this.plugin.startGame();
    }

    @FunctionInfo(name = "stop")
    private void stop(CommandSender commandSender) throws CommandExecutionException {
        if (plugin.getStatus() != GameStatus.IN_GAME)
            throw new CommandExecutionException("The game isn't started");
        this.plugin.stopGame();
    }

}
