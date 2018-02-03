package deadbycube.game.attack.state;

import deadbycube.game.attack.AttackManager;
import deadbycube.game.attack.KillerAttackManager;
import deadbycube.player.killer.KillerPlayer;
import deadbycube.util.MagicalValue;

public class SuccessfulAttackRecoveryHandler extends AttackHandler {

    private final MagicalValue recoveryDuration = new MagicalValue(40d);

    SuccessfulAttackRecoveryHandler(KillerAttackManager attackManager) {
        super(attackManager);
    }

    @Override
    public void update(int attackTick) {
        KillerPlayer killer = attackManager.getKiller();
        MagicalValue walkSpeed = killer.getWalkSpeed();

        if (attackTick == 0)
            walkSpeed.addModifier(AttackManager.SUCCESSFUL_ATTACK_RECOVERY_SPEED_MODIFIER, -1.2, MagicalValue.Operation.MULTIPLY);
        else if (attackTick == recoveryDuration.getValue()) {
            walkSpeed.removeModifier(AttackManager.SUCCESSFUL_ATTACK_RECOVERY_SPEED_MODIFIER);
            attackManager.setHandler(null);
        }
    }

}
