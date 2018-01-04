package deadbycube.player.killer.power;

import deadbycube.player.killer.Killer;

public class PowerBlackenedCatalyst extends Power {

    public PowerBlackenedCatalyst(Killer killer) {
        super(killer);
    }

    @Override
    public void init(boolean using) {
        super.init(using);

        killer.getTerrorRadius().setBaseValue(28);
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
