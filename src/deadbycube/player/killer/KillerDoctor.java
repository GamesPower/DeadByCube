package deadbycube.player.killer;

import deadbycube.DeadByCube;
import deadbycube.registry.PowerRegistry;
import deadbycube.registry.SkinRegistry;
import deadbycube.registry.SoundRegistry;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;

import java.util.Random;

public class KillerDoctor extends KillerPlayer {

    public KillerDoctor(DeadByCube plugin, Player player) {
        super(plugin, player, "doctor", (byte) 25, SkinRegistry.DEFAULT, PowerRegistry.CARTERS_SPARK);
    }

    @Override
    void playBreathSound() {
        super.playBreathSound();
        plugin.getAudioManager().playSound(SoundRegistry.KILLER_DOCTOR_BREATH_TEETH, SoundCategory.VOICE, player.getLocation(), (float) getBreathVolume().getValue());
    }
}
