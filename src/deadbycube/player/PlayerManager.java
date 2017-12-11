package deadbycube.player;

import deadbycube.player.killer.Killer;
import deadbycube.player.survivor.Survivor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.player.*;

import java.util.ArrayList;
import java.util.Iterator;

public class PlayerManager implements Listener {

    private static final ArrayList<DbcPlayer> PLAYER_LIST = new ArrayList<>();

    public static DbcPlayer getPlayer(Player player) {
        for (DbcPlayer dbcPlayer : PLAYER_LIST) {
            if (dbcPlayer.getPlayer().equals(player))
                return dbcPlayer;
        }
        return null;
    }

    public static void setPlayer(Player player, DbcPlayer dbcPlayer) {
        Iterator<DbcPlayer> iterator = PLAYER_LIST.iterator();
        while (iterator.hasNext()) {
            DbcPlayer iteratorPlayer = iterator.next();
            if (iteratorPlayer.getPlayer().equals(player)) {
                iteratorPlayer.reset();
                iterator.remove();
            }
        }
        PLAYER_LIST.add(dbcPlayer);
    }

    public static ArrayList<DbcPlayer> getPlayers() {
        return PLAYER_LIST;
    }

    public static ArrayList<Survivor> getSurvivors() {
        ArrayList<Survivor> survivorList = new ArrayList<>();
        for (DbcPlayer player : PLAYER_LIST) {
            if (player.getType().equals(PlayerType.SURVIVOR))
                survivorList.add((Survivor) player);
        }
        return survivorList;
    }

    public static ArrayList<Killer> getKillers() {
        ArrayList<Killer> killerList = new ArrayList<>();
        for (DbcPlayer player : PLAYER_LIST) {
            if (player.getType().equals(PlayerType.KILLER))
                killerList.add((Killer) player);
        }
        return killerList;
    }

    @EventHandler
    private void onJoin(PlayerJoinEvent event) {
        event.setJoinMessage(null);
    }

    @EventHandler
    private void onQuit(PlayerQuitEvent event) {
        event.setQuitMessage(null);
        PLAYER_LIST.removeIf(dbcPlayer -> dbcPlayer.getPlayer().equals(event.getPlayer()));
    }

    @EventHandler
    private void onToggleSneak(PlayerToggleSneakEvent event) {
        DbcPlayer player = PlayerManager.getPlayer(event.getPlayer());
        if (player != null)
            player.getActionHandler().toggleSneak(event.isSneaking());
    }

    @EventHandler
    private void onMove(PlayerMoveEvent event) {
        DbcPlayer player = PlayerManager.getPlayer(event.getPlayer());
        if (player != null)
            player.getActionHandler().move();
    }

    @EventHandler
    private void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        DbcPlayer dbcPlayer = PlayerManager.getPlayer(player);
        if (dbcPlayer == null)
            return;

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

}
