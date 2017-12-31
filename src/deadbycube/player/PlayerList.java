package deadbycube.player;

import deadbycube.player.killer.Killer;
import deadbycube.player.survivor.Survivor;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class PlayerList {

    private final ArrayList<DeadByCubePlayer> playerList = new ArrayList<>();

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

    public void removePlayer(Player player) {
        playerList.removeIf(dbcPlayer -> dbcPlayer.player.equals(player));
    }

    public ArrayList<DeadByCubePlayer> getPlayers() {
        return playerList;
    }

    public ArrayList<Survivor> getSurvivors() {
        ArrayList<Survivor> survivorList = new ArrayList<>();
        for (DeadByCubePlayer player : playerList) {
            if (player.getType().equals(PlayerType.SURVIVOR))
                survivorList.add((Survivor) player);
        }
        return survivorList;
    }

    public ArrayList<Killer> getKillers() {
        ArrayList<Killer> killerList = new ArrayList<>();
        for (DeadByCubePlayer player : playerList) {
            if (player.getType().equals(PlayerType.KILLER))
                killerList.add((Killer) player);
        }
        return killerList;
    }

}
