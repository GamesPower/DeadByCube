package deadbycube.world.generator;

import deadbycube.DeadByCube;
import deadbycube.world.DeadByCubeWorld;
import deadbycube.game.object.WorldObject;
import deadbycube.world.tile.WorldTile;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.file.FileConfiguration;

public class DeadByCubeWorldGenerator {

    private final String worldName;
    private final int sizeX;
    private final int sizeZ;

    private final World world;

    public DeadByCubeWorldGenerator(DeadByCube plugin) {
        FileConfiguration config = plugin.getConfig();
        this.worldName = config.getString("world.entity.name", "world_entity");
        this.sizeX = config.getInt("world.entity.size.x", 12);
        this.sizeZ = config.getInt("world.entity.size.z", 12);

        this.world = getEntityWorld();
    }

    private World getEntityWorld() {
        World world = Bukkit.getWorld(worldName);
        return world == null ? createWorld() : world;
    }

    private World createWorld() {
        World world = Bukkit.createWorld(new WorldCreator(worldName)
                .generator(new DeadByCubeChunkGenerator(this))
                .generateStructures(false)
        );
        world.setPVP(false);
        world.setAutoSave(false);
        world.setSpawnFlags(false, false);
        world.setTime(13600);
        world.setSpawnLocation(0, DeadByCubeWorld.WORLD_Y + 1, 0);

        world.setGameRuleValue("doDaylightCycle", "false");
        world.setGameRuleValue("doEntityDrops", "false");
        world.setGameRuleValue("doFireTick", "false");
        world.setGameRuleValue("doMobLoot", "false");
        world.setGameRuleValue("doMobSpawning", "false");
        world.setGameRuleValue("doTileDrops", "false");
        world.setGameRuleValue("doWeatherCycle", "false");
        world.setGameRuleValue("keepInventory", "true");
        world.setGameRuleValue("maxEntityCramming", "0");
        world.setGameRuleValue("mobGriefing", "false");
        world.setGameRuleValue("naturalRegeneration", "false");
        world.setGameRuleValue("randomTickSpeed", "0");
        world.setGameRuleValue("reducedDebugInfo", "true");
        world.setGameRuleValue("spawnRadius", "0");
        world.setGameRuleValue("spectatorsGenerateChunks", "false");

        return world;
    }

    // add tile (category, tile pattern)
    // add structure (x, z, xSize, zSize)

    public DeadByCubeWorld generate() {
        WorldTile[] worldTiles = new WorldTile[sizeX * sizeZ];
        for (int x = 0; x < sizeX; x++) {
            for (int z = 0; z < sizeZ; z++) {
                int index = (z * sizeX) + x;
                worldTiles[index] = new WorldTile(new WorldObject[0]);
            }
        }

        return new DeadByCubeWorld(world, worldTiles, sizeX, sizeZ);
    }

    int getSizeX() {
        return sizeX;
    }

    int getSizeZ() {
        return sizeZ;
    }

}
