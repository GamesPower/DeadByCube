package deadbycube.player.killer;

import deadbycube.player.PlayerActionHandler;
import deadbycube.player.killer.power.Power;

public class KillerActionHandler extends PlayerActionHandler<KillerPlayer> {

    KillerActionHandler(KillerPlayer killer) {
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
