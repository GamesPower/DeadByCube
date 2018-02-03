package deadbycube.player.killer.power;

import deadbycube.player.killer.KillerPlayer;

public class PowerDreamMaster extends Power {

    public PowerDreamMaster(KillerPlayer killer) {
        super(killer);
    }

    @Override
    public void init() {
        super.init();

        killer.getTerrorRadius().setBaseValue(24);
    }

    @Override
    public void reset() {
    }

}
