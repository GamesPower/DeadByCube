package deadbycube.player.killer.power;

import deadbycube.player.killer.Killer;

public class PowerBearTrap extends Power {

    public PowerBearTrap(Killer killer) {
        super(killer);
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
