package deadbycube.player.killer.power;

import deadbycube.player.killer.Killer;

public class PowerDebug extends Power {

    public PowerDebug(Killer killer) {
        super(killer);
    }

    @Override
    public boolean canUse() {
        return true;
    }

    @Override
    protected void onUse() {
        killer.getPlayer().sendMessage("onUse");
    }

    @Override
    protected void onUpdate() {
        killer.getPlayer().sendMessage("onUpdate");
    }

    @Override
    protected void onStopUse() {
        killer.getPlayer().sendMessage("onStopUse");
    }
}
