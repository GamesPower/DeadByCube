package deadbycube.player.killer;

import deadbycube.DeadByCube;
import deadbycube.player.killer.power.PowerCartersSpark;
import org.bukkit.entity.Player;

public class KillerDoctor extends Killer {

    public KillerDoctor(DeadByCube plugin, Player player) {
        super(plugin, player, "doctor");
    }

    @Override
    public void init() {
        this.setPower(new PowerCartersSpark(this));
    }

}
