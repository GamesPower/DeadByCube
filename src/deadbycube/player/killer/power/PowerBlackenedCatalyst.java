package deadbycube.player.killer.power;

import deadbycube.player.killer.KillerPlayer;

public class PowerBlackenedCatalyst extends Power {

    public PowerBlackenedCatalyst(KillerPlayer killer) {
        super(killer);
    }

    @Override
    public void init() {
        super.init();

        killer.getTerrorRadius().setBaseValue(28);
    }

    @Override
    public void reset() {
    }

}
