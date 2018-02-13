package deadbycube.player.killer.power.cartersspark.madness;

import deadbycube.util.MagicalValue;
import org.bukkit.Location;

public interface MadnessEmitter {

    Location getLocation();

    MagicalValue getRadius();

    MagicalValue getMadnessMultiplier();

}
