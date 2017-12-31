package deadbycube.world.generator;

import deadbycube.world.DeadByCubeWorld;
import deadbycube.world.tile.WorldTile;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.material.MaterialData;

import java.util.Random;

public class DeadByCubeChunkGenerator extends ChunkGenerator {

    private static final MaterialData[] FLOOR_MATERIAL_DATA = new MaterialData[]{
            new MaterialData(Material.GRASS),
            new MaterialData(Material.DIRT, (byte) 1),
            new MaterialData(Material.DIRT, (byte) 2)
    };

    private final int chunkSizeX;
    private final int chunkSizeZ;

    DeadByCubeChunkGenerator(DeadByCubeWorldGenerator worldGenerator) {
        this.chunkSizeX = (worldGenerator.getSizeX() * WorldTile.SIZE) / 16;
        this.chunkSizeZ = (worldGenerator.getSizeZ() * WorldTile.SIZE) / 16;
    }

    @Override
    public ChunkData generateChunkData(World world, Random random, int chunkX, int chunkZ, BiomeGrid biome) {
        ChunkData chunkData = this.createChunkData(world);

        boolean generateFloor = 0 <= chunkX && chunkX <= chunkSizeX && 0 <= chunkZ && chunkZ <= chunkSizeZ;
        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                biome.setBiome(x, z, Biome.SWAMPLAND);
                if (generateFloor)
                    chunkData.setBlock(x, DeadByCubeWorld.WORLD_Y, z, FLOOR_MATERIAL_DATA[random.nextInt(FLOOR_MATERIAL_DATA.length)]);
            }
        }

        return chunkData;
    }
}
