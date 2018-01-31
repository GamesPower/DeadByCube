package deadbycube.command;

import deadbycube.command.exception.CommandExecutionException;
import deadbycube.command.function.FunctionInfo;
import deadbycube.player.DeadByCubePlayer;
import deadbycube.player.PlayerList;
import deadbycube.player.PlayerType;
import deadbycube.player.survivor.SurvivorPlayer;
import deadbycube.player.survivor.heartbeat.HeartbeatEmitter;
import deadbycube.player.survivor.heartbeat.HeartbeatManager;
import deadbycube.util.MagicalValue;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

class CommandHeartbeat extends Command {

    CommandHeartbeat(CommandManager commandManager) {
        super(commandManager, "heartbeat");
    }

    @FunctionInfo(name = "register", requiredArgs = 1)
    private void register(CommandSender commandSender, double radius) throws CommandExecutionException {

        Player player = getPlayer(commandSender);
        PlayerList playerList = plugin.getPlayerList();
        DeadByCubePlayer deadByCubePlayer = playerList.getPlayer(player);

        if (deadByCubePlayer == null || deadByCubePlayer.getType() != PlayerType.SURVIVOR)
            throw new CommandExecutionException("Invalid dbc player");

        SurvivorPlayer survivor = (SurvivorPlayer) deadByCubePlayer;
        HeartbeatManager heartbeatManager = survivor.getHeartbeatManager();
        heartbeatManager.registerHeartbeatEmitter(new HeartbeatEmitter() {

            private final Location location = player.getLocation();
            private final MagicalValue terrorRadius = new MagicalValue(radius);

            @Override
            public Location getLocation() {
                return location;
            }

            @Override
            public MagicalValue getTerrorRadius() {
                return terrorRadius;
            }
        });
    }

}
