package deadbycube.player.killer.power.beartrap.interaction;

import deadbycube.interaction.Interaction;
import deadbycube.player.DeadByCubePlayer;
import deadbycube.player.killer.power.beartrap.PowerBearTrap;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class SetTrapInteraction extends Interaction {

    private final PowerBearTrap power;

    public SetTrapInteraction(PowerBearTrap power) {
        super("set_trap");

        this.power = power;
    }

    @Override
    public boolean canInteract(DeadByCubePlayer deadByCubePlayer) {
        return super.canInteract(deadByCubePlayer) && power.getTrapCount() > 0;
    }

    @Override
    protected void onInteract() {
        interactor.getWalkSpeed().forceValue(0);
    }

    @Override
    protected void onUpdate(int tick) {
        Player player = interactor.getPlayer();
        Location playerLocation = player.getLocation();

        float pitch = playerLocation.getPitch();
        if (pitch < 85) {
            playerLocation.setPitch(pitch + Math.max(8, Math.abs(70 - pitch) / 5));
            player.teleport(playerLocation);
        }

        double settingsTime = power.getSettingsTime().getValue();
        if (tick >= settingsTime)
            this.stopInteract();
    }

    @Override
    protected void onStopInteract(int tick) {
        interactor.getWalkSpeed().resetValue();

        Player player = interactor.getPlayer();
        Location playerLocation = player.getLocation();
        playerLocation.setPitch(10);
        player.teleport(playerLocation);

        double settingsTime = power.getSettingsTime().getValue();
        if (tick >= settingsTime)
            power.removeBearTrap();
    }

    @Override
    public boolean isInteracting() {
        return super.isInteracting() && interactor.getPlayer().isHandRaised();
    }
}
