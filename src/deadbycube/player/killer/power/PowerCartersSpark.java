package deadbycube.player.killer.power;

import deadbycube.player.killer.Killer;

public class PowerCartersSpark extends Power {

    private static final int SHOCK_CHARGE_TIME = 40;

    private int chargeTime;

    public PowerCartersSpark(Killer killer) {
        super(killer);
    }

    @Override
    public boolean canUse() {
        return true;
    }

    @Override
    protected void onUse() {
        this.chargeTime = 0;
    }

    @Override
    protected void onUpdate() {
        if (++chargeTime >= SHOCK_CHARGE_TIME) {

            this.stopUsing();
        }
    }

    @Override
    protected void onStopUse() {
    }

}
