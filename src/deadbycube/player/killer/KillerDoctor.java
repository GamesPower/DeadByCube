package deadbycube.player.killer;

import deadbycube.DeadByCube;
import deadbycube.player.killer.power.PowerRegistry;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

public class KillerDoctor extends Killer {

    private byte particleTick = 0;

    public KillerDoctor(DeadByCube plugin, Player player) {
        super(plugin, player, "doctor", PowerRegistry.CARTERS_SPARK);
    }

    @Override
    void update() {
        if (++particleTick == 4) {
            this.particleTick = 0;
            player.getWorld().spawnParticle(
                    Particle.CLOUD,
                    player.getLocation().add(0, .4, 0), 10,
                    .3, .1, .3,
                    1);
        }
    }
}
