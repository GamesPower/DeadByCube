package deadbycube.player.killer.power.cartersspark.interaction;

import deadbycube.DeadByCube;
import deadbycube.audio.SoundRegistry;
import deadbycube.audio.WorldAudioManager;
import deadbycube.interaction.Interaction;
import deadbycube.interaction.InteractionActionBinding;
import deadbycube.player.killer.KillerPlayer;
import deadbycube.player.killer.power.cartersspark.PowerCartersSpark;
import deadbycube.util.Progression;
import org.bukkit.*;
import org.bukkit.boss.BarColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitScheduler;

public class ShockTherapyInteraction extends Interaction {

    private final PowerCartersSpark power;
    private final Progression progression;

    public ShockTherapyInteraction(PowerCartersSpark power) {
        super(InteractionActionBinding.USE, "shock_therapy");
        this.power = power;
        this.progression = new Progression("shock_therapy", BarColor.WHITE);
    }

    @Override
    public void onInteract() {
        this.progression.display(power.getKiller());

        KillerPlayer killer = power.getKiller();
        Player player = killer.getPlayer();
        WorldAudioManager audioManager = killer.getPlugin().getAudioManager();
        Location soundLocation = player.getLocation();

        audioManager.playSound(SoundRegistry.POWER_CARTERS_SPARK_CHARGE, SoundCategory.MASTER, soundLocation, 1, 1);
        audioManager.playSound(SoundRegistry.POWER_CARTERS_SPARK_CHARGE_BASS, SoundCategory.MASTER, soundLocation, 1, 1);
        audioManager.playSound(SoundRegistry.POWER_CARTERS_SPARK_CHARGE_HIGH, SoundCategory.MASTER, soundLocation, 1, 1);

    }

    @Override
    public void onUpdate(int chargeProgress) {
        double chargeTime = power.getChargeTime().getValue();

        if (chargeProgress % 2 == 0) {
            this.progression.setMaxValue(chargeTime);
            this.progression.setValue(chargeProgress);
        }

        KillerPlayer killer = power.getKiller();
        Player player = killer.getPlayer();
        World world = player.getWorld();

        Location particleLocation = player.getLocation().add(0, .8, 0);
        for (int i = 0; i < (chargeProgress / 2); i++)
            world.spawnParticle(Particle.SMOKE_NORMAL, particleLocation, 1, .2, .35, .2, 0);

        if (chargeProgress >= chargeTime)
            this.stopInteract();
    }

    @Override
    public void onStopInteract(int chargeProgress) {
        double chargeTime = power.getChargeTime().getValue();

        KillerPlayer killer = power.getKiller();
        DeadByCube plugin = killer.getPlugin();
        WorldAudioManager audioManager = plugin.getAudioManager();
        if (chargeProgress >= chargeTime) {
            Player player = killer.getPlayer();

            Location soundLocation = player.getLocation();
            audioManager.playSound(SoundRegistry.POWER_CARTERS_SPARK_ATTACK, SoundCategory.MASTER, soundLocation, 1, 1);
            audioManager.playSound(SoundRegistry.POWER_CARTERS_SPARK_ATTACK_BASS, SoundCategory.MASTER, soundLocation, 1, 1);
            audioManager.playSound(SoundRegistry.POWER_CARTERS_SPARK_EXPLOSION, SoundCategory.MASTER, soundLocation, 1, 1);

            Server server = plugin.getServer();
            BukkitScheduler scheduler = server.getScheduler();
            scheduler.runTaskLater(plugin, power::generateShock, 5);

        } else {
            audioManager.stopSound(SoundRegistry.POWER_CARTERS_SPARK_CHARGE);
            audioManager.stopSound(SoundRegistry.POWER_CARTERS_SPARK_CHARGE_BASS);
            audioManager.stopSound(SoundRegistry.POWER_CARTERS_SPARK_CHARGE_HIGH);
        }

        this.progression.setValue(0);
        this.progression.reset(deadByCubePlayer);
    }

    @Override
    public boolean isInteracting() {
        return power.getKiller().getPlayer().isHandRaised();
    }
}
