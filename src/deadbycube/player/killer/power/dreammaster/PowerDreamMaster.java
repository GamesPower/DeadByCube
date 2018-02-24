package deadbycube.player.killer.power.dreammaster;

import deadbycube.interaction.InteractionActionBinding;
import deadbycube.interaction.InteractionManager;
import deadbycube.player.killer.KillerPlayer;
import deadbycube.player.killer.power.Power;
import deadbycube.player.killer.power.dreammaster.interaction.PullInDreamInteraction;
import deadbycube.util.MagicalValue;

public class PowerDreamMaster extends Power {

    private final MagicalValue range = new MagicalValue(8);

    private final PullInDreamInteraction pullInDreamInteraction;

    public PowerDreamMaster(KillerPlayer killer) {
        super(killer);

        this.pullInDreamInteraction = new PullInDreamInteraction(this);
    }

    @Override
    public void init() {
        super.init();

        killer.getTerrorRadius().setBaseValue(24);

        InteractionManager interactionManager = killer.getInteractionManager();
        interactionManager.registerInteraction(InteractionActionBinding.USE, pullInDreamInteraction);
    }

    @Override
    public void reset() {
    }

    public MagicalValue getRange() {
        return range;
    }

}
