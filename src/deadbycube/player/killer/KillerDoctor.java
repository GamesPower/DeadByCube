package deadbycube.player.killer;

import deadbycube.DeadByCube;
import deadbycube.player.DbcPlayer;
import deadbycube.player.PlayerActionHandler;
import deadbycube.player.PlayerType;
import deadbycube.player.killer.power.PowerCartersSpark;
import org.bukkit.entity.Player;

public class KillerDoctor extends Killer {

    public KillerDoctor(DeadByCube plugin, Player player) {
        super(plugin, player, "doctor");
    }

    @Override
    protected void init() {
        this.setPower(new PowerCartersSpark(this));
    }

}
