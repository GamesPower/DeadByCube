package deadbycube.player.killer;

import deadbycube.DeadByCube;
import deadbycube.player.killer.power.PowerRegistry;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;

public class KillerNurse extends KillerPlayer {

    private byte breathTick = 0;
    private byte particleTick = 0;

    public KillerNurse(DeadByCube plugin, Player player) {
        super(plugin, player, "nurse", PowerRegistry.SPENCERS_LAST_BREATH);
    }

    @Override
    public void init() {
        super.init();

        this.getBreathVolume().setBaseValue(.05f);
    }

    @Override
    void update() {
        if (++breathTick == 40) {
            this.breathTick = 0;
            this.playBreathSound();
        }

        if (++particleTick == 4) {
            this.particleTick = 0;
            player.getWorld().spawnParticle(
                    Particle.FALLING_DUST,
                    player.getLocation().add(0, .4, 0), 5,
                    .3, .1, .3,
                    1, new MaterialData(Material.COAL_BLOCK)
            );
        }
    }
}
