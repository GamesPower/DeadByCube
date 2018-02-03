package deadbycube.listener.lobby;

import deadbycube.DeadByCube;
import deadbycube.audio.PlayerAudioManager;
import deadbycube.audio.music.MusicManager;
import deadbycube.audio.music.MusicRegistry;
import deadbycube.listener.DeadByCubeListener;
import deadbycube.player.PlayerList;
import deadbycube.player.spectator.SpectatorPlayer;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class LobbyPlayerListener extends DeadByCubeListener {

    public LobbyPlayerListener(DeadByCube plugin) {
        super(plugin);
    }

    @EventHandler
    private void onPlayerJoin(PlayerJoinEvent event) {
        event.setJoinMessage(null);

        Player player = event.getPlayer();
        player.setGameMode(GameMode.ADVENTURE);
        player.teleport(plugin.getHandler().getWorld().getSpawnLocation());

        PlayerList playerList = plugin.getPlayerList();
        SpectatorPlayer spectator = new SpectatorPlayer(plugin, player);
        playerList.setPlayer(player, spectator);

        PlayerAudioManager audioManager = spectator.getAudioManager();
        MusicManager musicManager = audioManager.getMusicManager();
        musicManager.setMusics(MusicRegistry.LOBBY_NORMAL);
    }

    @EventHandler
    private void onPlayerQuit(PlayerQuitEvent event) {
        event.setQuitMessage(null);

        PlayerList playerList = plugin.getPlayerList();
        playerList.removePlayer(event.getPlayer());
    }

    @EventHandler
    private void onFoodLevelChange(FoodLevelChangeEvent event) {
        event.setCancelled(true);
    }

}
