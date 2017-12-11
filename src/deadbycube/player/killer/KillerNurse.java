package deadbycube.player.killer;

import deadbycube.DeadByCube;
import deadbycube.player.killer.power.PowerBreath;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class KillerNurse extends Killer {

    public KillerNurse(DeadByCube plugin, Player player) {
        super(plugin, player, EntityType.STRAY);
    }

    @Override
    protected void init() {
        this.setPower(new PowerBreath(this));
    }

}
