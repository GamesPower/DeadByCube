package deadbycube.player.killer.power.evilwithin;

import deadbycube.interaction.InteractionActionBinding;
import deadbycube.interaction.InteractionManager;
import deadbycube.player.killer.KillerPlayer;
import deadbycube.player.killer.power.Power;
import deadbycube.player.killer.power.evilwithin.interaction.StalkInteraction;

public abstract class PowerEvilWithin extends Power {

    private final StalkInteraction stalkInteraction;

    PowerEvilWithin(KillerPlayer killer) {
        super(killer);

        this.stalkInteraction = new StalkInteraction(this);
    }

    @Override
    public void init() {
        super.init();

        InteractionManager interactionManager = killer.getInteractionManager();
        interactionManager.registerInteraction(InteractionActionBinding.SNEAK, stalkInteraction);
        interactionManager.updateInteractions();
    }

    public abstract void onStalk(int stalk);

}
