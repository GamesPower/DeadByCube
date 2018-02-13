package deadbycube.interaction;

import deadbycube.player.DeadByCubePlayer;
import deadbycube.util.TickLoop;

public abstract class Interaction {

    private final String name;
    private final TickLoop tickLoop;

    protected DeadByCubePlayer interactor;
    private boolean interacting = false;
    private int tick = 0;

    public Interaction(String name) {
        this.name = name;
        this.tickLoop = new TickLoop(this::update);
    }

    private void update() {
        if (isInteracting())
            this.onUpdate(++tick);
        else
            this.stopInteract();
    }

    public void interact(DeadByCubePlayer deadByCubePlayer) {
        this.interacting = true;
        this.interactor = deadByCubePlayer;

        this.onInteract();
        this.tickLoop.startTask();
    }

    public void stopInteract() {
        this.tickLoop.stopTask();
        this.interacting = false;
        this.onStopInteract(tick);
        this.interactor = null;
        this.tick = 0;
    }

    public boolean canInteract(DeadByCubePlayer deadByCubePlayer) {
        return interactor == null;
    }

    protected abstract void onInteract();

    protected abstract void onUpdate(int tick);

    protected abstract void onStopInteract(int tick);

    public boolean isInteracting() {
        return interacting;
    }

    public DeadByCubePlayer getPlayer() {
        return interactor;
    }

    public String getName() {
        return name;
    }

}
