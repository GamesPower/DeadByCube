package deadbycube.player.killer.power.cartersspark.interaction;

import deadbycube.DeadByCube;
import deadbycube.audio.WorldAudioManager;
import deadbycube.interaction.Interaction;
import deadbycube.player.DeadByCubePlayer;
import deadbycube.player.killer.KillerPlayer;
import deadbycube.player.killer.power.cartersspark.PowerCartersSpark;
import deadbycube.registry.SoundRegistry;
import deadbycube.util.MagicalValue;
import deadbycube.util.ProgressBar;
import deadbycube.util.TickLoop;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.SoundCategory;
import org.bukkit.World;
import org.bukkit.boss.BarColor;
import org.bukkit.entity.Player;

public class ShockTherapyInteraction extends Interaction {

    private final PowerCartersSpark power;
    private final ProgressBar progressBar;

    private boolean canInteract = true;

    public ShockTherapyInteraction(PowerCartersSpark power) {
        super("shock_therapy");
        this.power = power;
        this.progressBar = new ProgressBar("shock_therapy", BarColor.WHITE);
    }

    @Override
    public boolean canInteract(DeadByCubePlayer deadByCubePlayer) {
        return super.canInteract(deadByCubePlayer) && canInteract;
    }

    @Override
    protected void onInteract() {
        this.progressBar.display(interactor.getPlayer());

        KillerPlayer killer = power.getKiller();
        Player player = killer.getPlayer();
        WorldAudioManager audioManager = killer.getPlugin().getAudioManager();
        Location soundLocation = player.getLocation();

        audioManager.playSound(SoundRegistry.POWER_CARTERS_SPARK_CHARGE, SoundCategory.MASTER, soundLocation);
        audioManager.playSound(SoundRegistry.POWER_CARTERS_SPARK_CHARGE_BASS, SoundCategory.MASTER, soundLocation);
        audioManager.playSound(SoundRegistry.POWER_CARTERS_SPARK_CHARGE_HIGH, SoundCategory.MASTER, soundLocation);
    }

    @Override
    protected void onUpdate(int chargeProgress) {
        MagicalValue chargeTime = power.getChargeTime();

        if (chargeProgress % 2 == 0) {
            this.progressBar.setColorFromValue(chargeTime);
            this.progressBar.setMaxValue(chargeTime.getValue());
            this.progressBar.setValue(chargeProgress);
        }

        KillerPlayer killer = power.getKiller();
        Player player = killer.getPlayer();
        World world = player.getWorld();

        Location particleLocation = player.getLocation().add(0, .8, 0);
        for (int i = 0; i < (chargeProgress / 2); i++)
            world.spawnParticle(Particle.SMOKE_NORMAL, particleLocation, 1, .2, .35, .2, 0);

        if (chargeProgress >= chargeTime.getValue())
            this.stopInteract();
    }

    @Override
    protected void onStopInteract(int chargeProgress) {
        double chargeTime = power.getChargeTime().getValue();

        KillerPlayer killer = power.getKiller();
        DeadByCube plugin = killer.getPlugin();
        WorldAudioManager audioManager = plugin.getAudioManager();
        if (chargeProgress >= chargeTime) {
            Player player = killer.getPlayer();

            Location soundLocation = player.getLocation();
            audioManager.playSound(SoundRegistry.POWER_CARTERS_SPARK_ATTACK, SoundCategory.MASTER, soundLocation);
            audioManager.playSound(SoundRegistry.POWER_CARTERS_SPARK_ATTACK_BASS, SoundCategory.MASTER, soundLocation);
            audioManager.playSound(SoundRegistry.POWER_CARTERS_SPARK_EXPLOSION, SoundCategory.MASTER, soundLocation);

            TickLoop.runLater(5, power::generateShock);
            TickLoop.runLater(30, () -> canInteract = true);

            this.canInteract = false;
        } else {
            audioManager.stopSound(SoundRegistry.POWER_CARTERS_SPARK_CHARGE);
            audioManager.stopSound(SoundRegistry.POWER_CARTERS_SPARK_CHARGE_BASS);
            audioManager.stopSound(SoundRegistry.POWER_CARTERS_SPARK_CHARGE_HIGH);
        }

        this.progressBar.setValue(0);
        this.progressBar.reset(interactor.getPlayer());
    }

    @Override
    public boolean isInteracting() {
        return super.isInteracting() && power.getKiller().getPlayer().isHandRaised();
    }
}
