package deadbycube.command.structure;

import deadbycube.DeadByCube;
import deadbycube.command.Command;
import deadbycube.command.exception.CommandExecutionException;
import deadbycube.command.function.FunctionInfo;
import deadbycube.structure.StructureManager;
import deadbycube.util.MathUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;

public class CommandStructure extends Command {

    public CommandStructure(DeadByCube plugin) {
        super(plugin, "structure");
    }

    @FunctionInfo(name = "load")
    private void load(CommandSender commandSender, String structureName) throws IOException, CommandExecutionException {
        Player player = getPlayer(commandSender);
        plugin.getStructureManager().load(structureName).spawn(player.getLocation(), MathUtils.Rotation.NONE);
    }

    @FunctionInfo(name = "create")
    private void create(CommandSender commandSender, String structureName, Byte sizeX, Byte sizeY, Byte sizeZ) throws IOException, CommandExecutionException {
        Player player = getPlayer(commandSender);
        StructureManager structureManager = plugin.getStructureManager();
        structureManager.save(
                structureManager.create(
                        structureName,
                        player.getLocation(),
                        sizeX, sizeY, sizeZ
                )
        );

    }

}
