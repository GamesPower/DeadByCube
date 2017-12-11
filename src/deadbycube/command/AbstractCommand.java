package deadbycube.command;

import deadbycube.DeadByCube;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.TabCompleter;

abstract class AbstractCommand implements CommandExecutor, TabCompleter {

    final DeadByCube plugin;
    private final String name;

    AbstractCommand(DeadByCube plugin, String name) {
        this.plugin = plugin;
        this.name = name;
    }

    String getName() {
        return name;
    }

}
