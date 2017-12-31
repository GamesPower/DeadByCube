package deadbycube.eventhandler;

import deadbycube.DeadByCube;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.*;

public abstract class EventHandler {

    final DeadByCube plugin;

    EventHandler(DeadByCube plugin) {
        this.plugin = plugin;
    }

    public abstract void onEntityDamage(EntityDamageEvent event);

    public abstract void onPlayerJoin(PlayerJoinEvent player);

    public abstract void onPlayerQuit(PlayerQuitEvent player);

    public abstract void onPlayerInteract(PlayerInteractEvent event);

    public abstract void onPlayerToggleSprint(PlayerToggleSprintEvent event);

    public abstract void onPlayerSwapHandItems(PlayerSwapHandItemsEvent event);

    public abstract void onPlayerMove(PlayerMoveEvent event);

}
