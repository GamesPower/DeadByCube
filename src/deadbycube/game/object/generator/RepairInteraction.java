package deadbycube.game.object.generator;

import deadbycube.game.interaction.Interaction;
import deadbycube.player.DeadByCubePlayer;
import deadbycube.player.PlayerType;
import org.bukkit.Location;

public class RepairInteraction extends Interaction {

    public RepairInteraction(Location location, GeneratorObject generatorObject) {
        super("repair", location, 1.2f);
    }

    @Override
    public boolean canInteract(DeadByCubePlayer deadByCubePlayer) {
        return deadByCubePlayer.getType().equals(PlayerType.SURVIVOR);
    }

    @Override
    protected void interact(DeadByCubePlayer deadByCubePlayer) {

    }

}
