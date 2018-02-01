package deadbycube.interaction;

import deadbycube.player.DeadByCubePlayer;
import deadbycube.util.Tickable;

public class Interaction {

    private final InteractionActionBinding actionBinding;
    private final String name;
    private final Tickable tickable;

    public Interaction(InteractionActionBinding actionBinding, String name) {
        this.actionBinding = actionBinding;
        this.name = name;
        this.tickable = new Tickable(this::update);
    }

    public void startInteract(DeadByCubePlayer deadByCubePlayer) {

    }

    public void stopInteract(DeadByCubePlayer deadByCubePlayer) {

    }

    private void update() {

    }

    public boolean canInteract(DeadByCubePlayer deadByCubePlayer) {
        return true;
    }

    public String getName() {
        return name;
    }

    public InteractionActionBinding getActionBinding() {
        return actionBinding;
    }

}
