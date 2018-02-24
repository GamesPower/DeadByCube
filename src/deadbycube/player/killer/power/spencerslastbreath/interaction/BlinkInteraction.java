package deadbycube.player.killer.power.spencerslastbreath.interaction;

import deadbycube.audio.AudioManager;
import deadbycube.interaction.Interaction;
import deadbycube.player.killer.power.spencerslastbreath.PowerSpencersLastBreath;
import deadbycube.registry.SoundRegistry;
import org.bukkit.*;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.material.MaterialData;
import org.bukkit.util.Vector;

public class BlinkInteraction extends Interaction {

    private final PowerSpencersLastBreath power;

    private Location originalLocation;
    private Location destinationLocation;
    private ArmorStand armorStand;
    private double blinkDistance;
    private Vector direction;

    public BlinkInteraction(PowerSpencersLastBreath power) {
        super("blink");

        this.power = power;
    }

    @Override
    protected void onInteract() {
        Player player = interactor.getPlayer();
        World world = interactor.getPlugin().getHandler().getWorld();

        Location playerLocation = player.getLocation();
        this.originalLocation = playerLocation;
        this.blinkDistance = destinationLocation.distance(playerLocation);
        this.direction = destinationLocation.subtract(playerLocation.clone()).toVector().normalize();

        this.armorStand = (ArmorStand) world.spawnEntity(playerLocation.setDirection(direction), EntityType.ARMOR_STAND);
        this.armorStand.setVisible(false);
        this.armorStand.setInvulnerable(true);
        this.armorStand.setGravity(false);


        player.setGameMode(GameMode.SPECTATOR);
        player.setSpectatorTarget(armorStand);

        AudioManager audioManager = interactor.getPlugin().getAudioManager();
        audioManager.playSound(SoundRegistry.POWER_SPENCERS_LAST_BREATH_DISAPPEAR, SoundCategory.MASTER, player.getLocation(), .5f);
        audioManager.playSound(SoundRegistry.POWER_SPENCERS_LAST_BREATH_DISAPPEAR_SMOKE, SoundCategory.MASTER, player.getLocation(), .5f);
    }

    @Override
    protected void onUpdate(int tick) {
        Location location = armorStand.getLocation();

        if (originalLocation.distance(location) >= blinkDistance) {
            this.stopInteract();
            return;
        }

        if (tick % 4 == 0) {
            location.getWorld().spawnParticle(
                    Particle.FALLING_DUST,
                    armorStand.getLocation().add(0, .4, 0), 5,
                    .3, .1, .3,
                    1, new MaterialData(Material.COAL_BLOCK)
            );
        }

        armorStand.teleport(location.add(direction.clone().multiply(power.getBlinkMovementSpeed().getValue())));
    }

    @Override
    protected void onStopInteract(int tick) {
        Player player = interactor.getPlayer();

        AudioManager audioManager = interactor.getPlugin().getAudioManager();
        audioManager.playSound(SoundRegistry.POWER_SPENCERS_LAST_BREATH_APPEAR, SoundCategory.MASTER, player.getLocation(), 15);
        audioManager.playSound(SoundRegistry.KILLER_NURSE_TELEPORT_APPEAR, SoundCategory.MASTER, player.getLocation(), 15);

        player.setSpectatorTarget(null);
        player.setGameMode(GameMode.ADVENTURE);

        this.armorStand.remove();
        this.power.createStunTask();
    }

    public void setDestinationLocation(Location destinationLocation) {
        this.destinationLocation = destinationLocation;
    }
}
