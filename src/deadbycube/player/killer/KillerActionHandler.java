package deadbycube.player.killer;

import deadbycube.player.PlayerActionHandler;
import deadbycube.player.killer.power.Power;

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

    @Override
    public void move() {
    }

    @Override
    public void toggleSneak(boolean sneaking) {
        /*if (sneaking)
            player.use();
        else
            player.stopUsing();*/
    }

}
