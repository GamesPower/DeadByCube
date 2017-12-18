package deadbycube.command.role;

import deadbycube.DeadByCube;
import deadbycube.command.Command;
import deadbycube.command.function.FunctionInfo;
import deadbycube.command.value.CommandStringValue;
import deadbycube.player.PlayerList;
import deadbycube.player.killer.KillerDoctor;
import deadbycube.player.killer.KillerNurse;
import deadbycube.player.killer.KillerShape;
import deadbycube.player.killer.KillerWraith;
import deadbycube.player.survivor.Survivor;
import org.bukkit.entity.Player;

public class CommandRole extends Command {

    public CommandRole(DeadByCube plugin) {
        super(plugin, "role");
    }

    @FunctionInfo(name = "survivor")
    private void survivor(Player player) {
        if(plugin.isInGame()) {
            player.sendMessage("ERR_IN_GAME");
            return;
        }

        plugin.getPlayerList().setPlayer(player, new Survivor(plugin, player));
        player.sendMessage("Player set to survivor");
    }

    @FunctionInfo(name = "killer")
    private void killer(Player player, CommandStringValue killer) {
        if(plugin.isInGame()) {
            player.sendMessage("ERR_IN_GAME");
            return;
        }

        String killerName = killer.getValue().toLowerCase();
        PlayerList playerList = plugin.getPlayerList();
        switch (killerName) {
            case "nurse":
                playerList.setPlayer(player, new KillerNurse(plugin, player));
                break;
            case "doctor":
                playerList.setPlayer(player, new KillerDoctor(plugin, player));
                break;
            case "shape":
                playerList.setPlayer(player, new KillerShape(plugin, player));
                break;
            case "wraith":
                playerList.setPlayer(player, new KillerWraith(plugin, player));
                break;
            default:
                return;
        }
        player.sendMessage("Player set to " + killerName);
    }

    @FunctionInfo(name = "list")
    private void list(Player player) {
        player.sendMessage(plugin.getPlayerList().getPlayers().toString());
    }

}