package deadbycube.player.killer;

import deadbycube.DeadByCube;
import deadbycube.registry.PowerRegistry;
import org.bukkit.entity.Player;

public class KillerWraith extends KillerPlayer {

    private byte breathTick = 0;

    public KillerWraith(DeadByCube plugin, Player player) {
        super(plugin, player, "wraith", PowerRegistry.INVISIBILITY_BELL);
    }

    @Override
    void update() {
        if (++breathTick == 15) {
            this.breathTick = 0;
            this.playBreathSound();
        }
    }
}
