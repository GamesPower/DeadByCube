package deadbycube.command;

import deadbycube.command.exception.CommandExecutionException;
import deadbycube.command.function.FunctionInfo;
import deadbycube.interaction.InteractionActionBinding;
import deadbycube.player.PlayerList;
import deadbycube.interaction.InteractionManager;
import deadbycube.player.killer.KillerPlayer;
import deadbycube.player.survivor.SurvivorPlayer;
import deadbycube.world.object.generator.GeneratorObject;
import deadbycube.world.object.generator.interaction.BreakInteraction;
import deadbycube.world.object.generator.interaction.RepairInteraction;
import org.bukkit.block.BlockFace;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

class CommandGenerator extends Command {

    CommandGenerator(CommandManager commandManager) {
        super(commandManager, "generator");
    }

    @FunctionInfo(name = "spawn", requiredArgs = 1)
    private void spawn(CommandSender commandSender) throws CommandExecutionException {
        Player player = getPlayer(commandSender);

        GeneratorObject generatorObject = new GeneratorObject(player.getLocation(), BlockFace.EAST, BlockFace.NORTH);
        PlayerList playerList = plugin.getPlayerList();
        List<SurvivorPlayer> survivors = playerList.getSurvivors();
        List<KillerPlayer> killers = playerList.getKillers();

        generatorObject.init();
        survivors.forEach(survivorPlayer -> {
            InteractionManager interactionManager = survivorPlayer.getInteractionManager();
            for (RepairInteraction repairInteraction : generatorObject.getRepairInteractions())
                interactionManager.registerInteraction(InteractionActionBinding.ATTACK, repairInteraction);
        });
        killers.forEach(killerPlayer -> {
            InteractionManager interactionManager = killerPlayer.getInteractionManager();
            for (BreakInteraction breakInteraction : generatorObject.getBreakInteractions())
                interactionManager.registerInteraction(InteractionActionBinding.ATTACK, breakInteraction);
        });

    }

}
