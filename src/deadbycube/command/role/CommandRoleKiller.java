package deadbycube.command.role;

import deadbycube.DeadByCube;
import deadbycube.command.exception.CommandExecutionException;
import deadbycube.command.function.FunctionInfo;
import deadbycube.command.node.CommandNode;
import deadbycube.player.killer.*;
import deadbycube.util.GameStatus;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

class CommandRoleKiller extends CommandNode {

    CommandRoleKiller(DeadByCube plugin) {
        super(plugin, "killer");
    }

    @FunctionInfo(name = "shape")
    private void shape(CommandSender commandSender) throws CommandExecutionException {
        this.checkIfInGame();

        Player player = getPlayer(commandSender);
        plugin.getPlayerList().setPlayer(player, new KillerShape(plugin, player));
        this.sendMessage(commandSender, "Your role has been set to The Shape", ChatColor.GREEN);
    }

    @FunctionInfo(name = "nurse")
    private void nurse(CommandSender commandSender) throws CommandExecutionException {
        this.checkIfInGame();

        Player player = getPlayer(commandSender);
        plugin.getPlayerList().setPlayer(player, new KillerNurse(plugin, player));
        this.sendMessage(commandSender, "Your role has been set to The Nurse", ChatColor.GREEN);
    }

    @FunctionInfo(name = "doctor")
    private void doctor(CommandSender commandSender) throws CommandExecutionException {
        this.checkIfInGame();

        Player player = getPlayer(commandSender);
        plugin.getPlayerList().setPlayer(player, new KillerDoctor(plugin, player));
        this.sendMessage(commandSender, "Your role has been set to The Doctor", ChatColor.GREEN);
    }

    @FunctionInfo(name = "wraith")
    private void wraith(CommandSender commandSender) throws CommandExecutionException {
        this.checkIfInGame();

        Player player = getPlayer(commandSender);
        plugin.getPlayerList().setPlayer(player, new KillerWraith(plugin, player));
        this.sendMessage(commandSender, "Your role has been set to The Wraith", ChatColor.GREEN);
    }

    @FunctionInfo(name = "trapper")
    private void trapper(CommandSender commandSender) throws CommandExecutionException {
        this.checkIfInGame();

        Player player = getPlayer(commandSender);
        plugin.getPlayerList().setPlayer(player, new KillerTrapper(plugin, player));
        this.sendMessage(commandSender, "Your role has been set to The Trapper", ChatColor.GREEN);
    }

    private void checkIfInGame() throws CommandExecutionException {
        if (plugin.getStatus() == GameStatus.IN_GAME)
            throw new CommandExecutionException("You can't change a role in game");
    }

}
