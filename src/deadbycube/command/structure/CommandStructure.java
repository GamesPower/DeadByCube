package deadbycube.command.structure;

import deadbycube.DeadByCube;
import deadbycube.command.Command;
import deadbycube.command.function.FunctionInfo;
import deadbycube.command.value.CommandIntValue;
import deadbycube.command.value.CommandStringValue;
import deadbycube.structure.StructureManager;
import deadbycube.util.MathUtils;
import org.bukkit.entity.Player;

import java.io.IOException;

public class CommandStructure extends Command {

    public CommandStructure(DeadByCube plugin) {
        super(plugin, "structure");
    }

    @FunctionInfo(name = "load")
    private void load(Player player, CommandStringValue structureName) throws IOException {
        plugin.getStructureManager().load(structureName.getValue()).spawn(player.getLocation(), MathUtils.Rotation.NONE);
    }

    @FunctionInfo(name = "create")
    private void create(Player player, CommandStringValue structureName, CommandIntValue sizeX, CommandIntValue sizeY, CommandIntValue sizeZ) throws IOException {
        StructureManager structureManager = plugin.getStructureManager();
        structureManager.save(
                structureManager.create(
                        structureName.getValue(),
                        player.getLocation(),
                        sizeX.getValue().byteValue(),
                        sizeY.getValue().byteValue(),
                        sizeZ.getValue().byteValue()
                )
        );

    }

}
