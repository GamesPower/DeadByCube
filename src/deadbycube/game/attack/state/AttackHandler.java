package deadbycube.game.attack.state;

import deadbycube.game.attack.KillerAttackManager;

public abstract class AttackHandler {

    final KillerAttackManager attackManager;

    AttackHandler(KillerAttackManager attackManager) {
        this.attackManager = attackManager;
    }

    public abstract void update(int attackTick);

}
