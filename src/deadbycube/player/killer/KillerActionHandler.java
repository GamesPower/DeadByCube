package deadbycube.player.killer;

import deadbycube.player.PlayerActionHandler;
import deadbycube.player.killer.power.Power;
import org.bukkit.event.player.PlayerToggleSneakEvent;

public class KillerActionHandler extends PlayerActionHandler<Killer> {

    KillerActionHandler(Killer killer) {
        super(killer);
    }

    @Override
    public void attack() {
    }

    @Override
    public void interact() {
        Power power = player.getPower();
        if (power != null && power.canUse())
            power.use();
    }

}
