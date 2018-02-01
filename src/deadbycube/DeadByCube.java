package deadbycube;

import deadbycube.audio.WorldAudioManager;
import deadbycube.command.CommandManager;
import deadbycube.eventhandler.EventHandler;
import deadbycube.eventhandler.InGameEventHandler;
import deadbycube.eventhandler.LobbyEventHandler;
import deadbycube.game.DeadByCubeGame;
import deadbycube.game.GameStatus;
import deadbycube.listener.EntityListener;
import deadbycube.listener.PlayerListener;
import deadbycube.player.PlayerList;
import deadbycube.structure.StructureManager;
import deadbycube.util.NMSUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class DeadByCube extends JavaPlugin {

    private static DeadByCube instance;

    public static Plugin getInstance() {
        return instance;
    }

    public static int getCurrentTick() {
        return (int) NMSUtils.getStaticField("MinecraftServer", "currentTick");
    }

    private final PlayerList playerList = new PlayerList();
    private final CommandManager commandManager = new CommandManager(this);
    private final StructureManager structureManager = new StructureManager(this);
    private final WorldAudioManager audioManager = new WorldAudioManager(this);
    private World lobbyWorld;

    private DeadByCubeGame game;
    private EventHandler eventHandler;

    @Override
    public void onEnable() {
        DeadByCube.instance = this;

        this.saveDefaultConfig();

        this.lobbyWorld = Bukkit.getWorld(getConfig().getString("world.lobby.name", Bukkit.getWorlds().get(0).getName()));

        this.eventHandler = new LobbyEventHandler(this);

        this.registerListeners();
        this.commandManager.registerCommands();

        /* LOBBY SNOW GENERATOR

        Random random = new Random();
        getServer().getScheduler().runTaskTimer(this, () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                for (int i = 0; i < 25; i++) {
                    int x = random.nextInt(40) - 20;
                    int y = random.nextInt(18) + 2;
                    int z = random.nextInt(40) - 20;

                    Location location = player.getLocation();
                    player.spawnParticle(
                            Particle.FALLING_DUST,
                            location.getX() + x, player.getWorld().getHighestBlockYAt(x, z) + y, location.getZ() + z, 1,
                            0, 0, 0,
                            1, new MaterialData(Material.SNOW)
                    );
                }

            }
        }, 0L, 0L);*/
    }

    @Override
    public void onDisable() {
        if (getStatus() == GameStatus.IN_GAME)
            this.stopGame();
        this.saveConfig();
    }

    private void registerListeners() {
        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new PlayerListener(this), this);
        pluginManager.registerEvents(new EntityListener(this), this);
    }

    public void startGame() {
        this.eventHandler = new InGameEventHandler(this);
        this.game = new DeadByCubeGame(this);
        this.game.start();
    }

    public void stopGame() {
        this.eventHandler = new LobbyEventHandler(this);
        this.game.stop();
        this.game = null;
    }

    public GameStatus getStatus() {
        return game == null ? GameStatus.LOBBY : GameStatus.IN_GAME;
    }

    public EventHandler getEventHandler() {
        return eventHandler;
    }

    public StructureManager getStructureManager() {
        return structureManager;
    }

    public WorldAudioManager getAudioManager() {
        return audioManager;
    }

    public World getLobbyWorld() {
        return lobbyWorld;
    }

    public DeadByCubeGame getGame() {
        return game;
    }

    public PlayerList getPlayerList() {
        return playerList;
    }
}
