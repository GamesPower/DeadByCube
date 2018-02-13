package deadbycube.player.killer.power.cartersspark;

import deadbycube.DeadByCube;
import deadbycube.audio.WorldAudioManager;
import deadbycube.interaction.InteractionActionBinding;
import deadbycube.interaction.InteractionManager;
import deadbycube.player.killer.KillerPlayer;
import deadbycube.player.killer.power.Power;
import deadbycube.player.killer.power.cartersspark.interaction.ShockTherapyInteraction;
import deadbycube.player.killer.power.cartersspark.interaction.SwitchToPunishmentInteraction;
import deadbycube.player.killer.power.cartersspark.interaction.SwitchToTreatmentInteraction;
import deadbycube.player.killer.power.cartersspark.madness.MadnessEmitter;
import deadbycube.player.survivor.SurvivorPlayer;
import deadbycube.registry.SoundRegistry;
import deadbycube.util.MagicalValue;
import deadbycube.util.MathUtils;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class PowerCartersSpark extends Power implements MadnessEmitter {

    private static final String TREATMENT_MODIFIER = "power.carters_spark.treatment";

    private static final float SHOCK_MIN_DISTANCE = 1.5f;
    private static final float SHOCK_MAX_DISTANCE = 12f;
    private static final int SHOCK_ANGLE = 35;

    private final SwitchToTreatmentInteraction switchToTreatmentInteraction;
    private final SwitchToPunishmentInteraction switchToPunishmentInteraction;
    private final ShockTherapyInteraction shockTherapyInteraction;

    private final MagicalValue switchToTreatmentTime = new MagicalValue(10);
    private final MagicalValue switchToPunishmentTime = new MagicalValue(10);
    private final MagicalValue chargeTime = new MagicalValue(20);
    private final MagicalValue madnessMultiplier = new MagicalValue(1);

    private CartersSparkMode mode;

    public PowerCartersSpark(KillerPlayer killer) {
        super(killer);

        this.switchToTreatmentInteraction = new SwitchToTreatmentInteraction(this);
        this.switchToPunishmentInteraction = new SwitchToPunishmentInteraction(this);
        this.shockTherapyInteraction = new ShockTherapyInteraction(this);
    }

    @Override
    public void init() {
        super.init();
        this.setMode(CartersSparkMode.PUNISHMENT);
    }

    @Override
    public void reset() {
        killer.getWalkSpeed().removeModifier(TREATMENT_MODIFIER);
    }

    public void setMode(CartersSparkMode mode) {
        if (this.mode == mode)
            return;
        this.mode = mode;

        InteractionManager interactionManager = killer.getInteractionManager();
        MagicalValue walkSpeed = killer.getWalkSpeed();

        if (mode == CartersSparkMode.TREATMENT) {

            walkSpeed.addModifier(TREATMENT_MODIFIER, -2, MagicalValue.Operation.PERCENTAGE);

            interactionManager.unregisterInteraction(InteractionActionBinding.ATTACK, killer.getAttackLungeInteraction());
            interactionManager.unregisterInteraction(InteractionActionBinding.SNEAK, switchToTreatmentInteraction);
            interactionManager.registerInteraction(InteractionActionBinding.SNEAK, switchToPunishmentInteraction);
            interactionManager.registerInteraction(InteractionActionBinding.USE, shockTherapyInteraction);
            interactionManager.updateInteractions();

        } else if (mode == CartersSparkMode.PUNISHMENT) {

            walkSpeed.removeModifier(TREATMENT_MODIFIER);

            interactionManager.unregisterInteraction(InteractionActionBinding.SNEAK, switchToPunishmentInteraction);
            interactionManager.unregisterInteraction(InteractionActionBinding.USE, shockTherapyInteraction);
            interactionManager.registerInteraction(InteractionActionBinding.ATTACK, killer.getAttackLungeInteraction());
            interactionManager.registerInteraction(InteractionActionBinding.SNEAK, switchToTreatmentInteraction);
            interactionManager.updateInteractions();

        }

        WorldAudioManager audioManager = killer.getPlugin().getAudioManager();
        audioManager.playSound(SoundRegistry.POWER_CARTERS_SPARK_ATTACK_READY, SoundCategory.MASTER, killer.getLocation(), 1);
    }

    public void generateShock() {
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

                            audioManager.playSound(soundRegistry, SoundCategory.MASTER, groundBlock.getLocation(), .6f);
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

    public MagicalValue getSwitchToTreatmentTime() {
        return switchToTreatmentTime;
    }

    public MagicalValue getSwitchToPunishmentTime() {
        return switchToPunishmentTime;
    }

    public MagicalValue getChargeTime() {
        return chargeTime;
    }

    @Override
    public MagicalValue getMadnessMultiplier() {
        return madnessMultiplier;
    }

    @Override
    public Location getLocation() {
        return killer.getLocation();
    }

    @Override
    public MagicalValue getRadius() {
        return killer.getTerrorRadius();
    }

}
