package deadbycube.listener.ingame;

import deadbycube.DeadByCube;
import deadbycube.listener.DeadByCubeListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryOpenEvent;

public class InGameInventoryListener extends DeadByCubeListener {

    public InGameInventoryListener(DeadByCube plugin) {
        super(plugin);
    }

    @EventHandler
    private void onInventoryOpen(InventoryOpenEvent event) {
        event.setCancelled(true);
    }

}
