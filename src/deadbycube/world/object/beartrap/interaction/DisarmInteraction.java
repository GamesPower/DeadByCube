package deadbycube.world.object.beartrap.interaction;

import deadbycube.interaction.WorldInteraction;
import deadbycube.player.PlayerType;
import org.bukkit.Location;

public class DisarmInteraction extends WorldInteraction {

    public DisarmInteraction(Location location) {
        super("disarm", location, 1, 60, PlayerType.SURVIVOR);
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
