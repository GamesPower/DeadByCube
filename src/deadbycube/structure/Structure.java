package deadbycube.structure;

import deadbycube.util.MathUtils;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.material.MaterialData;
import org.bukkit.util.Vector;

import java.util.Arrays;

public class Structure {

    private final String name;
    private final MaterialData[] materialData;
    private final byte sizeX;
    private final byte sizeY;

    Structure(String name, MaterialData[] materialData, byte sizeX, byte sizeY) {
        this.name = name;
        this.materialData = materialData;
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }

    public void spawn(Location location, MathUtils.Rotation rotation) {
        int offsetX = location.getBlockX();
        int offsetY = location.getBlockY();
        int offsetZ = location.getBlockZ();

        World world = location.getWorld();
        for (int i = 0; i < materialData.length; i++) {
            final int z = i / (sizeX * sizeY);
            final int y = (i - (z * sizeX * sizeY)) / sizeX;
            final int x = (i - (z * sizeX * sizeY)) % sizeX;
            Vector vector = MathUtils.rotate(rotation, new Vector(x, y, z));
            Block block = world.getBlockAt(
                    offsetX + vector.getBlockX(),
                    offsetY + vector.getBlockY(),
                    offsetZ + vector.getBlockZ()
            );
            MaterialData data = this.materialData[i];
            if (!data.getItemType().equals(Material.STRUCTURE_VOID)) {
                System.out.println("Block at " + x + " " + y + " " + z + " is now " + data.toString());
                block.setType(data.getItemType());
                block.setData(data.getData());
            } else {
                System.out.println("Skip block at " + x + " " + y + " " + z);
            }
        }
    }

    public MaterialData getBlock(byte x, byte y, byte z) {
        int index = (z * sizeX * sizeY) + (y * sizeX) + x;
        if (0 <= index && index < materialData.length)
            return materialData[index];
        else
            throw new IllegalArgumentException("Invalid position");
    }

    public void setBlock(byte x, byte y, byte z, MaterialData materialData) {
        int index = (z * sizeX * sizeY) + (y * sizeX) + x;
        if (0 <= index && index < this.materialData.length)
            this.materialData[index] = materialData;
        else
            throw new IllegalArgumentException("Invalid position");
    }

    public void setBlock(byte x, byte y, byte z, Material material, byte data) {
        int index = (z * sizeX * sizeY) + (y * sizeX) + x;
        if (0 <= index && index < materialData.length)
            materialData[index] = new MaterialData(material, data);
        else
            throw new IllegalArgumentException("Invalid position");
    }

    public String getName() {
        return name;
    }

    MaterialData[] getMaterialData() {
        return materialData;
    }

    byte getSizeX() {
        return sizeX;
    }

    byte getSizeY() {
        return sizeY;
    }

    @Override
    public String toString() {
        return "Structure{" +
                "name='" + name + '\'' +
                ", materialData=" + Arrays.toString(materialData) +
                '}';
    }

}
