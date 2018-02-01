package deadbycube.command;

import deadbycube.command.exception.CommandExecutionException;
import deadbycube.command.function.FunctionInfo;
import deadbycube.interaction.Interaction;
import deadbycube.interaction.InteractionActionBinding;
import deadbycube.interaction.PlayerInteractionManager;
import deadbycube.interaction.WorldInteraction;
import deadbycube.player.DeadByCubePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

class CommandInteraction extends Command {

    CommandInteraction(CommandManager commandManager) {
        super(commandManager, "interaction");
    }

    @FunctionInfo(name = "create", requiredArgs = 1)
    private void create(CommandSender commandSender, InteractionActionBinding actionBinding, String name) throws CommandExecutionException {
        Player player = getPlayer(commandSender);
        DeadByCubePlayer deadByCubePlayer = plugin.getPlayerList().getPlayer(player);
        PlayerInteractionManager interactionManager = deadByCubePlayer.getInteractionManager();
        interactionManager.registerInteraction(new Interaction(actionBinding, name));
    }

    @FunctionInfo(name = "create_at", requiredArgs = 1)
    private void createAt(CommandSender commandSender, InteractionActionBinding actionBinding, String name, Double distance, Double angle) throws CommandExecutionException {
        Player player = getPlayer(commandSender);
        DeadByCubePlayer deadByCubePlayer = plugin.getPlayerList().getPlayer(player);
        PlayerInteractionManager interactionManager = deadByCubePlayer.getInteractionManager();
        interactionManager.registerInteraction(new WorldInteraction(actionBinding, name, player.getLocation(), distance, angle));
    }

}
