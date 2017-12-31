package deadbycube.player.actionhandler;

import deadbycube.player.killer.Killer;
import deadbycube.player.killer.power.Power;

public class KillerActionHandler extends PlayerActionHandler<Killer> {

    public KillerActionHandler(Killer killer) {
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
