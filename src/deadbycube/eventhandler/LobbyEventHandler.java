package deadbycube.eventhandler;

import deadbycube.DeadByCube;
import deadbycube.audio.PlayerAudioManager;
import deadbycube.audio.music.MusicManager;
import deadbycube.audio.music.MusicRegistry;
import deadbycube.player.PlayerList;
import deadbycube.player.spectator.SpectatorPlayer;
import org.bukkit.GameMode;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.*;

public class LobbyEventHandler extends EventHandler {

    public LobbyEventHandler(DeadByCube plugin) {
        super(plugin);
    }

    @Override
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntityType() == EntityType.PLAYER)
            event.setCancelled(true);
    }

    @Override
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.setJoinMessage(null);

        Player player = event.getPlayer();
        player.setGameMode(GameMode.ADVENTURE);

        PlayerList playerList = plugin.getPlayerList();
        SpectatorPlayer spectator = new SpectatorPlayer(plugin, player);
        playerList.setPlayer(player, spectator);

        PlayerAudioManager audioManager = spectator.getAudioManager();
        MusicManager musicManager = audioManager.getMusicManager();
        musicManager.setMusics(MusicRegistry.LOBBY_WINTER);
    }

    @Override
    public void onPlayerQuit(PlayerQuitEvent event) {
        event.setQuitMessage(null);

        PlayerList playerList = plugin.getPlayerList();
        playerList.removePlayer(event.getPlayer());
    }

    @Override
    public void onPlayerMove(PlayerMoveEvent event) {
    }

    @Override
    public void onPlayerInteract(PlayerInteractEvent event) {
    }

    @Override
    public void onPlayerToggleSprint(PlayerToggleSprintEvent event) {
    }

    @Override
    public void onPlayerToggleSneak(PlayerToggleSneakEvent event) {
    }

    @Override
    public void onPlayerSwapHandItems(PlayerSwapHandItemsEvent event) {
    }

    @Override
    public void onPlayerDropItem(PlayerDropItemEvent event) {
    }

}
