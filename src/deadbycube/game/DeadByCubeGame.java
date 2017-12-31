package deadbycube.game;

import deadbycube.DeadByCube;
import deadbycube.game.interaction.InteractionManager;
import deadbycube.player.DeadByCubePlayer;
import deadbycube.player.PlayerList;
import deadbycube.player.spectator.Spectator;
import deadbycube.world.DeadByCubeWorld;
import deadbycube.world.generator.DeadByCubeWorldGenerator;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;


public class DeadByCubeGame {

    private final DeadByCube plugin;
    private final DeadByCubeWorld world;
    private final InteractionManager interactionManager;

    public DeadByCubeGame(DeadByCube plugin) {
        this.plugin = plugin;
        this.world = new DeadByCubeWorldGenerator(plugin).generate();
        this.interactionManager = new InteractionManager(this);
    }

    public void start() {
        World world = this.world.getWorld();
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.teleport(world.getSpawnLocation());

            PlayerList playerList = plugin.getPlayerList();
            DeadByCubePlayer deadByCubePlayer = playerList.getPlayer(player);
            if (deadByCubePlayer == null) {
                deadByCubePlayer = new Spectator(plugin, player);
                playerList.setPlayer(player, deadByCubePlayer);
            }
            deadByCubePlayer.init();
        }
    }

    public void stop() {
        Location spawnLocation = plugin.getLobbyWorld().getSpawnLocation();
        PlayerList playerList = plugin.getPlayerList();
        for (Player player : Bukkit.getOnlinePlayers()) {
            DeadByCubePlayer deadByCubePlayer = playerList.getPlayer(player);
            deadByCubePlayer.reset();
            playerList.removePlayer(player);

            player.setFoodLevel(20);
            player.setWalkSpeed(0.2f);
            player.getInventory().clear();

            player.teleport(spawnLocation);
        }

        this.world.unload();
    }

    public InteractionManager getInteractionManager() {
        return interactionManager;
    }

}
