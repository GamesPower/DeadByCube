package deadbycube.world.object.beartrap.interaction;

import deadbycube.interaction.WorldInteraction;
import deadbycube.player.DeadByCubePlayer;
import deadbycube.player.PlayerType;
import deadbycube.player.killer.KillerPlayer;
import deadbycube.player.killer.power.beartrap.PowerBearTrap;
import deadbycube.registry.PowerRegistry;
import deadbycube.world.object.beartrap.BearTrapObject;
import org.bukkit.Location;

public class PickUpInteraction extends WorldInteraction {

    private final BearTrapObject bearTrap;

    public PickUpInteraction(BearTrapObject bearTrap, Location location) {
        super("pick_up", location, 1, 80, PlayerType.KILLER);

        this.bearTrap = bearTrap;
    }

    @Override
    public boolean canInteract(DeadByCubePlayer deadByCubePlayer) {
        return super.canInteract(deadByCubePlayer) && PowerRegistry.BEAR_TRAP.hasPower(interactor);
    }

    @Override
    protected void onInteract() {
    }

    @Override
    protected void onUpdate(int tick) {

    }

    @Override
    protected void onStopInteract(int tick) {
        KillerPlayer killer = (KillerPlayer) interactor;
        PowerBearTrap power = (PowerBearTrap) killer.getPower();
        power.addBearTrap();
    }

}
