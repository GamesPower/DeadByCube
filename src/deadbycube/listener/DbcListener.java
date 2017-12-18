package deadbycube.listener;

import deadbycube.DeadByCube;
import org.bukkit.event.Listener;

public abstract class DbcListener implements Listener {

    final DeadByCube plugin;

    DbcListener(DeadByCube plugin) {
        this.plugin = plugin;
    }

}
