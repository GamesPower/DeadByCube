package deadbycube.command.role;

import deadbycube.DeadByCube;
import deadbycube.command.Command;
import deadbycube.command.exception.CommandExecutionException;
import deadbycube.command.function.FunctionInfo;
import deadbycube.player.killer.KillerShape;
import deadbycube.player.survivor.Survivor;
import deadbycube.util.GameStatus;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandRole extends Command {

    public CommandRole(DeadByCube plugin) {
        super(plugin, "role", new CommandRoleKiller(plugin));
    }

    @FunctionInfo(name = "survivor")
    private void survivor(CommandSender commandSender) throws CommandExecutionException {
        this.checkIfInGame();

        Player player = getPlayer(commandSender);

        plugin.getPlayerList().setPlayer(player, new Survivor(plugin, player));
        player.sendMessage("Player set to survivor");
    }

    @FunctionInfo(name = "set")
    private void set(CommandSender commandSender, String username) throws CommandExecutionException {
        this.checkIfInGame();

        Player targetPlayer = Bukkit.getPlayer(username);
        if (targetPlayer == null) {
            throw new CommandExecutionException("The target player cannot be found");
        }

        plugin.getPlayerList().setPlayer(targetPlayer, new Survivor(plugin, targetPlayer));
    }

    @FunctionInfo(name = "list")
    private void list(CommandSender commandSender) throws CommandExecutionException {
        Player player = getPlayer(commandSender);
        player.sendMessage(plugin.getPlayerList().getPlayers().toString());
    }

    private void checkIfInGame() throws CommandExecutionException {
        if (plugin.getStatus() == GameStatus.IN_GAME)
            throw new CommandExecutionException("You can't change a role in game");
    }

}