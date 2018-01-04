package deadbycube.player.killer;

import deadbycube.DeadByCube;
import deadbycube.audio.WorldAudioManager;
import deadbycube.audio.SoundRegistry;
import deadbycube.player.killer.power.PowerRegistry;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;

public class KillerNurse extends Killer {

    private byte breathTick = 0;
    private byte particleTick = 0;

    public KillerNurse(DeadByCube plugin, Player player) {
        super(plugin, player, "nurse", PowerRegistry.SPENCERS_LAST_BREATH);
    }

    @Override
    void update() {
        if (++breathTick == 40) {
            breathTick = 0;
            WorldAudioManager audioManager = plugin.getAudioManager();
            audioManager.playSound(SoundRegistry.KILLER_NURSE_BREATH, SoundCategory.VOICE, player.getLocation(), .05f, 1);
        }

        if (++particleTick == 4) {
            this.particleTick = 0;
            player.getWorld().spawnParticle(
                    Particle.FALLING_DUST,
                    player.getLocation().add(0, .4, 0), 10,
                    .3, .1, .3,
                    1, new MaterialData(Material.COAL_BLOCK)
            );
        }
    }
}
