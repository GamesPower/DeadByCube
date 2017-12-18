package deadbycube.player.spectator;

import deadbycube.DeadByCube;
import deadbycube.player.DbcPlayer;
import deadbycube.player.PlayerActionHandler;
import deadbycube.player.PlayerType;
import deadbycube.player.killer.Killer;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Spectator extends DbcPlayer {

    private DbcPlayer spectated;

    public Spectator(DeadByCube plugin, Player player) {
        super(plugin, player);
    }

    @Override
    public void init() {
        ArrayList<Killer> killerList = plugin.getPlayerList().getKillers();
        if (killerList.size() > 0) {
            player.setSpectatorTarget(killerList.get(0).getPlayer());
        }
    }

    @Override
    public void reset() {
    }

    @Override
    public PlayerActionHandler createActionHandler() {
        return new SpectatorActionHandler(this);
    }

    @Override
    public PlayerType getType() {
        return PlayerType.SPECTATOR;
    }

}
