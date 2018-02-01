package deadbycube.game;

import deadbycube.DeadByCube;
import deadbycube.game.attack.AttackManager;
import deadbycube.player.DeadByCubePlayer;
import deadbycube.player.PlayerList;
import deadbycube.player.PlayerType;
import deadbycube.player.killer.KillerPlayer;
import deadbycube.player.spectator.SpectatorPlayer;
import deadbycube.player.survivor.SurvivorPlayer;
import deadbycube.player.survivor.heartbeat.HeartbeatManager;
import deadbycube.util.Tickable;
import deadbycube.world.DeadByCubeWorld;
import deadbycube.world.generator.DeadByCubeWorldGenerator;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.List;


public class DeadByCubeGame {

    private final DeadByCube plugin;
    private final DeadByCubeWorld world;
    private final AttackManager attackManager;

    public DeadByCubeGame(DeadByCube plugin) {
        this.plugin = plugin;
        this.world = new DeadByCubeWorldGenerator(plugin).generate();
        this.attackManager = new AttackManager(this);
    }

    public void start() {
        World world = this.world.getWorld();

        PlayerList playerList = plugin.getPlayerList();
        List<KillerPlayer> killerList = playerList.getKillers();
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.teleport(world.getSpawnLocation());
            player.setSprinting(false);
            player.setSneaking(false);

            DeadByCubePlayer deadByCubePlayer = playerList.getPlayer(player);
            if (deadByCubePlayer == null) {
                deadByCubePlayer = new SpectatorPlayer(plugin, player);
                playerList.setPlayer(player, deadByCubePlayer);
            } else if (deadByCubePlayer.getType() == PlayerType.SURVIVOR) {
                SurvivorPlayer survivor = (SurvivorPlayer) deadByCubePlayer;

                HeartbeatManager heartbeatManager = survivor.getHeartbeatManager();
                killerList.forEach(heartbeatManager::registerHeartbeatEmitter);
            }

            deadByCubePlayer.init();
        }
    }

    public void stop() {
        BukkitScheduler scheduler = plugin.getServer().getScheduler();
        scheduler.cancelTasks(plugin);

        Location spawnLocation = plugin.getLobbyWorld().getSpawnLocation();
        PlayerList playerList = plugin.getPlayerList();
        for (Player player : Bukkit.getOnlinePlayers()) {
            playerList.resetPlayer(player);

            player.setFoodLevel(20);
            player.setWalkSpeed(0.2f);
            player.getInventory().clear();

            player.teleport(spawnLocation);
        }

        this.world.unload();
    }

    public AttackManager getAttackManager() {
        return attackManager;
    }
}
