package deadbycube.listener;

import deadbycube.DeadByCube;
import org.bukkit.event.Listener;

abstract class DeadByCubeListener implements Listener {

    final DeadByCube plugin;

    DeadByCubeListener(DeadByCube plugin) {
        this.plugin = plugin;
    }

}
