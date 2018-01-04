package deadbycube.command.interaction;

import deadbycube.DeadByCube;
import deadbycube.command.Command;
import deadbycube.command.exception.CommandExecutionException;
import deadbycube.command.function.FunctionInfo;
import deadbycube.game.DeadByCubeGame;
import deadbycube.game.interaction.Interaction;
import deadbycube.game.interaction.InteractionManager;
import deadbycube.player.DeadByCubePlayer;
import deadbycube.util.GameStatus;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandInteraction extends Command {

    public CommandInteraction(DeadByCube plugin) {
        super(plugin, "interaction");
    }

    @FunctionInfo(name = "create")
    private void create(CommandSender commandSender, String name, Double distance) throws CommandExecutionException {
        this.checkIfInGame();

        Player player = getPlayer(commandSender);
        registerInteraction(new Interaction(name, player.getLocation(), distance) {
            @Override
            public boolean canInteract(DeadByCubePlayer deadByCubePlayer) {
                return false;
            }

            @Override
            protected void interact(DeadByCubePlayer deadByCubePlayer) {
            }
        });
    }

    private void registerInteraction(Interaction interaction) {
        DeadByCubeGame game = plugin.getGame();
        InteractionManager interactionManager = game.getInteractionManager();
        interactionManager.registerInteraction(interaction);
    }

    private void checkIfInGame() throws CommandExecutionException {
        if (plugin.getStatus() != GameStatus.IN_GAME)
            throw new CommandExecutionException("You need to be in game for managing interactions");
    }

}
