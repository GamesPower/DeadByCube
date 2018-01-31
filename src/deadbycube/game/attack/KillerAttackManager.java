package deadbycube.game.attack;

import deadbycube.game.attack.state.AttackHandler;
import deadbycube.game.attack.state.LungeAttackHandler;
import deadbycube.player.killer.KillerPlayer;
import deadbycube.util.Tickable;

public class KillerAttackManager {

    private final KillerPlayer killer;
    private final Tickable tickable;

    private int attackTick = 0;
    private AttackHandler handler = null;

    public KillerAttackManager(KillerPlayer killer) {
        this.killer = killer;
        this.tickable = new Tickable(this::update);
    }

    public boolean canAttack() {
        return handler == null && !tickable.isTicking();
    }

    public void attack() {
        this.handler = new LungeAttackHandler(this);
        this.tickable.startTask();
    }

    private void update() {
        if (handler != null)
            this.handler.update(attackTick++);
        else {
            this.tickable.stopTask();
            this.attackTick = 0;
        }
    }

    public void setHandler(AttackHandler handler) {
        this.handler = handler;
        this.attackTick = 0;
    }

    public KillerPlayer getKiller() {
        return killer;
    }

}
