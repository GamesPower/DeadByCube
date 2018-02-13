package deadbycube.player.killer.power.spencerslastbreath.interaction.attack;

import deadbycube.audio.WorldAudioManager;
import deadbycube.interaction.InteractionManager;
import deadbycube.player.killer.KillerPlayer;
import deadbycube.player.killer.interaction.attack.AttackLungeInteraction;
import deadbycube.player.killer.power.spencerslastbreath.PowerSpencersLastBreath;
import org.bukkit.SoundCategory;

public class BlinkAttackLungeInteraction extends AttackLungeInteraction {

    private final PowerSpencersLastBreath power;

    public BlinkAttackLungeInteraction(PowerSpencersLastBreath power) {
        this.power = power;
    }

    @Override
    protected void onStopInteract(int tick) {
        interactor.getWalkSpeed().removeModifier(AttackLungeInteraction.LUNGE_SPEED_MODIFIER);

        KillerPlayer killer = (KillerPlayer) interactor;
        WorldAudioManager audioManager = killer.getPlugin().getAudioManager();
        audioManager.playSound("killer." + killer.getName() + ".weapon.missed", SoundCategory.MASTER, interactor.getPlayer().getLocation());

        InteractionManager interactionManager = interactor.getInteractionManager();
        interactionManager.interact(new BlinkAttackRecoveryInteraction(power, 20));
    }

}
