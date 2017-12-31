package deadbycube.game.interaction;

import deadbycube.player.DeadByCubePlayer;
import org.bukkit.Location;

import java.util.ArrayList;

public class PlayerInteractionManager {

    private final DeadByCubePlayer player;
    private final ArrayList<Interaction> interactions = new ArrayList<>();

    public PlayerInteractionManager(DeadByCubePlayer deadByCubePlayer) {
        this.player = deadByCubePlayer;
    }

    public void onInteract() {
        
    }

    public void update() {
        Location playerLocation = player.getPlayer().getLocation();
        interactions.removeIf(interaction -> !interaction.canInteract(player) || playerLocation.distance(interaction.getLocation()) > interaction.getDistance());
    }

    public void addInteraction(Interaction interaction) {
        this.interactions.add(interaction);
    }

}
