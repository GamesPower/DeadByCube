package deadbycube.listener.ingame;

import deadbycube.DeadByCube;
import deadbycube.listener.DeadByCubeListener;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityShootBowEvent;

public class InGameEntityListener extends DeadByCubeListener {

    public InGameEntityListener(DeadByCube plugin) {
        super(plugin);
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntityType() == EntityType.PLAYER)
            event.setCancelled(true);
    }

}
