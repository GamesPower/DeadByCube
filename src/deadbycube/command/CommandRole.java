package deadbycube.command;

import deadbycube.DeadByCube;
import deadbycube.player.PlayerManager;
import deadbycube.player.killer.KillerDebug;
import deadbycube.player.killer.KillerNurse;
import deadbycube.player.killer.KillerShape;
import deadbycube.player.killer.KillerWraith;
import deadbycube.player.survivor.Survivor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class CommandRole extends AbstractCommand {

    public CommandRole(DeadByCube plugin) {
        super(plugin, "role");
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        if (!(commandSender instanceof Player) || args.length < 1)
            return false;

        Player player = (Player) commandSender;

        String role = args[0];
        switch (role) {
            case "killer":
                if (args.length < 2)
                    return false;

                String killer = args[1];

                switch (killer) {
                    case "debug":
                        player.sendMessage("Killer set to debug");
                        PlayerManager.setPlayer(player, new KillerDebug(plugin, player));
                        break;
                    case "nurse":
                        player.sendMessage("Killer set to nurse");
                        PlayerManager.setPlayer(player, new KillerNurse(plugin, player));
                        break;
                    case "wraith":
                        player.sendMessage("Killer set to wraith");
                        PlayerManager.setPlayer(player, new KillerWraith(plugin, player));
                        break;
                    case "shape":
                        player.sendMessage("Killer set to shape");
                        PlayerManager.setPlayer(player, new KillerShape(plugin, player));
                        break;
                    default:
                        player.sendMessage("Invalid killer");
                        break;
                }

                return true;
            case "survivor":
                player.sendMessage("Killer set to survivor");
                PlayerManager.setPlayer(player, new Survivor(plugin, player));
                return true;
            case "list":
                player.sendMessage(PlayerManager.getPlayers().toString());
            default:
                return false;
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return null;
    }


}
