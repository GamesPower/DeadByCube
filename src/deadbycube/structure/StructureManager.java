package deadbycube.structure;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.Plugin;

import java.io.*;
import java.util.logging.Level;

public class StructureManager {

    private final File structuresDirectory;

    public StructureManager(Plugin plugin) {
        this.structuresDirectory = new File(plugin.getDataFolder(), "structures");
        if (!structuresDirectory.exists() && !structuresDirectory.mkdirs()) {
            plugin.getLogger().log(Level.SEVERE, "Unable to create the structures directory");
            plugin.getServer().getPluginManager().disablePlugin(plugin);
        }
    }

    public Structure load(String name) throws IOException {
        File structureFile = new File(structuresDirectory, name + ".structure");
        if (!structureFile.exists())
            throw new IOException("Structure file not found");

        DataInputStream in = new DataInputStream(new FileInputStream(structureFile));


        byte sizeX = in.readByte();
        byte sizeY = in.readByte();
        int size = in.readInt();
        MaterialData[] materialData = new MaterialData[size];
        for (int i = 0; i < size; i++) {
            materialData[i] = new MaterialData(
                    Material.getMaterial(in.readUTF()),
                    in.readByte()
            );
        }

        in.close();

        return new Structure(name, materialData, sizeX, sizeY);
    }

    public void save(Structure structure) throws IOException {
        File structureFile = new File(structuresDirectory, structure.getName() + ".structure");
        if (structureFile.exists())
            throw new IOException("Structure file already exists");

        DataOutputStream out = new DataOutputStream(new FileOutputStream(structureFile));

        MaterialData[] materialData = structure.getMaterialData();
        out.writeByte(structure.getSizeX());
        out.writeByte(structure.getSizeY());
        out.writeInt(materialData.length);
        for (MaterialData data : materialData) {
            out.writeUTF(data.getItemType().name());
            out.writeByte(data.getData());
        }
        out.close();
    }

    public Structure create(String name, Location location, byte sizeX, byte sizeY, byte sizeZ) {
        int offsetX = location.getBlockX();
        int offsetY = location.getBlockY();
        int offsetZ = location.getBlockZ();

        MaterialData[] materialData = new MaterialData[sizeX * sizeY * sizeZ];
        System.out.println("materialData: " + materialData.length);
        World world = location.getWorld();
        for (byte x = 0; x < sizeX; x++) {
            for (byte y = 0; y < sizeY; y++) {
                for (byte z = 0; z < sizeZ; z++) {
                    Block block = world.getBlockAt(offsetX + x, offsetY + y, offsetZ + z);
                    int index = (z * sizeX * sizeY) + (y * sizeX) + x;
                    System.out.println("Block at " + x + " " + y + " " + z + " = index " + index);
                    materialData[index] = new MaterialData(block.getType(), block.getData());
                }
            }
        }

        return new Structure(name, materialData, sizeX, sizeY);
    }

}
