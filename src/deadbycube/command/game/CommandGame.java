package deadbycube.command.game;

import deadbycube.command.Command;
import deadbycube.command.CommandManager;
import deadbycube.command.exception.CommandExecutionException;
import deadbycube.command.function.FunctionInfo;
import deadbycube.game.GameStatus;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.CommandSender;

public class CommandGame extends Command {

    public CommandGame(CommandManager commandManager) {
        super(commandManager, "game", new CommandGameOption(commandManager));
    }

    @FunctionInfo(name = "start", requiredArgs = 0)
    private void start(CommandSender commandSender) throws CommandExecutionException {
        if (plugin.getStatus() == GameStatus.IN_GAME)
            throw new CommandExecutionException("The game is already started");
        this.plugin.startGame();

        this.sendMessage(commandSender, "The game will start soon™", ChatColor.GREEN);
    }

    @FunctionInfo(name = "stop", requiredArgs = 0)
    private void stop(CommandSender commandSender) throws CommandExecutionException {
        if (plugin.getStatus() != GameStatus.IN_GAME)
            throw new CommandExecutionException("The game isn't started");
        this.plugin.stopGame();
    }

}
