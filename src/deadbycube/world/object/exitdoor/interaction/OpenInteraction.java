package deadbycube.world.object.exitdoor.interaction;

import deadbycube.interaction.WorldInteraction;
import deadbycube.player.PlayerType;
import deadbycube.world.object.exitdoor.ExitGateObject;

public class OpenInteraction extends WorldInteraction {

    private final ExitGateObject exitGate;

    public OpenInteraction(ExitGateObject exitGate) {
        super("open", exitGate.getLocation(), 1, 60, PlayerType.SURVIVOR);

        this.exitGate = exitGate;
    }

    @Override
    protected void onInteract() {
        interactor.getWalkSpeed().forceValue(0);
    }

    @Override
    protected void onUpdate(int tick) {


    }

    @Override
    protected void onStopInteract(int tick) {
        interactor.getWalkSpeed().resetValue();
    }

}
