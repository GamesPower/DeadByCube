package deadbycube.player.killer.power.huntinghatchets;

import deadbycube.interaction.InteractionActionBinding;
import deadbycube.interaction.InteractionManager;
import deadbycube.player.killer.KillerPlayer;
import deadbycube.player.killer.power.Power;
import deadbycube.player.killer.power.huntinghatchets.interaction.ThrowHatchetInteraction;
import deadbycube.util.MagicalValue;

public class PowerHuntingHatchets extends Power {

    private final ThrowHatchetInteraction throwHatchetInteraction;

    private final MagicalValue chargeTime = new MagicalValue(40);
    private final MagicalValue requiredChargeTime = new MagicalValue(20);
    private final MagicalValue maxHatchets = new MagicalValue(5);

    private int hatchetsCount = 0;

    public PowerHuntingHatchets(KillerPlayer killer) {
        super(killer);
        this.throwHatchetInteraction = new ThrowHatchetInteraction(this);
    }

    @Override
    public void init() {
        super.init();

        killer.getTerrorRadius().setBaseValue(20);

        this.reloadHatchets();
    }

    @Override
    public void reset() {
    }

    public void reloadHatchets() {
        if (hatchetsCount == 0) {
            InteractionManager interactionManager = killer.getInteractionManager();
            interactionManager.registerInteraction(InteractionActionBinding.USE, throwHatchetInteraction);
        }

        this.hatchetsCount = (int) maxHatchets.getValue();
        this.killer.setOffHandItemAmount(hatchetsCount);
    }

    public void useHatchet() {
        this.hatchetsCount--;
        this.killer.setOffHandItemAmount(hatchetsCount);

        if (hatchetsCount == 0) {
            InteractionManager interactionManager = killer.getInteractionManager();
            interactionManager.unregisterInteraction(InteractionActionBinding.USE, throwHatchetInteraction);
        }
    }

    public MagicalValue getChargeTime() {
        return chargeTime;
    }

    public MagicalValue getRequiredChargeTime() {
        return requiredChargeTime;
    }

}
