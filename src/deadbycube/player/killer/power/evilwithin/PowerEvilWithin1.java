package deadbycube.player.killer.power.evilwithin;

import deadbycube.player.killer.Killer;
import deadbycube.util.DBDSounds;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class PowerEvilWithin1 extends PowerEvilWithin {

    private static final float WALK_SPEED = 0.15f;

    public PowerEvilWithin1(Killer killer) {
        super(killer);

        killer.getPlayer().setWalkSpeed(WALK_SPEED);
    }

    @Override
    void onStalk() {
        if (stalked == 100) {
            Player player = killer.getPlayer();
            World world = player.getWorld();
            world.playSound(player.getLocation(), DBDSounds.KILLER_SHAPE_STALK_LEVEL_1, 100, 1);
            killer.setPower(new PowerEvilWithin2(killer));
        }
    }

}
