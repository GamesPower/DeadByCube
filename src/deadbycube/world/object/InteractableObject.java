package deadbycube.world.object;

import deadbycube.util.TickLoop;
import org.bukkit.Location;

public abstract class InteractableObject extends WorldObject {

    private final TickLoop tickLoop;

    protected InteractableObject(Location location) {
        super(location);

        this.tickLoop = new TickLoop(this::update);
    }

    public void init() {
        this.tickLoop.startTask();
    }

    protected void stopUpdate() {
        this.tickLoop.stopTask();
    }

    protected abstract void update();

}
