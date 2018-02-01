package deadbycube.eventhandler;

import deadbycube.DeadByCube;
import deadbycube.interaction.InteractionActionBinding;
import deadbycube.interaction.PlayerInteractionManager;
import deadbycube.player.DeadByCubePlayer;
import deadbycube.player.PlayerList;
import deadbycube.player.PlayerType;
import deadbycube.player.spectator.SpectatorPlayer;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.*;

public class InGameEventHandler extends EventHandler {

    public InGameEventHandler(DeadByCube plugin) {
        super(plugin);
    }

    @Override
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntityType() == EntityType.PLAYER) event.setCancelled(true);
    }

    @Override
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.setJoinMessage(null);

        Player player = event.getPlayer();
        PlayerList playerList = plugin.getPlayerList();
        SpectatorPlayer spectator = new SpectatorPlayer(plugin, player);
        playerList.setPlayer(player, spectator);
        spectator.init();
    }

    @Override
    public void onPlayerQuit(PlayerQuitEvent event) {
        event.setQuitMessage(null);

        PlayerList playerList = plugin.getPlayerList();
        Player player = event.getPlayer();
        DeadByCubePlayer deadByCubePlayer = playerList.getPlayer(player);
        deadByCubePlayer.reset();
        playerList.removePlayer(player);

        // TODO Call the kill method if he was a survivor
        // TODO Check the game requirements (at least 1 killer and 1 survivor)
    }

    @Override
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        DeadByCubePlayer deadByCubePlayer = plugin.getPlayerList().getPlayer(player);
        PlayerInteractionManager interactionManager = deadByCubePlayer.getInteractionManager();
        interactionManager.update();

        Location from = event.getFrom();
        Location to = event.getTo();
        if (!player.isOnGround()) {
            if (from.getY() < to.getY()) { // on jump

                event.setCancelled(true);
                interactionManager.dispatch(InteractionActionBinding.JUMP);

            } else if (player.getVelocity().getY() < -0.0785) { // on fall

                World world = player.getWorld();
                for (int y = 0; y < world.getMaxHeight(); y++) {
                    Block block = world.getBlockAt(to.getBlockX(), y, to.getBlockZ());
                    if (!block.isEmpty()) {
                        if (to.getY() < y)
                            player.teleport(world.getSpawnLocation());
                        return;
                    }
                }
                player.teleport(world.getSpawnLocation());

            }
        }

    }

    @Override
    public void onPlayerToggleSprint(PlayerToggleSprintEvent event) {
        if (event.isSprinting() && plugin.getPlayerList().getPlayer(event.getPlayer()).getType() == PlayerType.KILLER)
            event.setCancelled(true);
    }

    @Override
    public void onPlayerToggleSneak(PlayerToggleSneakEvent event) {
        DeadByCubePlayer deadByCubePlayer = plugin.getPlayerList().getPlayer(event.getPlayer());
        deadByCubePlayer.getInteractionManager().dispatch(InteractionActionBinding.SNEAK);
        event.setCancelled(true);
    }

    @Override
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        PlayerList playerList = plugin.getPlayerList();
        DeadByCubePlayer deadByCubePlayer = playerList.getPlayer(player);
        switch (event.getAction()) {
            case LEFT_CLICK_AIR:
            case LEFT_CLICK_BLOCK:
                deadByCubePlayer.getActionHandler().attack();
                deadByCubePlayer.getInteractionManager().dispatch(InteractionActionBinding.ATTACK);
                break;
            case RIGHT_CLICK_AIR:
            case RIGHT_CLICK_BLOCK:
                deadByCubePlayer.getActionHandler().interact();
                deadByCubePlayer.getInteractionManager().dispatch(InteractionActionBinding.USE);
                break;
        }
    }

    @Override
    public void onPlayerSwapHandItems(PlayerSwapHandItemsEvent event) {
        DeadByCubePlayer deadByCubePlayer = plugin.getPlayerList().getPlayer(event.getPlayer());
        deadByCubePlayer.getInteractionManager().dispatch(InteractionActionBinding.SWAP_HANDS);
        event.setCancelled(true);
    }

    @Override
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        DeadByCubePlayer deadByCubePlayer = plugin.getPlayerList().getPlayer(event.getPlayer());
        deadByCubePlayer.getInteractionManager().dispatch(InteractionActionBinding.DROP);
        event.setCancelled(true);
    }

}
