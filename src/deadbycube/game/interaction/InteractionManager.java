package deadbycube.game.interaction;

import deadbycube.game.DeadByCubeGame;
import deadbycube.player.DeadByCubePlayer;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class InteractionManager {

    private final DeadByCubeGame game;
    private final ArrayList<Interaction> interactions = new ArrayList<>();

    public InteractionManager(DeadByCubeGame game) {
        this.game = game;
    }

    public void update(DeadByCubePlayer deadByCubePlayer) {
        PlayerInteractionManager interactionManager = deadByCubePlayer.getInteractionManager();
        interactionManager.clearInteractions();

        Player player = deadByCubePlayer.getPlayer();
        Location playerLocation = player.getLocation();
        for (Interaction interaction : interactions) {
            if (interaction.canInteract(deadByCubePlayer) && playerLocation.distance(interaction.getLocation()) <= interaction.getDistance()) {
                interactionManager.registerInteraction(interaction);
            }
        }
    }

    public void registerInteraction(Interaction interaction) {
        this.interactions.add(interaction);
    }

    public void unregisterInteraction(Interaction interaction) {
        this.interactions.remove(interaction);
    }

    public List<Interaction> getInteractions() {
        return interactions;
    }

}
