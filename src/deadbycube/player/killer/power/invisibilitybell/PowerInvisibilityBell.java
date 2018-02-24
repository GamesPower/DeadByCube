package deadbycube.player.killer.power.invisibilitybell;

import deadbycube.DeadByCube;
import deadbycube.audio.WorldAudioManager;
import deadbycube.interaction.InteractionActionBinding;
import deadbycube.interaction.InteractionManager;
import deadbycube.player.killer.KillerPlayer;
import deadbycube.player.killer.power.Power;
import deadbycube.player.killer.power.invisibilitybell.interaction.CloakInteraction;
import deadbycube.player.killer.power.invisibilitybell.interaction.UncloakInteraction;
import deadbycube.registry.SoundRegistry;
import deadbycube.util.MagicalValue;
import org.bukkit.Location;
import org.bukkit.SoundCategory;

public class PowerInvisibilityBell extends Power {

    private static final String CLOAKED_MODIFIER = "power.invisibility_bell.cloaked";

    private static final double CLOAK_SPEED_BONUS = 12;
    private static final double CLOAK_BREATH_REDUCTION = -80;
    private static final int WHOOSH_DISTANCE = 40;

    private final CloakInteraction cloakInteraction;
    private final UncloakInteraction uncloakInteraction;

    private final MagicalValue cloakTime = new MagicalValue(20);
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
        this.killer.setVisible(false);
        this.killer.getTerrorRadius().forceValue(0d);
        this.killer.getBreathVolume().addModifier(PowerInvisibilityBell.CLOAKED_MODIFIER, PowerInvisibilityBell.CLOAK_BREATH_REDUCTION, MagicalValue.Operation.PERCENTAGE);
        this.killer.getWalkSpeed().addModifier(PowerInvisibilityBell.CLOAKED_MODIFIER, PowerInvisibilityBell.CLOAK_SPEED_BONUS, MagicalValue.Operation.PERCENTAGE);
        this.killer.getAudioManager().playSound(SoundRegistry.POWER_INVISIBILITY_BELL_INVISIBLE, SoundCategory.MASTER, 15, 1);

        InteractionManager interactionManager = killer.getInteractionManager();
        interactionManager.unregisterInteraction(InteractionActionBinding.ATTACK, killer.getAttackLungeInteraction());
        interactionManager.registerInteraction(InteractionActionBinding.USE, uncloakInteraction);
    }

    @Override
    public void reset() {
        this.killer.setVisible(true);
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
        InteractionManager interactionManager = killer.getInteractionManager();

        if (status == CloakStatus.CLOAKED) {

            audioManager.playSound(SoundRegistry.POWER_INVISIBILITY_BELL_INVISIBLE, SoundCategory.MASTER, killerLocation, 15, 1, deadByCubePlayer -> deadByCubePlayer.getPlayer().getLocation().distance(killerLocation) < WHOOSH_DISTANCE);
            killer.setVisible(false);
            killer.getTerrorRadius().forceValue(0d);
            killer.getBreathVolume().addModifier(CLOAKED_MODIFIER, CLOAK_BREATH_REDUCTION, MagicalValue.Operation.PERCENTAGE);
            killer.getWalkSpeed().addModifier(CLOAKED_MODIFIER, CLOAK_SPEED_BONUS, MagicalValue.Operation.PERCENTAGE);

            interactionManager.unregisterInteraction(InteractionActionBinding.ATTACK, killer.getAttackLungeInteraction());
            interactionManager.unregisterInteraction(InteractionActionBinding.USE, cloakInteraction);
            interactionManager.registerInteraction(InteractionActionBinding.USE, uncloakInteraction);

        } else if (status == CloakStatus.UNCLOAKED) {

            audioManager.playSound(SoundRegistry.POWER_INVISIBILITY_BELL_VISIBLE, SoundCategory.MASTER, killerLocation, 15, 1, deadByCubePlayer -> deadByCubePlayer.getPlayer().getLocation().distance(killerLocation) < WHOOSH_DISTANCE);
            killer.setVisible(true);
            killer.getTerrorRadius().resetValue();
            killer.getBreathVolume().removeModifier(CLOAKED_MODIFIER);
            killer.getWalkSpeed().removeModifier(CLOAKED_MODIFIER);

            interactionManager.unregisterInteraction(InteractionActionBinding.USE, uncloakInteraction);
            interactionManager.registerInteraction(InteractionActionBinding.ATTACK, killer.getAttackLungeInteraction());
            interactionManager.registerInteraction(InteractionActionBinding.USE, cloakInteraction);

        }
    }

    public MagicalValue getCloakTime() {
        return cloakTime;
    }

    public MagicalValue getUncloakTime() {
        return uncloakTime;
    }

}
