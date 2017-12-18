package deadbycube.command;

import deadbycube.DeadByCube;
import org.bukkit.command.PluginCommand;

public class CommandManager {

    private DeadByCube deadByCube;

    public CommandManager(DeadByCube deadByCube) {
        this.deadByCube = deadByCube;
    }

    public void register(Command command) {
        PluginCommand pluginCommand = deadByCube.getCommand(command.getName());
        pluginCommand.setExecutor(command);
        pluginCommand.setTabCompleter(command);
    }

}
