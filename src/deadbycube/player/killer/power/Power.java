package deadbycube.player.killer.power;

import deadbycube.player.killer.Killer;
import deadbycube.util.Tickable;

public abstract class Power {

    protected final Killer killer;
    private final Tickable tickable;

    private boolean update = true;

    protected Power(Killer killer) {
        this.killer = killer;
        this.tickable = new Tickable(killer.getPlugin(), this::update);
    }

    public void reset() {
        this.tickable.stopTask();
    }

    private void update() {
        if (!isUsing()) {
            this.tickable.stopTask();
            this.onStopUse();
        } else if (update)
            this.onUpdate();
    }

    public abstract boolean canUse();

    protected abstract void onUse();

    protected abstract void onUpdate();

    protected abstract void onStopUse();

    private boolean isUsing() {
        return killer.getPlayer().isHandRaised();
    }

    public void use() {
        this.onUse();
        this.update = true;
        this.tickable.startTask();
    }

    protected void stopUpdate() {
        this.update = false;
    }

}
