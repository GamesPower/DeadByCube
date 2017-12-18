package deadbycube.player.killer;

import deadbycube.DeadByCube;
import deadbycube.player.killer.power.PowerBell;
import org.bukkit.entity.Player;

public class KillerWraith extends Killer {

    public KillerWraith(DeadByCube plugin, Player player) {
        super(plugin, player, "wraith");
    }

    @Override
    public void init() {
        this.setPower(new PowerBell(this));
    }

}
