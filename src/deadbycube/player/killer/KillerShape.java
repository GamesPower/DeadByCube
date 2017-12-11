package deadbycube.player.killer;

import deadbycube.DeadByCube;
import deadbycube.player.killer.power.evilwithin.PowerEvilWithin1;
import org.bukkit.entity.Player;

public class KillerShape extends Killer {

    public KillerShape(DeadByCube plugin, Player player) {
        super(plugin, player, "shape");
    }

    @Override
    protected void init() {
        this.setPower(new PowerEvilWithin1(this));
    }

}
