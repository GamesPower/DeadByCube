package deadbycube.game.interaction;

import deadbycube.player.DeadByCubePlayer;

import java.util.ArrayList;

public class PlayerInteractionManager {

    private final DeadByCubePlayer deadByCubePlayer;
    private final ArrayList<Interaction> interactions = new ArrayList<>();

    public PlayerInteractionManager(DeadByCubePlayer deadByCubePlayer) {
        this.deadByCubePlayer = deadByCubePlayer;
    }

    public void dispatch(InteractionType type) {
        /*for (Interaction interaction : interactions) {
            Player player = deadByCubePlayer.getPlayer();
            Location playerLocation = player.getLocation();

            if (interaction.getType() == type && interaction.canInteract(deadByCubePlayer) && playerLocation.distance(interaction.getLocation()) >= interaction.getDistance()) {
                interaction.startInteract(deadByCubePlayer);
                break;
            }
        }*/
    }

    public void update() {
        //  TODO
    }

    public void registerInteraction(Interaction interaction) {
        this.interactions.add(interaction);
    }

}
