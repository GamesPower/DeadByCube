package deadbycube.world.object;

import org.bukkit.Location;

public abstract class WorldObject {

    private final Location location;

    protected WorldObject(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }

}
