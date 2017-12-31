package deadbycube.eventhandler;

import deadbycube.DeadByCube;
import deadbycube.game.DeadByCubeGame;
import deadbycube.game.interaction.InteractionManager;
import deadbycube.player.DeadByCubePlayer;
import deadbycube.player.PlayerList;
import deadbycube.player.PlayerType;
import deadbycube.player.spectator.Spectator;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.*;

public class InGameEventHandler extends EventHandler {

    public InGameEventHandler(DeadByCube plugin) {
        super(plugin);
    }

    @Override
    public void onEntityDamage(EntityDamageEvent event) {
        event.setCancelled(true);

        if (event.getCause() == EntityDamageEvent.DamageCause.FALL) {
            System.out.println("PLAY FALL SOUND");
        }
    }

    @Override
    public void onPlayerJoin(PlayerJoinEvent event) {
        event.setJoinMessage(null);

        Player player = event.getPlayer();
        PlayerList playerList = plugin.getPlayerList();
        Spectator spectator = new Spectator(plugin, player);
        playerList.setPlayer(player, spectator);
        spectator.init();
    }

    @Override
    public void onPlayerQuit(PlayerQuitEvent event) {
        event.setQuitMessage(null);

        PlayerList playerList = plugin.getPlayerList();
        playerList.removePlayer(event.getPlayer());

        // TODO Call the kill method if he was a survivor
        // TODO Check the game requirements (at least 1 killer and 1 survivor)
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
                break;
            case RIGHT_CLICK_AIR:
            case RIGHT_CLICK_BLOCK:
                deadByCubePlayer.getActionHandler().interact();
                break;
        }
    }

    @Override
    public void onPlayerToggleSprint(PlayerToggleSprintEvent event) {
        if (event.isSprinting() && plugin.getPlayerList().getPlayer(event.getPlayer()).getType() == PlayerType.KILLER)
            event.setCancelled(true);
    }

    @Override
    public void onPlayerSwapHandItems(PlayerSwapHandItemsEvent event) {
        event.setCancelled(true);
    }

    @Override
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        DeadByCubePlayer deadByCubePlayer = plugin.getPlayerList().getPlayer(player);

        if (deadByCubePlayer != null) {
            DeadByCubeGame game = plugin.getGame();
            InteractionManager interactionManager = game.getInteractionManager();
            interactionManager.update(deadByCubePlayer);
        }

        if (event.getTo().getY() <= 0) {
            World world = player.getWorld();
            player.teleport(world.getSpawnLocation());
        }
    }

}
