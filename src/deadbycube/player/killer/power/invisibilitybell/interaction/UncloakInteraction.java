package deadbycube.player.killer.power.invisibilitybell.interaction;

import deadbycube.audio.SoundRegistry;
import deadbycube.audio.WorldAudioManager;
import deadbycube.interaction.Interaction;
import deadbycube.interaction.InteractionActionBinding;
import deadbycube.player.killer.KillerPlayer;
import deadbycube.player.killer.power.invisibilitybell.CloakStatus;
import deadbycube.player.killer.power.invisibilitybell.PowerInvisibilityBell;
import deadbycube.util.Progression;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.SoundCategory;
import org.bukkit.World;
import org.bukkit.boss.BarColor;
import org.bukkit.entity.Player;

public class UncloakInteraction extends Interaction {

    private final PowerInvisibilityBell power;
    private final Progression progression;

    public UncloakInteraction(PowerInvisibilityBell power) {
        super(InteractionActionBinding.USE, "uncloak");
        this.power = power;
        this.progression = new Progression("uncloak", BarColor.WHITE);
    }

    @Override
    public void onInteract() {
        KillerPlayer killer = power.getKiller();
        Player killerPlayer = killer.getPlayer();
        WorldAudioManager audioManager = killer.getPlugin().getAudioManager();
        audioManager.playSound(SoundRegistry.POWER_INVISIBILITY_BELL_BURN, SoundCategory.MASTER, killerPlayer.getLocation());

        this.progression.display(power.getKiller());
    }

    @Override
    public void onUpdate(int uncloakProgress) {
        double uncloakTime = power.getUncloakTime().getValue();

        if (uncloakProgress % 2 == 0) {
            this.progression.setMaxValue(uncloakTime);
            this.progression.setValue(uncloakProgress);
        }

        KillerPlayer killer = power.getKiller();
        Player killerPlayer = killer.getPlayer();
        World world = killerPlayer.getWorld();
        WorldAudioManager audioManager = killer.getPlugin().getAudioManager();
        Location particleLocation = killerPlayer.getLocation().add(0, .5, 0);

        if ((uncloakProgress % 4) == 0)
            world.spawnParticle(Particle.SMOKE_LARGE, particleLocation, 5, .05, .25, .05, 0.015);

        if (uncloakProgress == (int) (uncloakTime * .08))
            audioManager.playSound(SoundRegistry.KILLER_WRAITH_WEAPON_ARM, SoundCategory.MASTER, killer.getPlayer().getLocation());

        if (uncloakProgress == (int) (uncloakTime * .55)) {
            audioManager.playSound(SoundRegistry.POWER_INVISIBILITY_BELL_BELL_SINGLE, SoundCategory.MASTER, killerPlayer.getLocation());
            audioManager.playSound(SoundRegistry.POWER_INVISIBILITY_BELL_BELL_IRON, SoundCategory.MASTER, killerPlayer.getLocation());
        }

        if (uncloakProgress == (int) (uncloakTime * .90)) {
            audioManager.playSound(SoundRegistry.POWER_INVISIBILITY_BELL_BELL_SINGLE, SoundCategory.MASTER, killerPlayer.getLocation());
            audioManager.playSound(SoundRegistry.POWER_INVISIBILITY_BELL_BELL_IRON, SoundCategory.MASTER, killerPlayer.getLocation());
        }

        if (uncloakProgress >= uncloakTime)
            this.stopInteract();
    }

    @Override
    public void onStopInteract(int cloakProgress) {
        double uncloakTime = power.getUncloakTime().getValue();

        if (cloakProgress >= uncloakTime)
            power.setStatus(CloakStatus.UNCLOAKED);

        this.progression.setValue(0);
        this.progression.reset(deadByCubePlayer);
    }

    @Override
    public boolean isInteracting() {
        return deadByCubePlayer.getPlayer().isHandRaised();
    }

}
