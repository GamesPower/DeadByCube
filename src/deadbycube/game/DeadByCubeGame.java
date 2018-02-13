package deadbycube.game;

import deadbycube.DeadByCube;
import deadbycube.DeadByCubeHandler;
import deadbycube.listener.ingame.InGameEntityListener;
import deadbycube.listener.ingame.InGameInventoryListener;
import deadbycube.listener.ingame.InGamePlayerListener;
import deadbycube.player.DeadByCubePlayer;
import deadbycube.player.PlayerList;
import deadbycube.player.PlayerType;
import deadbycube.player.killer.KillerPlayer;
import deadbycube.player.spectator.SpectatorPlayer;
import deadbycube.player.survivor.SurvivorPlayer;
import deadbycube.player.survivor.heartbeat.HeartbeatManager;
import deadbycube.world.DeadByCubeWorld;
import deadbycube.world.generator.DeadByCubeWorldGenerator;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.PluginManager;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.List;


public class DeadByCubeGame implements DeadByCubeHandler {

    private final DeadByCube plugin;
    private final DeadByCubeWorld world;

    private final InGameEntityListener entityListener;
    private final InGamePlayerListener playerListener;
    private final InGameInventoryListener inventoryListener;

    public DeadByCubeGame(DeadByCube plugin) {
        this.plugin = plugin;
        this.world = new DeadByCubeWorldGenerator(plugin).generate();

        this.entityListener = new InGameEntityListener(plugin);
        this.playerListener = new InGamePlayerListener(plugin);
        this.inventoryListener = new InGameInventoryListener(plugin);
    }

    @Override
    public void init() {
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

        PluginManager pluginManager = plugin.getServer().getPluginManager();
        pluginManager.registerEvents(entityListener, plugin);
        pluginManager.registerEvents(playerListener, plugin);
        pluginManager.registerEvents(inventoryListener, plugin);
    }

    @Override
    public void reset(DeadByCubeHandler newHandler) {
        BukkitScheduler scheduler = plugin.getServer().getScheduler();
        scheduler.cancelTasks(plugin);

        HandlerList.unregisterAll(entityListener);
        HandlerList.unregisterAll(playerListener);
        HandlerList.unregisterAll(inventoryListener);

        Location spawnLocation = newHandler.getWorld().getSpawnLocation();
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

    @Override
    public GameStatus getStatus() {
        return GameStatus.IN_GAME;
    }

    @Override
    public World getWorld() {
        return world.getWorld();
    }

}
