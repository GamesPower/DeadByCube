package deadbycube.interaction;

import deadbycube.player.DeadByCubePlayer;
import deadbycube.util.MathUtils;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class WorldInteraction extends Interaction {

    private final Location location;
    private final double distance;
    private final double angle;

    public WorldInteraction(InteractionActionBinding actionBinding, String name, Location location, double distance, double angle) {
        super(actionBinding, name);
        this.location = location;
        this.distance = distance;
        this.angle = angle;
    }

    @Override
    public boolean canInteract(DeadByCubePlayer deadByCubePlayer) {
        Player player = deadByCubePlayer.getPlayer();
        Location playerLocation = player.getLocation();

        double playerInteractionDistance = playerLocation.distance(location);
        if (playerInteractionDistance >= this.distance)
            return false;

        Vector playerDirection = MathUtils.direction(playerLocation.getYaw(), 0);
        Vector playerToInteractionDirection = location.clone().subtract(playerLocation).toVector();
        double playerInteractionAngle = Math.toDegrees(playerToInteractionDirection.angle(playerDirection));
        return -angle < playerInteractionAngle && playerInteractionAngle < angle;
    }

    public Location getLocation() {
        return location;
    }

    public double getDistance() {
        return distance;
    }

    public double getAngle() {
        return angle;
    }

}
