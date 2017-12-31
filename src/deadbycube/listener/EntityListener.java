package deadbycube.listener;

import deadbycube.DeadByCube;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityShootBowEvent;

public class EntityListener extends DeadByCubeListener {

    public EntityListener(DeadByCube plugin) {
        super(plugin);
    }

    @EventHandler
    private void onEntityShootBow(EntityShootBowEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    private void onEntityDamage(EntityDamageEvent event) {
        plugin.getEventHandler().onEntityDamage(event);
    }

}
