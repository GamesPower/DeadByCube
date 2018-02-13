package deadbycube.player.killer.power.spencerslastbreath.interaction;

import deadbycube.audio.AudioManager;
import deadbycube.interaction.Interaction;
import deadbycube.player.killer.power.spencerslastbreath.PowerSpencersLastBreath;
import deadbycube.registry.SoundRegistry;
import deadbycube.util.MagicalValue;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;

public class ChargeBlinkInteraction extends Interaction {

    private final PowerSpencersLastBreath power;

    public ChargeBlinkInteraction(PowerSpencersLastBreath power) {
        super("blink");

        this.power = power;
    }

    @Override
    protected void onInteract() {
        Player player = interactor.getPlayer();
        AudioManager audioManager = interactor.getPlugin().getAudioManager();
        audioManager.playSound(SoundRegistry.POWER_SPENCERS_LAST_BREATH_CHARGE_START, SoundCategory.MASTER, player.getLocation(), .5f);
        audioManager.playSound(SoundRegistry.POWER_SPENCERS_LAST_BREATH_CHARGE, SoundCategory.MASTER, player.getLocation(), .5f);
        audioManager.playSound(SoundRegistry.POWER_SPENCERS_LAST_BREATH_CHARGE_HIGH, SoundCategory.MASTER, player.getLocation(), .5f);
        audioManager.playSound(SoundRegistry.POWER_SPENCERS_LAST_BREATH_CHARGE_BASS, SoundCategory.MASTER, player.getLocation(), .5f);
        audioManager.playSound(SoundRegistry.KILLER_NURSE_TELEPORT_CHARGING, SoundCategory.MASTER, player.getLocation(), .5f);
    }

    @Override
    protected void onUpdate(int chargeProgress) {
        MagicalValue chargeTime = power.getChargeTime();

        if (chargeProgress == chargeTime.getValue()) {
            Player player = interactor.getPlayer();
            AudioManager audioManager = interactor.getPlugin().getAudioManager();
            audioManager.playSound(SoundRegistry.KILLER_NURSE_TELEPORT_CHARGE_FULL, SoundCategory.MASTER, player.getLocation(), .5f);
        }
    }

    @Override
    protected void onStopInteract(int chargeProgress) {
        AudioManager audioManager = interactor.getPlugin().getAudioManager();
        audioManager.stopSound(SoundRegistry.POWER_SPENCERS_LAST_BREATH_CHARGE_START);
        audioManager.stopSound(SoundRegistry.POWER_SPENCERS_LAST_BREATH_CHARGE);
        audioManager.stopSound(SoundRegistry.POWER_SPENCERS_LAST_BREATH_CHARGE_HIGH);
        audioManager.stopSound(SoundRegistry.POWER_SPENCERS_LAST_BREATH_CHARGE_BASS);
        audioManager.stopSound(SoundRegistry.KILLER_NURSE_TELEPORT_CHARGING);

        MagicalValue chargeTime = power.getChargeTime();
        power.blink(Math.min((int) chargeTime.getValue(), chargeProgress));
    }

    @Override
    public boolean isInteracting() {
        return super.isInteracting() && interactor.getPlayer().isHandRaised();
    }

}
