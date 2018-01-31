package deadbycube.game.interaction;

import deadbycube.player.DeadByCubePlayer;
import deadbycube.util.Tickable;
import org.bukkit.Location;

import java.util.ArrayList;

public abstract class Interaction {

    private final InteractionType type;
    private final String name;
    private final Location location;
    private final double distance;
    final ArrayList<DeadByCubePlayer> players = new ArrayList<>();

    private final Tickable tickable;

    Interaction(InteractionType type, String name, Location location, double distance) {
        this.type = type;
        this.name = name;
        this.location = location;
        this.distance = distance;
        this.tickable = new Tickable(this::update);
        this.tickable.startTask();
    }

    public void reset() {
        this.tickable.stopTask();
    }

    void update() {
        this.onUpdate();
    }

    public abstract void onUpdate();

    protected abstract void onPlayerStartInteract(DeadByCubePlayer deadByCubePlayer);

    protected abstract void onPlayerStopInteract(DeadByCubePlayer deadByCubePlayer);

    public abstract boolean canInteract(DeadByCubePlayer deadByCubePlayer);

    public void startInteract(DeadByCubePlayer deadByCubePlayer) {
        this.players.add(deadByCubePlayer);
        this.onPlayerStartInteract(deadByCubePlayer);
    }

    public void stopInteract(DeadByCubePlayer deadByCubePlayer) {
        this.onPlayerStopInteract(deadByCubePlayer);
        this.players.remove(deadByCubePlayer);
    }

    public String getName() {
        return name;
    }

    public Location getLocation() {
        return location;
    }

    public double getDistance() {
        return distance;
    }

    public InteractionType getType() {
        return type;
    }

}
