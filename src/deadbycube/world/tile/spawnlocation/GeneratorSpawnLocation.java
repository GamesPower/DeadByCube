package deadbycube.world.tile.spawnlocation;

import org.bukkit.block.BlockFace;

public class GeneratorSpawnLocation extends SpawnLocation {

    private final BlockFace[] faces;

    public GeneratorSpawnLocation(byte x, byte y, byte z, BlockFace... faces) {
        super(x, y, z);

        this.faces = faces;
    }

    public BlockFace[] getFaces() {
        return faces;
    }

}
