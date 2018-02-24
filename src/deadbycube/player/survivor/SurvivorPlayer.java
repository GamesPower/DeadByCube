package deadbycube.player.survivor;

import deadbycube.DeadByCube;
import deadbycube.audio.WorldAudioManager;
import deadbycube.player.DeadByCubePlayer;
import deadbycube.player.PlayerType;
import deadbycube.player.killer.power.cartersspark.madness.MadnessManager;
import deadbycube.player.survivor.heartbeat.HeartbeatManager;
import deadbycube.registry.SkinRegistry;
import deadbycube.util.MagicalValue;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;

import java.util.Random;

public class SurvivorPlayer extends DeadByCubePlayer {

    private static final int FOOD_LEVEL = 20;
    private static final double WALK_SPEED = 0.2;

    private final HeartbeatManager heartbeatManager;
    private final MadnessManager madnessManager;

    private final MagicalValue actionSpeed = new MagicalValue(1);
    private final MagicalValue repairSpeed = new MagicalValue(1);

    public SurvivorPlayer(DeadByCube plugin, Player player) {
        super(plugin, player, "dwight", SkinRegistry.DEFAULT);
        this.heartbeatManager = new HeartbeatManager(this);
        this.madnessManager = new MadnessManager(this);
    }

    @Override
    public void init() {
        super.init();

        walkSpeed.setBaseValue(WALK_SPEED);
        player.setFoodLevel(FOOD_LEVEL);

        this.heartbeatManager.init();
        this.madnessManager.init();
    }

    @Override
    public void reset() {
        super.reset();

        this.heartbeatManager.reset();
        this.madnessManager.reset();
    }

    public void hit() {
        World world = player.getWorld();
        Location playerLocation = player.getLocation();

        MaterialData materialData = new MaterialData(Material.NETHER_WART_BLOCK);
        world.spawnParticle(
                Particle.BLOCK_DUST,
                playerLocation.getX(),
                playerLocation.getY() + 0.7,
                playerLocation.getZ(),
                16,
                .2, .2, .2,
                0.1,
                materialData
        );

        WorldAudioManager audioManager = plugin.getAudioManager();
        audioManager.playSound("survivor." + name + ".scream.short", SoundCategory.VOICE, playerLocation, 2, 1);
    }

    @Override
    public PlayerType getType() {
        return PlayerType.SURVIVOR;
    }

    public HeartbeatManager getHeartbeatManager() {
        return heartbeatManager;
    }

    public MadnessManager getMadnessManager() {
        return madnessManager;
    }

    public MagicalValue getActionSpeed() {
        return actionSpeed;
    }

    public MagicalValue getRepairSpeed() {
        return repairSpeed;
    }


}
