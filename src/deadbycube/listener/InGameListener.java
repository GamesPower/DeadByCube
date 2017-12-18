package deadbycube.listener;

import deadbycube.DeadByCube;
import deadbycube.player.DbcPlayer;
import deadbycube.player.PlayerType;
import deadbycube.player.spectator.Spectator;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;

public class InGameListener extends DbcListener {

    public InGameListener(DeadByCube plugin) {
        super(plugin);
    }

    @EventHandler
    private void onPlayerJoin(PlayerJoinEvent event) {
        event.setJoinMessage(null);

        Player player = event.getPlayer();
        Spectator spectator = new Spectator(plugin, player);
        plugin.getPlayerList().setPlayer(player, spectator);
    }

    @EventHandler
    private void onPlayerQuit(PlayerQuitEvent event) {
        event.setQuitMessage(null);
        plugin.getPlayerList().removePlayer(event.getPlayer());
        // TODO Kill if he was a survivor and check number of killers/survivors (killers > 0 && survivors > 0)
    }

    @EventHandler
    private void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        DbcPlayer dbcPlayer = plugin.getPlayerList().getPlayer(player);
        switch (event.getAction()) {
            case LEFT_CLICK_AIR:
            case LEFT_CLICK_BLOCK:
                dbcPlayer.getActionHandler().attack();
                break;
            case RIGHT_CLICK_AIR:
            case RIGHT_CLICK_BLOCK:
                dbcPlayer.getActionHandler().interact();
                break;
        }
    }

    @EventHandler
    private void onEntityShootBow(EntityShootBowEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    private void onPlayerToggleSprint(PlayerToggleSprintEvent event) {
        if (event.isSprinting() && plugin.getPlayerList().getPlayer(event.getPlayer()).getType() == PlayerType.KILLER)
            event.setCancelled(true);
    }

}
