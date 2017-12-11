package deadbycube.player.survivor;

import deadbycube.DeadByCube;
import deadbycube.player.DbcPlayer;
import deadbycube.player.PlayerType;
import org.bukkit.entity.Player;

public class Survivor extends DbcPlayer {

    private static final float DEFAULT_WALK_SPEED = 0.2f;
    private static final int DEFAULT_FOOD_LEVEL = 20;

    public Survivor(DeadByCube plugin, Player player) {
        super(plugin, player);
    }

    @Override
    public void init() {
        player.setWalkSpeed(DEFAULT_WALK_SPEED);
        player.setFoodLevel(DEFAULT_FOOD_LEVEL);
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
