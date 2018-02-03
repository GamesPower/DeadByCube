package deadbycube.interaction;

import deadbycube.player.DeadByCubePlayer;
import deadbycube.util.Tickable;

public abstract class Interaction {

    private final InteractionActionBinding actionBinding;
    private final String name;
    private final Tickable tickable;

    protected DeadByCubePlayer deadByCubePlayer;
    private int tick = 0;

    public Interaction(InteractionActionBinding actionBinding, String name) {
        this.actionBinding = actionBinding;
        this.name = name;
        this.tickable = new Tickable(this::update);
    }

    private void update() {
        if (isInteracting())
            this.onUpdate(++tick);
        else
            this.stopInteract();
    }

    public void interact(DeadByCubePlayer deadByCubePlayer) {
        this.deadByCubePlayer = deadByCubePlayer;

        this.onInteract();
        this.tickable.startTask();
    }

    public boolean isInteracting(DeadByCubePlayer deadByCubePlayer) {
        return this.deadByCubePlayer == deadByCubePlayer && (this.deadByCubePlayer != null && this.isInteracting());
    }

    protected void stopInteract() {
        this.tickable.stopTask();
        this.onStopInteract(tick);

        this.deadByCubePlayer = null;
        this.tick = 0;
    }

    public abstract void onInteract();

    public abstract void onUpdate(int tick);

    public abstract void onStopInteract(int tick);

    public abstract boolean isInteracting();

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
