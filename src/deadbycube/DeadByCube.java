package deadbycube;

import deadbycube.audio.WorldAudioManager;
import deadbycube.command.CommandManager;
import deadbycube.command.game.CommandGame;
import deadbycube.command.interaction.CommandInteraction;
import deadbycube.command.role.CommandRole;
import deadbycube.command.structure.CommandStructure;
import deadbycube.eventhandler.EventHandler;
import deadbycube.eventhandler.InGameEventHandler;
import deadbycube.eventhandler.LobbyEventHandler;
import deadbycube.game.DeadByCubeGame;
import deadbycube.listener.EntityListener;
import deadbycube.listener.PlayerListener;
import deadbycube.player.PlayerList;
import deadbycube.structure.StructureManager;
import deadbycube.util.GameStatus;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class DeadByCube extends JavaPlugin {

    private final PlayerList playerList = new PlayerList();
    private final CommandManager commandManager = new CommandManager(this);
    private final StructureManager structureManager = new StructureManager(this);
    private final WorldAudioManager audioManager = new WorldAudioManager(this);

    private World lobbyWorld;
    private GameStatus status;
    private DeadByCubeGame game;
    private EventHandler eventHandler;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();

        this.lobbyWorld = Bukkit.getWorld(getConfig().getString("world.lobby.name", Bukkit.getWorlds().get(0).getName()));

        this.status = GameStatus.LOBBY;
        this.eventHandler = new LobbyEventHandler(this);

        this.registerListeners();
        this.registerCommands();

        /*Random random = new Random();
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
        if (status == GameStatus.IN_GAME)
            this.stopGame();
        this.saveConfig();
    }

    private void registerListeners() {
        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new PlayerListener(this), this);
        pluginManager.registerEvents(new EntityListener(this), this);
    }

    private void registerCommands() {
        commandManager.register(new CommandGame(this));
        commandManager.register(new CommandStructure(this));
        commandManager.register(new CommandRole(this));
        commandManager.register(new CommandInteraction(this));
    }

    public void startGame() {
        this.status = GameStatus.IN_GAME;
        this.eventHandler = new InGameEventHandler(this);
        this.game = new DeadByCubeGame(this);
        this.game.start();
    }

    public void stopGame() {
        this.status = GameStatus.LOBBY;
        this.eventHandler = new LobbyEventHandler(this);
        this.game.stop();
        this.game = null;
    }

    public GameStatus getStatus() {
        return status;
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
