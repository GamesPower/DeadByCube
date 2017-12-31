package deadbycube.game.object.generator;

import deadbycube.player.DeadByCubePlayer;
import deadbycube.player.PlayerType;
import deadbycube.game.interaction.Interaction;

public class RepairInteraction extends Interaction {

    public RepairInteraction(GeneratorObject generatorObject) {
        super("repair", generatorObject.getLocation(), 1.2f);
    }

    @Override
    public boolean canInteract(DeadByCubePlayer deadByCubePlayer) {
        return deadByCubePlayer.getType().equals(PlayerType.SURVIVOR);
    }

    @Override
    protected void interact(DeadByCubePlayer deadByCubePlayer) {

    }

}
