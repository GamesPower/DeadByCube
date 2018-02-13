package deadbycube.interaction;

import deadbycube.player.DeadByCubePlayer;

import java.util.ArrayList;
import java.util.List;

public class InteractionRegistry {

    private final List<Interaction> interactionList = new ArrayList<>();

    public Interaction getMainInteraction(DeadByCubePlayer deadByCubePlayer) {
        // TODO Implement priority (with distance or angle)
        for (Interaction interaction : interactionList) {
            if (interaction.canInteract(deadByCubePlayer))
                return interaction;
        }
        return null;
    }

    public boolean isRegistered(Interaction interaction) {
        return interactionList.contains(interaction);
    }

    public void register(Interaction interaction) {
        this.interactionList.add(interaction);
    }

    public void unregister(Interaction interaction) {
        this.interactionList.remove(interaction);
    }

}
