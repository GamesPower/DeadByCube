package deadbycube.player;

import deadbycube.player.killer.Killer;
import deadbycube.player.survivor.Survivor;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class PlayerList {

    public PlayerList() {
    }

    private final ArrayList<DbcPlayer> playerList = new ArrayList<>();

    public DbcPlayer getPlayer(Player player) {
        for (DbcPlayer dbcPlayer : playerList) {
            if (dbcPlayer.getPlayer().equals(player))
                return dbcPlayer;
        }
        return null;
    }

    public void setPlayer(Player player, DbcPlayer dbcPlayer) {
        this.removePlayer(player);
        this.playerList.add(dbcPlayer);
    }

    public void removePlayer(Player player) {
        playerList.removeIf(dbcPlayer -> dbcPlayer.player.equals(player));
    }

    public ArrayList<DbcPlayer> getPlayers() {
        return playerList;
    }

    public ArrayList<Survivor> getSurvivors() {
        ArrayList<Survivor> survivorList = new ArrayList<>();
        for (DbcPlayer player : playerList) {
            if (player.getType().equals(PlayerType.SURVIVOR))
                survivorList.add((Survivor) player);
        }
        return survivorList;
    }

    public ArrayList<Killer> getKillers() {
        ArrayList<Killer> killerList = new ArrayList<>();
        for (DbcPlayer player : playerList) {
            if (player.getType().equals(PlayerType.KILLER))
                killerList.add((Killer) player);
        }
        return killerList;
    }

}
