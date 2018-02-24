package deadbycube.listener.ingame;

import deadbycube.DeadByCube;
import deadbycube.interaction.InteractionActionBinding;
import deadbycube.interaction.InteractionManager;
import deadbycube.listener.DeadByCubeListener;
import deadbycube.player.DeadByCubePlayer;
import deadbycube.player.PlayerList;
import deadbycube.player.PlayerType;
import deadbycube.player.spectator.SpectatorPlayer;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.*;

public class InGamePlayerListener extends DeadByCubeListener {

    public InGamePlayerListener(DeadByCube plugin) {
        super(plugin);
    }

    @EventHandler
    private void onPlayerJoin(PlayerJoinEvent event) {
        event.setJoinMessage(null);

        Player player = event.getPlayer();
        PlayerList playerList = plugin.getPlayerList();
        SpectatorPlayer spectator = new SpectatorPlayer(plugin, player);
        playerList.setPlayer(player, spectator);
        spectator.init();
    }

    @EventHandler
    private void onPlayerQuit(PlayerQuitEvent event) {
        event.setQuitMessage(null);

        PlayerList playerList = plugin.getPlayerList();
        Player player = event.getPlayer();
        playerList.resetPlayer(player);

        // TODO Call the kill method if he was a survivor
        // TODO Check the game requirements (at least 1 killer and 1 survivor)
    }

    @EventHandler
    private void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        DeadByCubePlayer deadByCubePlayer = plugin.getPlayerList().getPlayer(player);
        InteractionManager interactionManager = deadByCubePlayer.getInteractionManager();
        interactionManager.updateInteractions();

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

    @EventHandler
    private void onPlayerToggleSprint(PlayerToggleSprintEvent event) {
        if (event.isSprinting() && plugin.getPlayerList().getPlayer(event.getPlayer()).getType() == PlayerType.KILLER)
            event.setCancelled(true);
    }

    @EventHandler
    private void onPlayerToggleSneak(PlayerToggleSneakEvent event) {
        DeadByCubePlayer deadByCubePlayer = plugin.getPlayerList().getPlayer(event.getPlayer());
        deadByCubePlayer.setSneaking(event.isSneaking());

        if (event.isSneaking()) {
            InteractionManager interactionManager = deadByCubePlayer.getInteractionManager();
            interactionManager.dispatch(InteractionActionBinding.SNEAK);
        }

        event.setCancelled(true);
    }

    @EventHandler
    private void onPlayerItemHeld(PlayerItemHeldEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    private void onPlayerAnimation(PlayerAnimationEvent event) {
        if (event.getAnimationType() == PlayerAnimationType.ARM_SWING) {
            DeadByCubePlayer deadByCubePlayer = plugin.getPlayerList().getPlayer(event.getPlayer());
            deadByCubePlayer.getInteractionManager().dispatch(InteractionActionBinding.ATTACK);
            event.setCancelled(true);
        }
    }

    @EventHandler
    private void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        PlayerList playerList = plugin.getPlayerList();
        DeadByCubePlayer deadByCubePlayer = playerList.getPlayer(player);
        switch (event.getAction()) {
            case LEFT_CLICK_AIR:
            case LEFT_CLICK_BLOCK:
                deadByCubePlayer.getInteractionManager().dispatch(InteractionActionBinding.ATTACK);
                break;
            case RIGHT_CLICK_AIR:
            case RIGHT_CLICK_BLOCK:
                deadByCubePlayer.getInteractionManager().dispatch(InteractionActionBinding.USE);
                break;
        }
    }

    @EventHandler
    private void onPlayerSwapHandItems(PlayerSwapHandItemsEvent event) {
        DeadByCubePlayer deadByCubePlayer = plugin.getPlayerList().getPlayer(event.getPlayer());
        deadByCubePlayer.getInteractionManager().dispatch(InteractionActionBinding.SWAP_HANDS);
        event.setCancelled(true);
    }

    @EventHandler
    private void onPlayerDropItem(PlayerDropItemEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    private void onFoodLevelChange(FoodLevelChangeEvent event) {
        event.setCancelled(true);
    }

}
