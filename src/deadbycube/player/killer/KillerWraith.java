package deadbycube.player.killer;

import deadbycube.DeadByCube;
import deadbycube.registry.PowerRegistry;
import deadbycube.registry.SkinRegistry;
import org.bukkit.entity.Player;

public class KillerWraith extends KillerPlayer {

    public KillerWraith(DeadByCube plugin, Player player) {
        super(plugin, player, "wraith", (byte) 15, SkinRegistry.THE_WRAITH, PowerRegistry.INVISIBILITY_BELL);
    }

}
