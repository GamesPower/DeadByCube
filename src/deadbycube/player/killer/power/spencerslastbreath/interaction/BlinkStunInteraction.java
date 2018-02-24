package deadbycube.player.killer.power.spencerslastbreath.interaction;

import deadbycube.audio.AudioManager;
import deadbycube.interaction.Interaction;
import deadbycube.player.killer.power.spencerslastbreath.PowerSpencersLastBreath;
import deadbycube.registry.SoundRegistry;
import deadbycube.util.MagicalValue;
import org.bukkit.Location;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class BlinkStunInteraction extends Interaction {

    private static final String BLINK_STUN_MODIFIER = "power.spencers_last_breath.stun";
    private static final long STUN_TIME = 50L;

    private final PowerSpencersLastBreath power;

    public BlinkStunInteraction(PowerSpencersLastBreath power) {
        super("blink_stun");

        this.power = power;
    }

    @Override
    protected void onInteract() {
        interactor.getWalkSpeed().addModifier(BLINK_STUN_MODIFIER, -80, MagicalValue.Operation.PERCENTAGE);

        AudioManager audioManager = interactor.getPlugin().getAudioManager();
        Player killerPlayer = interactor.getPlayer();
        audioManager.playSound(SoundRegistry.KILLER_NURSE_TELEPORT_DISAPPEAR, SoundCategory.MASTER, killerPlayer.getLocation(), 1);

        killerPlayer.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, (int) (STUN_TIME + 20), 0, false, false));
    }

    @Override
    protected void onUpdate(int tick) {
        Player player = interactor.getPlayer();
        Location playerLocation = player.getLocation();

        float pitch = playerLocation.getPitch();
        if (pitch < 70) {
            playerLocation.setPitch(tick * 20);
            player.teleport(playerLocation);
        }

        if (tick == STUN_TIME)
            this.stopInteract();
    }

    @Override
    protected void onStopInteract(int tick) {
        interactor.getWalkSpeed().removeModifier(BLINK_STUN_MODIFIER);
        power.updateRemainingBlinks();
    }

}
