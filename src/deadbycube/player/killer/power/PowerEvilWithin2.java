package deadbycube.player.killer.power;

import deadbycube.player.killer.Killer;

public class PowerEvilWithin2 extends Power {

    protected PowerEvilWithin2(Killer killer) {
        super(killer);
    }

    @Override
    public void reset() {
        this.stopUsing();
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
