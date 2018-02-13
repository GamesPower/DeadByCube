package deadbycube.world.object.generator.interaction;

import deadbycube.interaction.WorldInteraction;
import deadbycube.player.DeadByCubePlayer;
import deadbycube.player.PlayerType;
import deadbycube.world.object.generator.GeneratorObject;
import org.bukkit.Location;

public class BreakInteraction extends WorldInteraction {

    private final GeneratorObject generatorObject;

    public BreakInteraction(GeneratorObject generatorObject, Location location, double distance, double angle) {
        super("break", location, distance, angle, PlayerType.KILLER);
        this.generatorObject = generatorObject;
    }

    @Override
    public boolean canInteract(DeadByCubePlayer deadByCubePlayer) {
        return deadByCubePlayer.getType() == PlayerType.KILLER && super.canInteract(deadByCubePlayer);
    }

    @Override
    protected void onInteract() {

    }

    @Override
    protected void onUpdate(int tick) {

    }

    @Override
    protected void onStopInteract(int tick) {

    }

}
