package deadbycube.player.killer.interaction.stun;

import deadbycube.interaction.Interaction;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;

public class StunRecoverInteraction extends Interaction {

    private static final double RECOVER_PITCH = 5;
    private static final float RECOVER_STEP = 8;

    private final ArmorStand armorStand;

    StunRecoverInteraction(ArmorStand armorStand) {
        super("stun_recover");

        this.armorStand = armorStand;
    }

    @Override
    protected void onInteract() {
    }

    @Override
    protected void onUpdate(int tick) {
        Location armorStandLocation = armorStand.getLocation();
        float pitch = armorStandLocation.getPitch();
        if (pitch > RECOVER_PITCH) {
            armorStandLocation.setPitch(pitch - RECOVER_STEP);
            armorStand.teleport(armorStandLocation);
        } else
            this.stopInteract();
    }

    @Override
    protected void onStopInteract(int tick) {
        Player player = interactor.getPlayer();
        player.setSpectatorTarget(null);
        player.setGameMode(GameMode.ADVENTURE);

        this.armorStand.remove();
    }

}
