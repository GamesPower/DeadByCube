package deadbycube.command;

import deadbycube.DeadByCube;
import deadbycube.player.DbcPlayer;
import deadbycube.player.PlayerManager;
import deadbycube.player.PlayerType;
import deadbycube.player.killer.Killer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class CommandKiller extends AbstractCommand {

    public CommandKiller(DeadByCube plugin) {
        super(plugin, "killer");
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (!(commandSender instanceof Player) || args.length < 1)
            return false;

        Player player = (Player) commandSender;
        DbcPlayer dbcPlayer = PlayerManager.getPlayer(player);
        if (dbcPlayer == null || !dbcPlayer.getType().equals(PlayerType.KILLER))
            return false;

        Killer killer = (Killer) dbcPlayer;

        switch (args[0]) {
            case "stun":
                killer.stun();
                return true;
            default:
                return false;
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return null;
    }
}
