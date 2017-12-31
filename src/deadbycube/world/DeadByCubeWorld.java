package deadbycube.world;

import deadbycube.world.tile.WorldTile;
import org.bukkit.Bukkit;
import org.bukkit.World;

public class DeadByCubeWorld {

    public static final int WORLD_Y = 50;

    private final World world;
    private final WorldTile[] tiles;

    private final int sizeX;
    private final int sizeZ;

    public DeadByCubeWorld(World world, WorldTile[] tiles, int sizeX, int sizeZ) {
        this.world = world;
        this.tiles = tiles;
        this.sizeX = sizeX;
        this.sizeZ = sizeZ;
    }

    public void unload() {
        Bukkit.unloadWorld(world, false);
    }

    public WorldTile getTile(int x, int z) {
        return tiles[(z * sizeX) + x];
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeZ() {
        return sizeZ;
    }

    public World getWorld() {
        return world;
    }

}
