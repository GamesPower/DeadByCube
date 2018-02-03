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

public class CloakInteraction extends Interaction {

    private final PowerInvisibilityBell power;
    private final Progression progression;

    public CloakInteraction(PowerInvisibilityBell power) {
        super(InteractionActionBinding.USE, "cloak");
        this.power = power;
        this.progression = new Progression("cloak", BarColor.WHITE);
    }

    @Override
    public void onInteract() {
        KillerPlayer killer = power.getKiller();
        Player killerPlayer = killer.getPlayer();
        WorldAudioManager audioManager = killer.getPlugin().getAudioManager();
        audioManager.playSound(SoundRegistry.KILLER_WRAITH_WEAPON_ARM, SoundCategory.MASTER, killerPlayer.getLocation());

        this.progression.display(power.getKiller());
    }

    @Override
    public void onUpdate(int cloakProgress) {
        double cloakTime = power.getCloakTime().getValue();

        if (cloakProgress % 2 == 0) {
            this.progression.setMaxValue(cloakTime);
            this.progression.setValue(cloakProgress);
        }

        KillerPlayer killer = power.getKiller();
        Player killerPlayer = killer.getPlayer();
        World world = killerPlayer.getWorld();
        WorldAudioManager audioManager = killer.getPlugin().getAudioManager();
        Location particleLocation = killerPlayer.getLocation().add(0, .5, 0);

        if (cloakProgress >= (int) (cloakTime * .20) && cloakProgress % 4 == 0)
            world.spawnParticle(Particle.SMOKE_LARGE, particleLocation, 5, .05, .25, .05, 0.015);

        if (cloakProgress == (int) (cloakTime * .20)) {
            audioManager.playSound(SoundRegistry.POWER_INVISIBILITY_BELL_BELL_SINGLE, SoundCategory.MASTER, killerPlayer.getLocation());
            audioManager.playSound(SoundRegistry.POWER_INVISIBILITY_BELL_BELL_IRON, SoundCategory.MASTER, killerPlayer.getLocation());
            audioManager.playSound(SoundRegistry.POWER_INVISIBILITY_BELL_BURN, SoundCategory.MASTER, killerPlayer.getLocation());
        }

        if (cloakProgress == (int) (cloakTime * .65)) {
            audioManager.playSound(SoundRegistry.POWER_INVISIBILITY_BELL_BELL_SINGLE, SoundCategory.MASTER, killerPlayer.getLocation());
            audioManager.playSound(SoundRegistry.POWER_INVISIBILITY_BELL_BELL_IRON, SoundCategory.MASTER, killerPlayer.getLocation());
        }

        if (cloakProgress >= cloakTime)
            this.stopInteract();
    }

    @Override
    public void onStopInteract(int cloakProgress) {
        double cloakTime = power.getCloakTime().getValue();
        if (cloakProgress >= cloakTime)
            power.setStatus(CloakStatus.CLOAKED);

        this.progression.setValue(0);
        this.progression.reset(deadByCubePlayer);
    }

    @Override
    public boolean isInteracting() {
        return deadByCubePlayer.getPlayer().isHandRaised();
    }

}
