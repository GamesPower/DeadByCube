package deadbycube.player.killer;

import deadbycube.DeadByCube;
import deadbycube.registry.SoundRegistry;
import deadbycube.registry.PowerRegistry;
import org.bukkit.Particle;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;

import java.util.Random;

public class KillerDoctor extends KillerPlayer {

    private static final Random RANDOM = new Random();

    private byte breathTick = 0;
    private byte particleTick = 0;

    public KillerDoctor(DeadByCube plugin, Player player) {
        super(plugin, player, "doctor", PowerRegistry.CARTERS_SPARK);
    }

    @Override
    void update() {
        if (++breathTick == 25) {
            this.breathTick = 0;
            this.playBreathSound();
        }

        if (++particleTick == 2) {
            this.particleTick = 0;
            player.getWorld().spawnParticle(
                    Particle.CLOUD,
                    player.getLocation().add((RANDOM.nextDouble() * 10) - 5, .1, (RANDOM.nextDouble() * 10) - 5), 5,
                    1, 0, 1,
                    0);
        }

    }

    @Override
    void playBreathSound() {
        super.playBreathSound();
        plugin.getAudioManager().playSound(SoundRegistry.KILLER_DOCTOR_BREATH_TEETH, SoundCategory.VOICE, player.getLocation(), (float) getBreathVolume().getValue());
    }
}
