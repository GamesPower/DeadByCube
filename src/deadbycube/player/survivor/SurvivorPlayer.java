package deadbycube.player.survivor;

import deadbycube.DeadByCube;
import deadbycube.player.DeadByCubePlayer;
import deadbycube.player.PlayerType;
import deadbycube.player.survivor.heartbeat.HeartbeatManager;
import deadbycube.util.MagicalValue;
import org.bukkit.entity.Player;

public class SurvivorPlayer extends DeadByCubePlayer {

    private static final int FOOD_LEVEL = 20;
    private static final double WALK_SPEED = 0.2;

    private final HeartbeatManager heartbeatManager;

    private final MagicalValue repairSpeed = new MagicalValue(1);

    public SurvivorPlayer(DeadByCube plugin, Player player) {
        super(plugin, player);
        this.heartbeatManager = new HeartbeatManager(this);
    }

    @Override
    public void init() {
        super.init();

        walkSpeed.setBaseValue(WALK_SPEED);
        player.setFoodLevel(FOOD_LEVEL);

        this.heartbeatManager.init();
    }

    @Override
    public PlayerType getType() {
        return PlayerType.SURVIVOR;
    }

    public HeartbeatManager getHeartbeatManager() {
        return heartbeatManager;
    }

    public MagicalValue getRepairSpeed() {
        return repairSpeed;
    }

}
