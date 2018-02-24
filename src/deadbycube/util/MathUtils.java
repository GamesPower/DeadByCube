package deadbycube.util;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
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
        return MathUtils.rotate(vector.getX(), vector.getY(), vector.getZ(), angle);
    }

    public static Vector getRightDirection(Location location) {
        return location.getDirection().normalize().crossProduct(location.toVector().setY(1).normalize());
    }

    public static boolean isLookingAt(Location location, AxisAlignedBB AxisAlignedBB, double maxDistance, double accuracy) {
        for (double i = 0; i < maxDistance; i += accuracy) {
            location.add(location.getDirection().multiply(accuracy));
            if (!location.getBlock().isEmpty())
                return false;
            if (AxisAlignedBB.isCollide(location.toVector()))
                return true;
        }
        return false;
    }

    public static void displayVector(Location location, Vector vector) {
        World world = location.getWorld();
        Location clone = location.clone();
        Vector step = vector.clone().divide(new Vector(10, 10, 10));
        for (int i = 0; i < 10; i++) {
            world.spawnParticle(Particle.REDSTONE, clone.add(step), 1, 0, 0, 0, 0);
        }
    }

}
