package deadbycube.player.killer.power.beartrap;

import deadbycube.interaction.InteractionActionBinding;
import deadbycube.interaction.InteractionManager;
import deadbycube.player.killer.KillerPlayer;
import deadbycube.player.killer.power.Power;
import deadbycube.player.killer.power.beartrap.interaction.SetTrapInteraction;
import deadbycube.util.MagicalValue;

public class PowerBearTrap extends Power {

    private final SetTrapInteraction setTrapInteraction;

    private final MagicalValue defaultTrapsCount = new MagicalValue(1);
    private final MagicalValue settingsTime = new MagicalValue(40);

    private int trapsCount = 0;

    public PowerBearTrap(KillerPlayer killer) {
        super(killer);

        this.setTrapInteraction = new SetTrapInteraction(this);
    }

    @Override
    public void init() {
        super.init();

        /*InteractionManager interactionManager = killer.getInteractionManager();
        interactionManager.registerInteraction(InteractionActionBinding.USE, setTrapInteraction);
        interactionManager.updateInteractions();*/

        this.trapsCount = (int) defaultTrapsCount.getValue();
    }

    @Override
    public void reset() {
    }

    public void addBearTrap() {
        this.trapsCount++;
    }

    public void removeBearTrap() {
        this.trapsCount--;
    }

    public int getTrapCount() {
        return trapsCount;
    }

    public MagicalValue getSettingsTime() {
        return settingsTime;
    }
}
