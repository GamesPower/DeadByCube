package deadbycube.player.killer.power.huntinghatchets.interaction;

import deadbycube.DeadByCube;
import deadbycube.audio.WorldAudioManager;
import deadbycube.interaction.Interaction;
import deadbycube.player.killer.power.huntinghatchets.HatchetProjectile;
import deadbycube.player.killer.power.huntinghatchets.PowerHuntingHatchets;
import deadbycube.registry.SoundRegistry;
import org.bukkit.Location;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class ThrowHatchetInteraction extends Interaction {

    private final PowerHuntingHatchets power;

    private boolean thrown = false;

    public ThrowHatchetInteraction(PowerHuntingHatchets power) {
        super("throw_hatchet");

        this.power = power;
    }

    @Override
    protected void onInteract() {
        this.thrown = false;

        Player player = interactor.getPlayer();
        Location location = player.getLocation();

        DeadByCube plugin = interactor.getPlugin();
        WorldAudioManager audioManager = plugin.getAudioManager();
        audioManager.playSound(SoundRegistry.KILLER_HUNTRESS_CHARGE, SoundCategory.MASTER, location);
    }

    @Override
    protected void onUpdate(int chargeProgress) {
        Player player = interactor.getPlayer();
        Location location = player.getLocation();
        DeadByCube plugin = interactor.getPlugin();
        WorldAudioManager audioManager = plugin.getAudioManager();

        double requiredChargeTime = power.getRequiredChargeTime().getValue();
        if (thrown && chargeProgress >= requiredChargeTime) {
            this.stopInteract();

        } else if (!thrown && !interactor.getPlayer().isHandRaised()) {
            this.thrown = true;

        } else if (interactor.isSneaking()) {
            this.thrown = false;
            this.stopInteract();

            audioManager.playSound(SoundRegistry.POWER_HUNTING_HATCHETS_THROW_CANCEL, SoundCategory.MASTER, location);

        } else if (chargeProgress == (int) power.getChargeTime().getValue()) {
            audioManager.playSound(SoundRegistry.POWER_HUNTING_HATCHETS_CUE, SoundCategory.MASTER, location);

        }
    }

    @Override
    protected void onStopInteract(int chargeProgress) {
        if (thrown) {
            double chargeTime = power.getChargeTime().getValue();
            double requiredChargeTime = power.getRequiredChargeTime().getValue();
            if (chargeProgress > chargeTime)
                chargeProgress = (int) chargeTime;

            Player player = interactor.getPlayer();
            Location location = player.getLocation();

            DeadByCube plugin = interactor.getPlugin();
            WorldAudioManager audioManager = plugin.getAudioManager();
            audioManager.playSound(SoundRegistry.POWER_HUNTING_HATCHETS_THROW, SoundCategory.MASTER, location);
            audioManager.playSound(SoundRegistry.KILLER_HUNTRESS_THROW, SoundCategory.MASTER, location);

            Location eyeLocation = player.getEyeLocation();
            Vector direction = eyeLocation.getDirection();

            double speedModifier = (chargeProgress - requiredChargeTime) / (chargeTime - requiredChargeTime);
            double speed = 1.5 + speedModifier;

            new HatchetProjectile(location.add(0, HatchetProjectile.HATCHET_OFFSET, 0), eyeLocation.getYaw(), direction.multiply(speed));

            power.useHatchet();
        }
    }

}
