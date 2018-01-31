package deadbycube.command.structure;

import deadbycube.command.Command;
import deadbycube.command.CommandManager;
import deadbycube.command.exception.CommandExecutionException;
import deadbycube.command.function.FunctionInfo;
import deadbycube.structure.StructureManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;

public class CommandStructure extends Command {

    public CommandStructure(CommandManager commandManager) {
        super(commandManager, "structure");
    }

    @FunctionInfo(name = "load", requiredArgs = 2)
    private void load(CommandSender commandSender, String structureName, Double angle) throws IOException, CommandExecutionException {
        Player player = getPlayer(commandSender);
        plugin.getStructureManager().load(structureName).spawn(player.getLocation(), Math.toRadians(angle));
    }

    @FunctionInfo(name = "create", requiredArgs = 4)
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
