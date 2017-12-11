package deadbycube.player.killer.power.evilwithin;

import deadbycube.player.killer.Killer;
import deadbycube.util.DBDSounds;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class PowerEvilWithin2 extends PowerEvilWithin {

    PowerEvilWithin2(Killer killer) {
        super(killer);
    }

    @Override
    void onStalk() {
        if (stalked == 100) {
            Player player = killer.getPlayer();
            World world = player.getWorld();
            world.playSound(player.getLocation(), DBDSounds.KILLER_SHAPE_STALK_LEVEL_3, 100, 1);
            killer.setPower(new PowerEvilWithin3(killer));
        }
    }


}
