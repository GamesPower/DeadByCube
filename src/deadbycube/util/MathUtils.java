package deadbycube.util;

import org.bukkit.util.Vector;

public class MathUtils {

    private MathUtils() {}

    public static Vector getDirection(double yaw, double pitch) {
        double xz = Math.cos(Math.toRadians(pitch));
        return new Vector(
                -xz * Math.sin(Math.toRadians(yaw)),
                -Math.sin(Math.toRadians(pitch)),
                xz * Math.cos(Math.toRadians(yaw))
        );
    }

}
