package deadbycube.player.killer.power;

import deadbycube.player.killer.KillerPlayer;

public class PowerDreamMaster extends Power {

    public PowerDreamMaster(KillerPlayer killer) {
        super(killer);
    }

    @Override
    public void init(boolean using) {
        super.init(using);

        killer.getTerrorRadius().setBaseValue(24);
    }

    @Override
    public boolean canUse() {
        return true;
    }

    @Override
    protected void onUse() {
    }

    @Override
    protected void onUpdate() {
    }

    @Override
    protected void onStopUse() {
    }

}
