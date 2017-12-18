package deadbycube.listener;

import deadbycube.DeadByCube;
import deadbycube.player.lobby.LobbyPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class LobbyListener extends DbcListener {

    public LobbyListener(DeadByCube plugin) {
        super(plugin);
    }

    @EventHandler
    private void onPlayerJoin(PlayerJoinEvent event) {
        event.setJoinMessage(null);

        Player player = event.getPlayer();
        plugin.getPlayerList().setPlayer(player, new LobbyPlayer(plugin, player));
    }

    @EventHandler
    private void onPlayerQuit(PlayerQuitEvent event) {
        event.setQuitMessage(null);
    }

}
