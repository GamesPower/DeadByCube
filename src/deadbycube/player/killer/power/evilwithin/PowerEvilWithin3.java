package deadbycube.player.killer.power.evilwithin;

import deadbycube.player.killer.Killer;
import deadbycube.player.killer.power.Power;
import deadbycube.util.DBDSounds;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class PowerEvilWithin3 extends PowerEvilWithin {

    PowerEvilWithin3(Killer killer) {
        super(killer);
    }

    @Override
    void onStalk() {
        this.stopUsing();
    }

}
