package deadbycube.game.object.generator;

import deadbycube.game.object.InteractableObjects;
import org.bukkit.Location;

public class GeneratorObject extends InteractableObjects {

    public static final double CHARGE = 80d;

    protected GeneratorObject(Location location) {
        super(location);
    }

}
