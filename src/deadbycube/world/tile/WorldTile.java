package deadbycube.world.tile;

import org.bukkit.Location;

public class WorldTile {

    public static final int SIZE = 13;

    private final Location[] generatorLocations;
    private final Location[] hookLocations;
    private final Location[] chestLocations;
    private final Location[] pullDownLocations;
    private final Location[] windowLocations;

    public WorldTile(Location[] generatorLocations, Location[] hookLocations, Location[] chestLocations, Location[] pullDownLocations, Location[] windowLocations) {
        this.generatorLocations = generatorLocations;
        this.hookLocations = hookLocations;
        this.chestLocations = chestLocations;
        this.pullDownLocations = pullDownLocations;
        this.windowLocations = windowLocations;
    }

}
