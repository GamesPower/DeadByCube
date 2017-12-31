package deadbycube.player.survivor;

import deadbycube.DeadByCube;
import deadbycube.player.DeadByCubePlayer;
import deadbycube.player.PlayerType;
import deadbycube.player.actionhandler.SurvivorActionHandler;
import org.bukkit.entity.Player;

public class Survivor extends DeadByCubePlayer {

    private static final float DEFAULT_WALK_SPEED = 0.2f;
    private static final int DEFAULT_FOOD_LEVEL = 20;

    private final HeartbeatManager heartbeatManager;

    public Survivor(DeadByCube plugin, Player player) {
        super(plugin, player);
        this.heartbeatManager = new HeartbeatManager(this);
    }

    @Override
    public void init() {
        player.setWalkSpeed(DEFAULT_WALK_SPEED);
        player.setFoodLevel(DEFAULT_FOOD_LEVEL);

        this.heartbeatManager.init();
    }

    @Override
    public void reset() {
        this.heartbeatManager.reset();
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
