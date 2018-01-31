package deadbycube.player;

import deadbycube.player.killer.KillerPlayer;
import deadbycube.player.survivor.SurvivorPlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PlayerList {

    private final List<DeadByCubePlayer> playerList = new ArrayList<>();

    public DeadByCubePlayer getPlayer(Player player) {
        for (DeadByCubePlayer deadByCubePlayer : playerList) {
            if (deadByCubePlayer.getPlayer().equals(player))
                return deadByCubePlayer;
        }
        return null;
    }

    public void setPlayer(Player player, DeadByCubePlayer deadByCubePlayer) {
        this.removePlayer(player);
        this.playerList.add(deadByCubePlayer);
    }

    public void resetPlayer(Player player) {
        Iterator<DeadByCubePlayer> iterator = playerList.iterator();
        while (iterator.hasNext()) {
            DeadByCubePlayer deadByCubePlayer = iterator.next();
            if (deadByCubePlayer.getPlayer().equals(player)) {
                deadByCubePlayer.reset();
                iterator.remove();
            }
        }
    }

    public void removePlayer(Player player) {
        playerList.removeIf(dbcPlayer -> dbcPlayer.player.equals(player));
    }

    public List<DeadByCubePlayer> getPlayers() {
        return playerList;
    }

    public List<SurvivorPlayer> getSurvivors() {
        ArrayList<SurvivorPlayer> survivorList = new ArrayList<>();
        for (DeadByCubePlayer player : playerList) {
            if (player.getType().equals(PlayerType.SURVIVOR))
                survivorList.add((SurvivorPlayer) player);
        }
        return survivorList;
    }

    public List<KillerPlayer> getKillers() {
        ArrayList<KillerPlayer> killerList = new ArrayList<>();
        for (DeadByCubePlayer player : playerList) {
            if (player.getType().equals(PlayerType.KILLER))
                killerList.add((KillerPlayer) player);
        }
        return killerList;
    }

}
