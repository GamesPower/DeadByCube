package deadbycube;

import deadbycube.command.CommandManager;
import deadbycube.command.game.CommandGame;
import deadbycube.command.role.CommandRole;
import deadbycube.game.DbcGame;
import deadbycube.gui.GuiManager;
import deadbycube.listener.DbcListener;
import deadbycube.listener.InGameListener;
import deadbycube.listener.LobbyListener;
import deadbycube.player.PlayerList;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class DeadByCube extends JavaPlugin {

    private PlayerList playerList;
    private GuiManager guiManager;
    private CommandManager commandManager;

    private DbcListener listener;
    private DbcGame game;

    @Override
    public void onEnable() {
        this.playerList = new PlayerList();
        this.guiManager = new GuiManager(this);
        this.commandManager = new CommandManager(this);

        this.listener = new LobbyListener(this);

        this.registerListeners();
        this.registerCommands();
    }

    @Override
    public void onDisable() {
        this.stopGame();
        this.saveConfig();
    }

    public void startGame() {
        this.game = new DbcGame(this);
        this.game.start();
        this.setListener(new InGameListener(this));
    }

    public void stopGame() {
        if (this.game != null) {
            this.setListener(new LobbyListener(this));
            this.game.stop();
            this.game = null;
        }
    }

    public boolean isInGame() {
        return game != null && game.isStarted();
    }

    private void setListener(DbcListener listener) {
        HandlerList.unregisterAll(this.listener);
        getServer().getPluginManager().registerEvents(this.listener = listener, this);
    }

    private void registerListeners() {
        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(guiManager, this);
        pluginManager.registerEvents(listener, this);
    }

    private void registerCommands() {
        commandManager.register(new CommandGame(this));
        commandManager.register(new CommandRole(this));
    }

    public PlayerList getPlayerList() {
        return playerList;
    }

}
