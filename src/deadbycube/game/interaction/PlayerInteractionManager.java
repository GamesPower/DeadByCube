package deadbycube.game.interaction;

import deadbycube.player.DeadByCubePlayer;

import java.util.ArrayList;

public class PlayerInteractionManager {

    private final DeadByCubePlayer player;
    private final ArrayList<Interaction> interactions = new ArrayList<>();

    public PlayerInteractionManager(DeadByCubePlayer deadByCubePlayer) {
        this.player = deadByCubePlayer;
    }

    public void update() {



    }

    public void registerInteraction(Interaction interaction) {
        this.interactions.add(interaction);
    }

    public void clearInteractions() {
        this.interactions.clear();
    }

}
