package deadbycube.player.killer;

import deadbycube.DeadByCube;
import deadbycube.player.killer.power.PowerDebug;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class KillerDebug extends Killer {

    public KillerDebug(DeadByCube plugin, Player player) {
        super(plugin, player, EntityType.BAT);
    }

    @Override
    protected void init() {
        this.setPower(new PowerDebug(this));
    }

}
