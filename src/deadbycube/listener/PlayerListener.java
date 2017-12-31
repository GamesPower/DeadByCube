package deadbycube.listener;

import deadbycube.DeadByCube;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.*;

public class PlayerListener extends DeadByCubeListener {

    public PlayerListener(DeadByCube plugin) {
        super(plugin);
    }

    @EventHandler
    private void onPlayerJoin(PlayerJoinEvent event) {
        plugin.getEventHandler().onPlayerJoin(event);
    }

    @EventHandler
    private void onPlayerQuit(PlayerQuitEvent event) {
        plugin.getEventHandler().onPlayerQuit(event);
    }

    @EventHandler
    private void onPlayerInteract(PlayerInteractEvent event) {
        plugin.getEventHandler().onPlayerInteract(event);
    }

    @EventHandler
    private void onPlayerToggleSprint(PlayerToggleSprintEvent event) {
        plugin.getEventHandler().onPlayerToggleSprint(event);
    }

    @EventHandler
    private void onPlayerSwapHandItems(PlayerSwapHandItemsEvent event) {
        plugin.getEventHandler().onPlayerSwapHandItems(event);
    }

    @EventHandler
    private void onPlayerDropItem(PlayerDropItemEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    private void onPlayerMove(PlayerMoveEvent event) {
        plugin.getEventHandler().onPlayerMove(event);
    }

}
