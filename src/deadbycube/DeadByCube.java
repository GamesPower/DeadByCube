package deadbycube;

import deadbycube.audio.AudioManager;
import deadbycube.command.CommandManager;
import deadbycube.command.game.CommandGame;
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
    private final AudioManager audioManager = new AudioManager(this);

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

    public AudioManager getAudioManager() {
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
