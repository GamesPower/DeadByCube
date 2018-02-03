package deadbycube.player.killer.power;

import deadbycube.player.killer.KillerPlayer;

public class PowerHuntingHatchets extends Power {

    public PowerHuntingHatchets(KillerPlayer killer) {
        super(killer);
    }

    @Override
    public void init() {
        super.init();
        killer.getTerrorRadius().setBaseValue(20);
    }

    @Override
    public void reset() {
    }

}
