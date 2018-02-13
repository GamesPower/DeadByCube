package deadbycube.interaction;

import deadbycube.player.DeadByCubePlayer;
import deadbycube.player.PlayerType;
import deadbycube.util.MathUtils;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public abstract class WorldInteraction extends Interaction {

    private final Location location;
    private final double distance;
    private final double angle;
    private final PlayerType[] playerTypes;

    public WorldInteraction(String name, Location location, double distance, double angle, PlayerType... playerTypes) {
        super(name);
        this.location = location;
        this.distance = distance;
        this.angle = angle;
        this.playerTypes = playerTypes;
    }

    @Override
    public boolean canInteract(DeadByCubePlayer deadByCubePlayer) {
        if (!super.canInteract(deadByCubePlayer))
            return false;
        if (!ArrayUtils.contains(playerTypes, deadByCubePlayer.getType()))
            return false;

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

    public PlayerType[] getPlayerTypes() {
        return playerTypes;
    }

}
