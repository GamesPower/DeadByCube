package deadbycube.player.survivor;

import deadbycube.DeadByCube;
import deadbycube.player.DbcPlayer;
import deadbycube.player.PlayerActionHandler;
import deadbycube.player.PlayerType;
import deadbycube.player.killer.KillerActionHandler;
import org.bukkit.entity.Player;

public class Survivor extends DbcPlayer {

    public Survivor(DeadByCube plugin, Player player) {
        super(plugin, player);
    }

    @Override
    public void init() {
    }

    @Override
    public void reset() {
    }

    @Override
    public SurvivorActionHandler createActionHandler() {
        return new SurvivorActionHandler(this);
    }

    @Override
    public PlayerType getType() {
        return PlayerType.SURVIVOR;
    }

}
