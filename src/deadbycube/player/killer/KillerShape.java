package deadbycube.player.killer;

import deadbycube.DeadByCube;
import deadbycube.player.PlayerManager;
import deadbycube.player.killer.power.PowerEvilWithin1;
import deadbycube.player.survivor.Survivor;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class KillerShape extends Killer {

    public KillerShape(DeadByCube plugin, Player player) {
        super(plugin, player, EntityType.WITHER_SKELETON);
    }

    @Override
    protected void init() {
        this.setPower(new PowerEvilWithin1(this));
    }

}
