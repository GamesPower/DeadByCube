package deadbycube.player.killer;

import deadbycube.DeadByCube;
import deadbycube.registry.PowerRegistry;
import deadbycube.registry.SkinRegistry;
import org.bukkit.entity.Player;

public class KillerShape extends KillerPlayer {

    public KillerShape(DeadByCube plugin, Player player) {
        super(plugin, player, "shape", (byte) 65, SkinRegistry.THE_SHAPE, PowerRegistry.EVIL_WITHIN_1);
    }

}
