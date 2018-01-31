package deadbycube.player.killer;

import deadbycube.DeadByCube;
import deadbycube.player.killer.power.PowerRegistry;
import org.bukkit.entity.Player;

public class KillerShape extends KillerPlayer {

    private byte breathTick = 0;

    public KillerShape(DeadByCube plugin, Player player) {
        super(plugin, player, "shape", PowerRegistry.EVIL_WITHIN_1);
    }

    @Override
    void update() {
        if (++breathTick == 65) {
            this.breathTick = 0;
            this.playBreathSound();
        }
    }

}
