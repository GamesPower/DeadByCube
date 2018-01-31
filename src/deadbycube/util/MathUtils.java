package deadbycube.util;

import org.bukkit.util.Vector;

public class MathUtils {

    private MathUtils() {
    }

    public static Vector direction(double yaw, double pitch) {
        double pitchRad = Math.toRadians(pitch);
        double yawRad = Math.toRadians(yaw);
        double pitchCos = Math.cos(pitchRad);
        return new Vector(
                -pitchCos * Math.sin(yawRad),
                -Math.sin(pitchRad),
                pitchCos * Math.cos(yawRad)
        );
    }

    public static double angle(double x1, double z1, double x2, double z2) {
        double dot = x1 * x2 + z1 * z2;
        double magnitude1 = Math.sqrt(Math.pow(x1, 2) + Math.pow(z1, 2));
        double magnitude2 = Math.sqrt(Math.pow(x2, 2) + Math.pow(z2, 2));

        return Math.acos(dot / (magnitude1 * magnitude2));
    }

    public static Vector rotate(double x, double y, double z, double angle) {
        double angleCos = Math.cos(angle);
        double angleSin = Math.sin(angle);
        return new Vector(
                angleCos * x - angleSin * z,
                y,
                angleSin * x + angleCos * z
        );
    }

    public static Vector rotate(Vector vector, double angle) {
        double angleCos = Math.cos(angle);
        double angleSin = Math.sin(angle);
        return new Vector(
                angleCos * vector.getX() - angleSin * vector.getZ(),
                vector.getY(),
                angleSin * vector.getX() + angleCos * vector.getZ()
        );
    }

}
