package deadbycube.player.killer.interaction.stun;

import deadbycube.DeadByCube;
import deadbycube.audio.WorldAudioManager;
import deadbycube.interaction.Interaction;
import deadbycube.interaction.InteractionManager;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.SoundCategory;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class StunInteraction extends Interaction {

    private static final float STUN_PITCH = 85;

    private int duration;
    private ArmorStand armorStand;
    private float orginalYaw;

    public StunInteraction() {
        super("stun");
    }

    @Override
    protected void onInteract() {
        Player player = interactor.getPlayer();
        Location playerLocation = player.getLocation();
        World world = player.getWorld();

        this.armorStand = (ArmorStand) world.spawnEntity(playerLocation, EntityType.ARMOR_STAND);
        this.armorStand.setVisible(false);
        this.armorStand.setInvulnerable(true);
        this.armorStand.setGravity(false);

        this.orginalYaw = playerLocation.getYaw();

        player.setGameMode(GameMode.SPECTATOR);
        player.setSpectatorTarget(armorStand);

        DeadByCube plugin = interactor.getPlugin();
        WorldAudioManager audioManager = plugin.getAudioManager();
        audioManager.playSound("killer." + interactor.getName() + ".stun", SoundCategory.VOICE, interactor.getPlayer().getLocation(), 1, 1);
    }

    @Override
    protected void onUpdate(int tick) {
        Location armorStandLocation = armorStand.getLocation();
        float pitch = armorStandLocation.getPitch();
        if (pitch < STUN_PITCH)
            armorStandLocation.setPitch(tick * 25);
        else if (tick % 5 == 0) {
            armorStandLocation.setPitch(STUN_PITCH + ((tick % 10 == 0) ? 0 : 2));
            armorStandLocation.setYaw(orginalYaw + (tick % 10 - 5));
        }

        armorStand.teleport(armorStandLocation);

        if (tick >= (duration * .40))
            this.stopInteract();
    }

    @Override
    protected void onStopInteract(int tick) {
        InteractionManager interactionManager = interactor.getInteractionManager();
        interactionManager.interact(new StunRecoverInteraction(armorStand));
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

}
