package deadbycube.world.object.exitdoor;

import deadbycube.world.object.WorldObject;
import deadbycube.world.object.exitdoor.interaction.OpenInteraction;
import org.bukkit.Location;

public class ExitGateObject extends WorldObject {

    private static final double REQUIRED_CHARGE = 20;

    private final OpenInteraction openInteraction;

    private double charge = 0;

    protected ExitGateObject(Location location) {
        super(location);

        this.openInteraction = new OpenInteraction(this);
    }


}
