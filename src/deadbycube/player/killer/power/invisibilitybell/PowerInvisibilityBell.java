package deadbycube.player.killer.power.invisibilitybell;

import deadbycube.DeadByCube;
import deadbycube.audio.SoundRegistry;
import deadbycube.audio.WorldAudioManager;
import deadbycube.interaction.PlayerInteractionManager;
import deadbycube.player.killer.KillerPlayer;
import deadbycube.player.killer.power.Power;
import deadbycube.player.killer.power.invisibilitybell.interaction.CloakInteraction;
import deadbycube.player.killer.power.invisibilitybell.interaction.UncloakInteraction;
import deadbycube.util.MagicalValue;
import org.bukkit.Location;
import org.bukkit.SoundCategory;

public class PowerInvisibilityBell extends Power {

    private static final String CLOAKED_MODIFIER = "power.invisibility_bell.cloaked";

    private static final double CLOAK_SPEED_BONUS = 0.12;
    private static final double CLOAK_BREATH_REDUCTION = .07;
    private static final int WHOOSH_DISTANCE = 40;

    private final CloakInteraction cloakInteraction;
    private final UncloakInteraction uncloakInteraction;

    private final MagicalValue cloakTime = new MagicalValue(30);
    private final MagicalValue uncloakTime = new MagicalValue(50);

    private CloakStatus status;

    public PowerInvisibilityBell(KillerPlayer killer) {
        super(killer);

        this.cloakInteraction = new CloakInteraction(this);
        this.uncloakInteraction = new UncloakInteraction(this);
    }

    @Override
    public void init() {
        super.init();

        // Set status to cloaked
        this.status = CloakStatus.CLOAKED;
        this.killer.setHidden(true);
        this.killer.getTerrorRadius().forceValue(0d);
        this.killer.getBreathVolume().addModifier(PowerInvisibilityBell.CLOAKED_MODIFIER, PowerInvisibilityBell.CLOAK_BREATH_REDUCTION, MagicalValue.Operation.SUBTRACT);
        this.killer.getWalkSpeed().addModifier(PowerInvisibilityBell.CLOAKED_MODIFIER, PowerInvisibilityBell.CLOAK_SPEED_BONUS, MagicalValue.Operation.MULTIPLY);
        this.killer.getAudioManager().playSound(SoundRegistry.POWER_INVISIBILITY_BELL_INVISIBLE, SoundCategory.MASTER, 15, 1);

        PlayerInteractionManager interactionManager = killer.getInteractionManager();
        interactionManager.registerInteraction(uncloakInteraction);
        interactionManager.updateInteractions();
    }

    @Override
    public void reset() {
        killer.getBreathVolume().removeModifier(CLOAKED_MODIFIER);
        killer.getWalkSpeed().removeModifier(CLOAKED_MODIFIER);
    }

    public void setStatus(CloakStatus status) {
        if (this.status == status)
            return;
        this.status = status;

        DeadByCube plugin = killer.getPlugin();
        WorldAudioManager audioManager = plugin.getAudioManager();
        Location killerLocation = killer.getLocation();
        PlayerInteractionManager interactionManager = killer.getInteractionManager();

        if (status == CloakStatus.CLOAKED) {

            audioManager.playSound(SoundRegistry.POWER_INVISIBILITY_BELL_VISIBLE, SoundCategory.MASTER, killerLocation, 15, 1, deadByCubePlayer -> deadByCubePlayer.getPlayer().getLocation().distance(killerLocation) < WHOOSH_DISTANCE);
            killer.setHidden(true);
            killer.getTerrorRadius().forceValue(0d);
            killer.getBreathVolume().addModifier(CLOAKED_MODIFIER, CLOAK_BREATH_REDUCTION, MagicalValue.Operation.SUBTRACT);
            killer.getWalkSpeed().addModifier(CLOAKED_MODIFIER, CLOAK_SPEED_BONUS, MagicalValue.Operation.MULTIPLY);

            interactionManager.unregisterInteraction(cloakInteraction);
            interactionManager.registerInteraction(uncloakInteraction);
            interactionManager.updateInteractions();

        } else if (status == CloakStatus.UNCLOAKED) {

            audioManager.playSound(SoundRegistry.POWER_INVISIBILITY_BELL_INVISIBLE, SoundCategory.MASTER, killerLocation, 15, 1, deadByCubePlayer -> deadByCubePlayer.getPlayer().getLocation().distance(killerLocation) < WHOOSH_DISTANCE);
            killer.setHidden(false);
            killer.getTerrorRadius().resetValue();
            killer.getBreathVolume().removeModifier(CLOAKED_MODIFIER);
            killer.getWalkSpeed().removeModifier(CLOAKED_MODIFIER);

            interactionManager.unregisterInteraction(uncloakInteraction);
            interactionManager.registerInteraction(cloakInteraction);
            interactionManager.updateInteractions();

        }
    }

    public MagicalValue getCloakTime() {
        return cloakTime;
    }

    public MagicalValue getUncloakTime() {
        return uncloakTime;
    }

}
