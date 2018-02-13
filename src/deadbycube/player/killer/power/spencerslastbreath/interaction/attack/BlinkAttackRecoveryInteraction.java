package deadbycube.player.killer.power.spencerslastbreath.interaction.attack;

import deadbycube.player.killer.interaction.attack.AttackRecoveryInteraction;
import deadbycube.player.killer.power.spencerslastbreath.PowerSpencersLastBreath;
import deadbycube.player.survivor.SurvivorPlayer;

public class BlinkAttackRecoveryInteraction extends AttackRecoveryInteraction {

    private final PowerSpencersLastBreath power;

    BlinkAttackRecoveryInteraction(PowerSpencersLastBreath power, int duration) {
        super(duration);

        this.power = power;
    }

    @Override
    protected void onStopInteract(int tick) {
        super.onStopInteract(tick);
        power.stun();
    }

}
