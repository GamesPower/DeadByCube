package deadbycube.game.interaction;

import deadbycube.player.DeadByCubePlayer;
import org.bukkit.Location;

public abstract class Interaction {

    private final String name;
    private final Location location;
    private final double distance;

    public Interaction(String name, Location location, double distance) {
        this.name = name;
        this.location = location;
        this.distance = distance;
    }

    public abstract boolean canInteract(DeadByCubePlayer deadByCubePlayer);

    protected abstract void interact(DeadByCubePlayer deadByCubePlayer);

    public String getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }

    public double getDistance() {
        return distance;
    }
}
