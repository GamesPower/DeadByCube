package deadbycube;

import deadbycube.command.CommandGui;
import deadbycube.command.CommandKiller;
import deadbycube.command.CommandManager;
import deadbycube.command.CommandRole;
import deadbycube.gui.GuiManager;
import deadbycube.player.PlayerManager;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class DeadByCube extends JavaPlugin {

    private PlayerManager playerManager;
    private GuiManager guiManager;
    private CommandManager commandManager;

    @Override
    public void onEnable() {
        this.playerManager = new PlayerManager();
        this.guiManager = new GuiManager(this);
        this.commandManager = new CommandManager(this);

        this.registerListeners();
        this.registerCommands();
    }

    @Override
    public void onDisable() {
    }

    private void registerListeners() {
        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(playerManager, this);
        pluginManager.registerEvents(guiManager, this);
    }

    private void registerCommands() {
        commandManager.register(new CommandGui(this));
        commandManager.register(new CommandRole(this));
        commandManager.register(new CommandKiller(this));
    }

    public GuiManager getGuiManager() {
        return guiManager;
    }
}
