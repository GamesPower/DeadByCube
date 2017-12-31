package deadbycube.command;

import deadbycube.DeadByCube;
import org.bukkit.command.PluginCommand;

public class CommandManager {

    private DeadByCube plugin;

    public CommandManager(DeadByCube plugin) {
        this.plugin = plugin;
    }

    public void register(Command command) {
        PluginCommand pluginCommand = plugin.getCommand(command.getName());
        pluginCommand.setExecutor(command);
        pluginCommand.setTabCompleter(command);
    }

}
