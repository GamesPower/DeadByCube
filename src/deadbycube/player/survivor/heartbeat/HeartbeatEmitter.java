package deadbycube.player.survivor.heartbeat;

import deadbycube.util.MagicalValue;
import org.bukkit.Location;

public interface HeartbeatEmitter {

    Location getLocation();

    MagicalValue getTerrorRadius();

}
