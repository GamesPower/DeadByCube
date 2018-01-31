package deadbycube.game.interaction;

import deadbycube.game.DeadByCubeGame;
import deadbycube.player.DeadByCubePlayer;

import java.util.ArrayList;
import java.util.List;

public class InteractionManager {

    private final DeadByCubeGame game;
    private final ArrayList<Interaction> interactions = new ArrayList<>();

    public InteractionManager(DeadByCubeGame game) {
        this.game = game;
    }

    public void init(DeadByCubePlayer deadByCubePlayer) {
        PlayerInteractionManager playerInteractionManager = deadByCubePlayer.getInteractionManager();
        for (Interaction interaction : interactions) {
            if (interaction.canInteract(deadByCubePlayer))
                playerInteractionManager.registerInteraction(interaction);
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

    public void reset() {
        interactions.forEach(Interaction::reset);
    }

}
