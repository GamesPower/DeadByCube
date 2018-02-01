package deadbycube.player.killer.power.cartersspark;

import deadbycube.DeadByCube;
import deadbycube.audio.SoundRegistry;
import deadbycube.audio.WorldAudioManager;
import deadbycube.player.killer.KillerPlayer;
import deadbycube.player.killer.power.Power;
import deadbycube.player.survivor.SurvivorPlayer;
import deadbycube.util.MathUtils;
import deadbycube.util.Progression;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.boss.BarColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.Vector;

public class PowerCartersSpark extends Power {

    private static final float SHOCK_MIN_DISTANCE = 1.5f;
    private static final float SHOCK_MAX_DISTANCE = 10f;
    private static final int SHOCK_CHARGE_TIME = 25;
    private static final int SHOCK_ANGLE = 35;

    private final Progression progression;

    private int chargeTime;

    public PowerCartersSpark(KillerPlayer killer) {
        super(killer);

        this.progression = new Progression("power.carters_spark.action", BarColor.WHITE);
    }

    @Override
    public void init(boolean using) {
        super.init(using);

        this.progression.setMaxValue(SHOCK_CHARGE_TIME);
        this.progression.display(killer);
    }

    @Override
    public void reset() {
        this.progression.reset();
        super.reset();
    }

    @Override
    public boolean canUse() {
        return true;
    }

    @Override
    protected void onUse() {
        Player player = killer.getPlayer();
        WorldAudioManager audioManager = killer.getPlugin().getAudioManager();
        Location soundLocation = player.getLocation();

        audioManager.playSound(SoundRegistry.POWER_CARTERS_SPARK_CHARGE, SoundCategory.MASTER, soundLocation, 1, 1);
        audioManager.playSound(SoundRegistry.POWER_CARTERS_SPARK_CHARGE_BASS, SoundCategory.MASTER, soundLocation, 1, 1);
        audioManager.playSound(SoundRegistry.POWER_CARTERS_SPARK_CHARGE_HIGH, SoundCategory.MASTER, soundLocation, 1, 1);
    }

    @Override
    protected void onUpdate() {
        if (chargeTime % 2 == 0)
            this.progression.setValue(chargeTime);

        Player player = killer.getPlayer();
        World world = player.getWorld();

        Location particleLocation = player.getLocation().add(0, .8, 0);
        for (int i = 0; i < (chargeTime / 2); i++)
            world.spawnParticle(Particle.SMOKE_NORMAL, particleLocation, 1, .2, .35, .2, 0);

        if (++chargeTime == SHOCK_CHARGE_TIME) {
            WorldAudioManager audioManager = killer.getPlugin().getAudioManager();
            audioManager.playSound(SoundRegistry.POWER_CARTERS_SPARK_ATTACK_READY, SoundCategory.MASTER, player.getLocation(), 1, 1);
            this.stopUse();
        }
    }

    @Override
    protected void onStopUse() {
        DeadByCube plugin = killer.getPlugin();
        WorldAudioManager audioManager = plugin.getAudioManager();
        if (chargeTime == SHOCK_CHARGE_TIME) {
            Player player = killer.getPlayer();

            Location soundLocation = player.getLocation();
            audioManager.playSound(SoundRegistry.POWER_CARTERS_SPARK_ATTACK, SoundCategory.MASTER, soundLocation, 1, 1);
            audioManager.playSound(SoundRegistry.POWER_CARTERS_SPARK_ATTACK_BASS, SoundCategory.MASTER, soundLocation, 1, 1);
            audioManager.playSound(SoundRegistry.POWER_CARTERS_SPARK_EXPLOSION, SoundCategory.MASTER, soundLocation, 1, 1);


            Server server = plugin.getServer();
            BukkitScheduler scheduler = server.getScheduler();
            scheduler.runTaskLater(plugin, this::generateShock, 5);


        } else {
            audioManager.stopSound(SoundRegistry.POWER_CARTERS_SPARK_CHARGE);
            audioManager.stopSound(SoundRegistry.POWER_CARTERS_SPARK_CHARGE_BASS);
            audioManager.stopSound(SoundRegistry.POWER_CARTERS_SPARK_CHARGE_HIGH);
        }

        this.chargeTime = 0;
        this.progression.setValue(0);
    }

    private void generateShock() {
        DeadByCube plugin = killer.getPlugin();
        WorldAudioManager audioManager = plugin.getAudioManager();

        Player killerPlayer = killer.getPlayer();
        World world = killerPlayer.getWorld();

        float distanceStep = .5f;
        float angleStep = 3.2f;

        for (float distance = SHOCK_MIN_DISTANCE; distance < SHOCK_MAX_DISTANCE; distance += distanceStep) {
            for (double angle = -SHOCK_ANGLE; angle <= SHOCK_ANGLE; angle += angleStep) {
                Location killerLocation = killerPlayer.getLocation();
                Location blockLocation = killerLocation.add(
                        MathUtils.direction(killerLocation.getYaw() + angle, 0).multiply(distance)
                );
                Block block = world.getBlockAt(blockLocation);
                Material type = block.getType();
                if (type.isOccluding() || type.isTransparent()) {
                    world.spawnParticle(Particle.CLOUD, blockLocation, 1, 0, 0, 0, 0);

                    if (distance % 1 == 0 && angle % 1 == 0) {
                        Block groundBlock = block.getRelative(BlockFace.DOWN);

                        if (!groundBlock.isEmpty()) {
                            SoundRegistry soundRegistry;
                            Material groundType = groundBlock.getType();
                            if (groundType == Material.GRASS || groundType == Material.DIRT)
                                soundRegistry = SoundRegistry.POWER_CARTERS_SPARK_ATTACK_DIRT;
                            else
                                soundRegistry = SoundRegistry.POWER_CARTERS_SPARK_ATTACK_GROUND;

                            audioManager.playSound(soundRegistry, SoundCategory.MASTER, groundBlock.getLocation(), .6f, 1);
                        }
                    }
                }

            }
        }

        Location killerLocation = killerPlayer.getLocation();
        int shockedSurvivor = 0;
        for (SurvivorPlayer survivor : plugin.getPlayerList().getSurvivors()) {
            Player survivorPlayer = survivor.getPlayer();
            Location survivorLocation = survivorPlayer.getLocation();

            Vector shockDirection = MathUtils.direction(killerLocation.getYaw(), 0);
            Vector killerToSurvivorDirection = survivorLocation.subtract(killerLocation).toVector();
            double angle = Math.toDegrees(killerToSurvivorDirection.angle(shockDirection));
            if (-SHOCK_ANGLE < angle && angle < SHOCK_ANGLE) {
                double distance = killerLocation.distance(survivorPlayer.getLocation());
                if (SHOCK_MIN_DISTANCE < distance && distance < SHOCK_MAX_DISTANCE) {
                    shockedSurvivor++;

                    audioManager.playSound("survivor.dwight.scream.short", SoundCategory.VOICE, survivorPlayer.getLocation());
                    // TODO Increments madness
                }
            }
        }

        if (shockedSurvivor > 0)
            audioManager.playSound(SoundRegistry.KILLER_DOCTOR_LAUGH, SoundCategory.VOICE, killerLocation);
    }

}
