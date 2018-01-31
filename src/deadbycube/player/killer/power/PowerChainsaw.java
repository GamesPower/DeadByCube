package deadbycube.player.killer.power;

import deadbycube.player.killer.KillerPlayer;

public class PowerChainsaw extends Power {

    public PowerChainsaw(KillerPlayer killer) {
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
