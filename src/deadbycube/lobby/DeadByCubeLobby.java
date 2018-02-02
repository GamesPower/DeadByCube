package deadbycube.lobby;

import deadbycube.DeadByCube;
import deadbycube.DeadByCubeHandler;
import deadbycube.game.GameStatus;
import deadbycube.listener.lobby.LobbyEntityListener;
import deadbycube.listener.lobby.LobbyPlayerListener;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.PluginManager;
import org.bukkit.scheduler.BukkitScheduler;

public class DeadByCubeLobby implements DeadByCubeHandler {

    private final DeadByCube plugin;
    private final World world;

    private final LobbyEntityListener entityListener;
    private final LobbyPlayerListener playerListener;

    public DeadByCubeLobby(DeadByCube plugin) {
        this.plugin = plugin;

        Server server = plugin.getServer();
        this.world = server.getWorld(plugin.getConfig().getString("world.lobby.name", server.getWorlds().get(0).getName()));

        this.entityListener = new LobbyEntityListener(plugin);
        this.playerListener = new LobbyPlayerListener(plugin);
    }

    @Override
    public void init() {
        PluginManager pluginManager = plugin.getServer().getPluginManager();
        pluginManager.registerEvents(entityListener, plugin);
        pluginManager.registerEvents(playerListener, plugin);
    }

    @Override
    public void reset() {
        BukkitScheduler scheduler = plugin.getServer().getScheduler();
        scheduler.cancelTasks(plugin);

        HandlerList.unregisterAll(entityListener);
        HandlerList.unregisterAll(playerListener);
    }

    @Override
    public GameStatus getStatus() {
        return GameStatus.LOBBY;
    }

    @Override
    public World getWorld() {
        return world;
    }

}
