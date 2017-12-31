package deadbycube.eventhandler;

import deadbycube.DeadByCube;
import deadbycube.player.PlayerList;
import deadbycube.player.spectator.Spectator;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.*;

public class LobbyEventHandler extends EventHandler {

    public LobbyEventHandler(DeadByCube plugin) {
        super(plugin);
    }

    @Override
    public void onEntityDamage(EntityDamageEvent event) {
        event.setCancelled(true);
    }

    @Override
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.setJoinMessage(null);

        Player player = event.getPlayer();
        player.setGameMode(GameMode.ADVENTURE);

        PlayerList playerList = plugin.getPlayerList();
        playerList.setPlayer(player, new Spectator(plugin, player));
    }

    @Override
    public void onPlayerQuit(PlayerQuitEvent event) {
        event.setQuitMessage(null);
    }

    @Override
    public void onPlayerInteract(PlayerInteractEvent event) {
    }

    @Override
    public void onPlayerToggleSprint(PlayerToggleSprintEvent event) {
    }

    @Override
    public void onPlayerSwapHandItems(PlayerSwapHandItemsEvent event) {
        event.setCancelled(true);
    }

    @Override
    public void onPlayerMove(PlayerMoveEvent event) {
    }

}
