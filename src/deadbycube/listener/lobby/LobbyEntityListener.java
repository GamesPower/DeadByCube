package deadbycube.listener.lobby;

import deadbycube.DeadByCube;
import deadbycube.listener.DeadByCubeListener;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;

public class LobbyEntityListener extends DeadByCubeListener {

    public LobbyEntityListener(DeadByCube plugin) {
        super(plugin);
    }

    @EventHandler
    private void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntityType() == EntityType.PLAYER)
            event.setCancelled(true);
    }

}
