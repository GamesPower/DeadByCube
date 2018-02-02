package deadbycube.listener;

import deadbycube.DeadByCube;
import org.bukkit.event.Listener;

public abstract class DeadByCubeListener implements Listener {

    protected final DeadByCube plugin;

    protected DeadByCubeListener(DeadByCube plugin) {
        this.plugin = plugin;
    }

}
