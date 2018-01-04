package deadbycube.player.killer.power;

import deadbycube.player.killer.Killer;

public class PowerHuntingHatchets extends Power {

    public PowerHuntingHatchets(Killer killer) {
        super(killer);
    }

    @Override
    public void init(boolean using) {
        super.init(using);

        killer.getTerrorRadius().setBaseValue(20);
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
