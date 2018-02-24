package deadbycube.command.role;

import deadbycube.command.Command;
import deadbycube.command.CommandManager;
import deadbycube.command.exception.CommandExecutionException;
import deadbycube.command.function.FunctionInfo;
import deadbycube.game.GameStatus;
import deadbycube.player.DeadByCubePlayer;
import deadbycube.player.PlayerList;
import deadbycube.player.killer.KillerPlayer;
import deadbycube.registry.KillerRegistry;
import deadbycube.player.survivor.SurvivorPlayer;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ProxiedCommandSender;
import org.bukkit.entity.Player;

public class CommandRole extends Command {

    public CommandRole(CommandManager commandManager) {
        super(commandManager, "role");
    }

    @FunctionInfo(name = "survivor", requiredArgs = 0)
    private void survivor(CommandSender commandSender) throws CommandExecutionException {
        this.checkIfInGame();

        Player player = getPlayer(commandSender);

        plugin.getPlayerList().setPlayer(player, new SurvivorPlayer(plugin, player));
        this.sendMessage(commandSender, "Your role has been set to survivor", ChatColor.GREEN);
    }

    @FunctionInfo(name = "killer", requiredArgs = 1)
    private void killer(CommandSender commandSender, KillerRegistry killerRegistry) throws CommandExecutionException {
        System.out.println("commandSender = " + commandSender);

        this.checkIfInGame();

        Player player = getPlayer(commandSender);

        KillerPlayer killer = killerRegistry.create(plugin, player);
        plugin.getPlayerList().setPlayer(player, killer);
        this.sendMessage(commandSender, "Your role has been set to " + killer.getName(), ChatColor.GREEN);
    }

    @FunctionInfo(name = "set", requiredArgs = 1)
    private void set(CommandSender commandSender, Player player) throws CommandExecutionException {
        this.checkIfInGame();
        plugin.getPlayerList().setPlayer(player, new SurvivorPlayer(plugin, player));
        this.sendMessage(commandSender, "The " + player.getDisplayName() + "'s role has been set to survivor", ChatColor.GREEN);
    }

    @FunctionInfo(name = "info", requiredArgs = 0)
    private void info(CommandSender commandSender) throws CommandExecutionException {
        Player player = this.getPlayer(commandSender);
        PlayerList playerList = plugin.getPlayerList();
        DeadByCubePlayer deadByCubePlayer = playerList.getPlayer(player);

        this.sendMessage(commandSender, "Your role is set to " + deadByCubePlayer.toString() + " (" + deadByCubePlayer.getType() + ")", ChatColor.GREEN);
    }

    @FunctionInfo(name = "list", requiredArgs = 0)
    private void list(CommandSender commandSender) throws CommandExecutionException {
        Player player = getPlayer(commandSender);
        player.sendMessage(plugin.getPlayerList().getPlayers().toString());
    }

    private void checkIfInGame() throws CommandExecutionException {
        if (plugin.getHandler().getStatus() == GameStatus.IN_GAME)
            throw new CommandExecutionException("You can't change a role in game");
    }

}