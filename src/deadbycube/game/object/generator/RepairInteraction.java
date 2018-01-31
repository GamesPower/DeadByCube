package deadbycube.game.object.generator;

import deadbycube.DeadByCube;
import deadbycube.game.interaction.InteractionType;
import deadbycube.game.interaction.ProgressableInteraction;
import deadbycube.player.DeadByCubePlayer;

public class RepairInteraction extends ProgressableInteraction {

    public RepairInteraction(DeadByCube plugin, GeneratorObject generatorObject) {
        super(InteractionType.SNEAK, "repair", generatorObject.getLocation(), 1.2f, GeneratorObject.CHARGE);
    }

    @Override
    public void onUpdate() {
    }

    @Override
    protected void onPlayerStartInteract(DeadByCubePlayer deadByCubePlayer) {
    }

    @Override
    protected void onPlayerStopInteract(DeadByCubePlayer deadByCubePlayer) {
    }

    @Override
    public boolean canInteract(DeadByCubePlayer deadByCubePlayer) {
        return false;
    }

    @Override
    protected void onInteractionCompleted() {
    }

}
