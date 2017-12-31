package deadbycube.util;

import org.bukkit.util.Vector;

public class MathUtils {

    private MathUtils() {
    }

    public static Vector getDirection(double yaw, double pitch) {
        double pitchCos = Math.cos(Math.toRadians(pitch));
        return new Vector(
                -pitchCos * Math.sin(Math.toRadians(yaw)),
                -Math.sin(Math.toRadians(pitch)),
                pitchCos * Math.cos(Math.toRadians(yaw))
        );
    }

    public static Vector rotate(Rotation rotation, Vector vector) {
        switch (rotation) {
            case CLOCKWISE_90:
                return new Vector(-vector.getZ(), vector.getY(), vector.getX());
            case CLOCKWISE_180:
                return new Vector(-vector.getX(), vector.getY(), -vector.getZ());
            case COUNTERCLOCKWISE_90:
                return new Vector(vector.getZ(), vector.getY(), -vector.getX());
            default:
                return vector;
        }
    }

    public enum Rotation {

        NONE, CLOCKWISE_90, CLOCKWISE_180, COUNTERCLOCKWISE_90

    }

}
