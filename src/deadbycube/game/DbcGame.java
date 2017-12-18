package deadbycube.game;

import deadbycube.DeadByCube;
import deadbycube.player.DbcPlayer;
import deadbycube.player.PlayerList;
import deadbycube.player.lobby.LobbyPlayer;
import deadbycube.player.spectator.Spectator;
import deadbycube.world.DbcWorld;
import deadbycube.world.generator.WorldGenerator;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class DbcGame {

    private final DeadByCube plugin;
    private final DbcWorld world;
    private boolean started = false;

    public DbcGame(DeadByCube plugin) {
        this.plugin = plugin;
        this.world = new WorldGenerator(plugin).generate();
    }

    public void start() {
        this.started = true;

        PlayerList playerList = plugin.getPlayerList();

        for (Player player : Bukkit.getOnlinePlayers()) {
            DbcPlayer dbcPlayer = playerList.getPlayer(player);
            if (dbcPlayer == null) {
                dbcPlayer = new Spectator(plugin, player);
                playerList.setPlayer(player, dbcPlayer);
            }
            dbcPlayer.init();
        }
    }

    public void stop() {
        PlayerList playerList = plugin.getPlayerList();

        for (Player player : Bukkit.getOnlinePlayers()) {
            DbcPlayer dbcPlayer = playerList.getPlayer(player);
            if (dbcPlayer != null)
                dbcPlayer.reset();
            playerList.setPlayer(player, new LobbyPlayer(plugin, player));
        }

        Bukkit.unloadWorld(world.getWorld(), false);

        this.started = false;
    }

    public boolean isStarted() {
        return started;
    }
}
