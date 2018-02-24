package deadbycube.player.killer;

import deadbycube.DeadByCube;
import deadbycube.registry.PowerRegistry;
import deadbycube.registry.SkinRegistry;
import org.bukkit.entity.Player;

public class KillerNightmare extends KillerPlayer {

    public KillerNightmare(DeadByCube plugin, Player player) {
        super(plugin, player, "nightmare", (byte) 120, SkinRegistry.THE_NIGHTMARE, PowerRegistry.DREAM_MASTER);
    }

}
