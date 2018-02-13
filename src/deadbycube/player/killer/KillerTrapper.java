package deadbycube.player.killer;

import deadbycube.DeadByCube;
import deadbycube.registry.PowerRegistry;
import org.bukkit.entity.Player;

public class KillerTrapper extends KillerPlayer {

    private byte breathTick = 0;

    public KillerTrapper(DeadByCube plugin, Player player) {
        super(plugin, player, "trapper", PowerRegistry.BEAR_TRAP);
    }

    @Override
    public void init() {
        super.init();

        this.getBreathVolume().setBaseValue(.15f);
    }

    @Override
    void update() {
        if (++breathTick == 35) {
            this.breathTick = 0;
            this.playBreathSound();
        }
    }

}
