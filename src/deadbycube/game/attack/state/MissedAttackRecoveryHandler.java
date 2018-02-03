package deadbycube.game.attack.state;

import deadbycube.game.attack.AttackManager;
import deadbycube.game.attack.KillerAttackManager;
import deadbycube.player.killer.KillerPlayer;
import deadbycube.util.MagicalValue;

public class MissedAttackRecoveryHandler extends AttackHandler {

    private final MagicalValue recoveryDuration = new MagicalValue(20d);

    MissedAttackRecoveryHandler(KillerAttackManager attackManager) {
        super(attackManager);
    }

    @Override
    public void update(int attackTick) {
        KillerPlayer killer = attackManager.getKiller();
        MagicalValue walkSpeed = killer.getWalkSpeed();

        if (attackTick == 0) {
            walkSpeed.addModifier(AttackManager.MISSED_ATTACK_RECOVERY_SPEED_MODIFIER, -.8, MagicalValue.Operation.MULTIPLY);
        } else if (attackTick == recoveryDuration.getValue()) {
            walkSpeed.removeModifier(AttackManager.MISSED_ATTACK_RECOVERY_SPEED_MODIFIER);
            attackManager.setHandler(null);
        }
    }

}
