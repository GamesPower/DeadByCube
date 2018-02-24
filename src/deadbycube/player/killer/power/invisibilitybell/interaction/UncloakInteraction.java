package deadbycube.player.killer.power.invisibilitybell.interaction;

import deadbycube.audio.WorldAudioManager;
import deadbycube.interaction.Interaction;
import deadbycube.player.killer.KillerPlayer;
import deadbycube.player.killer.power.invisibilitybell.CloakStatus;
import deadbycube.player.killer.power.invisibilitybell.PowerInvisibilityBell;
import deadbycube.registry.SoundRegistry;
import deadbycube.util.MagicalValue;
import deadbycube.util.ProgressBar;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.SoundCategory;
import org.bukkit.World;
import org.bukkit.boss.BarColor;
import org.bukkit.entity.Player;

public class UncloakInteraction extends Interaction {

    private final PowerInvisibilityBell power;
    private final ProgressBar uncloakProgressBar;

    public UncloakInteraction(PowerInvisibilityBell power) {
        super("uncloak");
        this.power = power;
        this.uncloakProgressBar = new ProgressBar("uncloak", BarColor.WHITE);
    }

    @Override
    protected void onInteract() {
        KillerPlayer killer = power.getKiller();
        Player killerPlayer = killer.getPlayer();
        WorldAudioManager audioManager = killer.getPlugin().getAudioManager();
        audioManager.playSound(SoundRegistry.POWER_INVISIBILITY_BELL_BURN, SoundCategory.MASTER, killerPlayer.getLocation());

        this.uncloakProgressBar.display(interactor.getPlayer());
    }

    @Override
    protected void onUpdate(int uncloakProgress) {
        MagicalValue uncloakTime = power.getUncloakTime();

        if (uncloakProgress % 2 == 0) {
            this.uncloakProgressBar.setColorFromValue(uncloakTime);
            this.uncloakProgressBar.setMaxValue(uncloakTime.getValue());
            this.uncloakProgressBar.setValue(uncloakProgress);
        }

        KillerPlayer killer = power.getKiller();
        Player killerPlayer = killer.getPlayer();
        World world = killerPlayer.getWorld();
        WorldAudioManager audioManager = killer.getPlugin().getAudioManager();
        Location particleLocation = killerPlayer.getLocation().add(0, .5, 0);

        if ((uncloakProgress % 2) == 0)
            world.spawnParticle(Particle.SMOKE_NORMAL, particleLocation, 20, .1, .5, .1, 0.015);

        if (uncloakProgress == (int) (uncloakTime.getValue() * .08))
            audioManager.playSound(SoundRegistry.KILLER_WRAITH_WEAPON_ARM, SoundCategory.MASTER, killer.getPlayer().getLocation());

        if (uncloakProgress == (int) (uncloakTime.getValue() * .55)) {
            audioManager.playSound(SoundRegistry.POWER_INVISIBILITY_BELL_BELL_SINGLE, SoundCategory.MASTER, killerPlayer.getLocation());
            audioManager.playSound(SoundRegistry.POWER_INVISIBILITY_BELL_BELL_IRON, SoundCategory.MASTER, killerPlayer.getLocation());
        }

        if (uncloakProgress == (int) (uncloakTime.getValue() * .90)) {
            audioManager.playSound(SoundRegistry.POWER_INVISIBILITY_BELL_BELL_SINGLE, SoundCategory.MASTER, killerPlayer.getLocation());
            audioManager.playSound(SoundRegistry.POWER_INVISIBILITY_BELL_BELL_IRON, SoundCategory.MASTER, killerPlayer.getLocation());
        }

        if (uncloakProgress >= uncloakTime.getValue())
            this.stopInteract();
    }

    @Override
    protected void onStopInteract(int cloakProgress) {
        double uncloakTime = power.getUncloakTime().getValue();

        if (cloakProgress >= uncloakTime)
            power.setStatus(CloakStatus.UNCLOAKED);

        this.uncloakProgressBar.setValue(0);
        this.uncloakProgressBar.reset(interactor.getPlayer());
    }

    @Override
    public boolean isInteracting() {
        return super.isInteracting() && interactor.getPlayer().isHandRaised();
    }

}
