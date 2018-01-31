package deadbycube.util;

import org.bukkit.Location;
import org.bukkit.util.Vector;

public class EntityUtils {

    private EntityUtils() {
    }

    public static boolean inFieldOfView(Location cameraLocation, Location targetLocation, double fieldOfView, double minDistance, double maxDistance) {
        double distance = cameraLocation.distance(targetLocation);
        System.out.println("distance = " + distance);
        if (minDistance > distance || distance > maxDistance) return false;

        Vector cameraDirection = cameraLocation.getDirection();
        Vector direction = targetLocation.subtract(cameraLocation).toVector();

        double maxHRad = Math.toRadians(fieldOfView);
        double hRad = MathUtils.angle(direction.getX(), direction.getZ(), cameraDirection.getX(), cameraDirection.getZ());
        System.out.println("maxHRad = " + maxHRad);
        System.out.println("hRad = " + hRad);
        if (hRad > maxHRad) return false;

        double maxVRad = maxHRad * (9d / 16d);
        double vRad = MathUtils.angle(0, direction.getY(), 0, cameraDirection.getY());
        System.out.println("maxVRad = " + maxVRad);
        System.out.println("vRad = " + vRad);
        return (!(vRad > maxVRad));
    }

}
