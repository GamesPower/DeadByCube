package deadbycube.player.killer;

import deadbycube.DeadByCube;
import deadbycube.registry.PowerRegistry;
import deadbycube.registry.SkinRegistry;
import org.bukkit.entity.Player;

public class KillerTrapper extends KillerPlayer {

    public KillerTrapper(DeadByCube plugin, Player player) {
        super(plugin, player, "trapper", (byte) 35, SkinRegistry.DEFAULT, PowerRegistry.BEAR_TRAP);
    }

    @Override
    public void init() {
        super.init();

        this.getBreathVolume().setBaseValue(.15f);
    }

}
