package deadbycube.player.survivor;

import deadbycube.DeadByCube;
import deadbycube.audio.PlayerAudioManager;
import deadbycube.audio.music.MusicManager;
import deadbycube.audio.music.MusicRegistry;
import deadbycube.player.DeadByCubePlayer;
import deadbycube.player.PlayerType;
import deadbycube.player.survivor.heartbeat.HeartbeatManager;
import org.bukkit.entity.Player;

public class SurvivorPlayer extends DeadByCubePlayer {

    private static final int FOOD_LEVEL = 20;
    private static final double WALK_SPEED = 0.2;

    private final HeartbeatManager heartbeatManager;

    public SurvivorPlayer(DeadByCube plugin, Player player) {
        super(plugin, player);
        this.heartbeatManager = new HeartbeatManager(this);
    }

    @Override
    public void init() {
        super.init();

        this.getWalkSpeed().setBaseValue(WALK_SPEED);
        player.setFoodLevel(FOOD_LEVEL);

        PlayerAudioManager audioManager = getAudioManager();
        MusicManager musicManager = audioManager.getMusicManager();
        musicManager.setMusics(MusicRegistry.SURVIVOR_NORMAL);

        this.heartbeatManager.init();
    }

    @Override
    public PlayerType getType() {
        return PlayerType.SURVIVOR;
    }

    public HeartbeatManager getHeartbeatManager() {
        return heartbeatManager;
    }
}
