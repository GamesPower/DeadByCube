package deadbycube.player.killer;

import deadbycube.DeadByCube;
import deadbycube.player.killer.power.PowerRegistry;
import org.bukkit.entity.Player;

public class KillerWraith extends Killer {

    public KillerWraith(DeadByCube plugin, Player player) {
        super(plugin, player, "wraith", PowerRegistry.INVISIBILITY_BELL);
    }

    @Override
    void update() {

    }
}
