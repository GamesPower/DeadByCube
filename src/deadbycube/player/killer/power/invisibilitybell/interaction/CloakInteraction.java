package deadbycube.player.killer.power.invisibilitybell.interaction;

import deadbycube.audio.WorldAudioManager;
import deadbycube.interaction.Interaction;
import deadbycube.player.killer.KillerPlayer;
import deadbycube.player.killer.power.invisibilitybell.CloakStatus;
import deadbycube.player.killer.power.invisibilitybell.PowerInvisibilityBell;
import deadbycube.registry.SoundRegistry;
import deadbycube.util.MagicalValue;
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
        super("cloak");
        this.power = power;
        this.progression = new Progression("cloak", BarColor.WHITE);
    }

    @Override
    protected void onInteract() {
        KillerPlayer killer = power.getKiller();
        Player killerPlayer = killer.getPlayer();
        WorldAudioManager audioManager = killer.getPlugin().getAudioManager();
        audioManager.playSound(SoundRegistry.KILLER_WRAITH_WEAPON_ARM, SoundCategory.MASTER, killerPlayer.getLocation());

        this.progression.display(power.getKiller());
    }

    @Override
    protected void onUpdate(int cloakProgress) {
        MagicalValue cloakTime = power.getCloakTime();

        if (cloakProgress % 2 == 0) {
            if (cloakTime.isLower())
                this.progression.setColor(BarColor.YELLOW);
            else if (cloakTime.isGreater())
                this.progression.setColor(BarColor.RED);
            else
                this.progression.setColor(BarColor.WHITE);

            this.progression.setMaxValue(cloakTime.getValue());
            this.progression.setValue(cloakProgress);
        }

        KillerPlayer killer = power.getKiller();
        Player killerPlayer = killer.getPlayer();
        World world = killerPlayer.getWorld();
        WorldAudioManager audioManager = killer.getPlugin().getAudioManager();
        Location particleLocation = killerPlayer.getLocation().add(0, .5, 0);

        if (cloakProgress >= (int) (cloakTime.getValue() * .20) && (cloakProgress % 2) == 0)
            world.spawnParticle(Particle.SMOKE_NORMAL, particleLocation, 20, .1, .5, .1, 0.015);

        if (cloakProgress == (int) (cloakTime.getValue() * .20)) {
            audioManager.playSound(SoundRegistry.POWER_INVISIBILITY_BELL_BELL_SINGLE, SoundCategory.MASTER, killerPlayer.getLocation());
            audioManager.playSound(SoundRegistry.POWER_INVISIBILITY_BELL_BELL_IRON, SoundCategory.MASTER, killerPlayer.getLocation());
            audioManager.playSound(SoundRegistry.POWER_INVISIBILITY_BELL_BURN, SoundCategory.MASTER, killerPlayer.getLocation());
        }

        if (cloakProgress == (int) (cloakTime.getValue() * .65)) {
            audioManager.playSound(SoundRegistry.POWER_INVISIBILITY_BELL_BELL_SINGLE, SoundCategory.MASTER, killerPlayer.getLocation());
            audioManager.playSound(SoundRegistry.POWER_INVISIBILITY_BELL_BELL_IRON, SoundCategory.MASTER, killerPlayer.getLocation());
        }

        if (cloakProgress >= cloakTime.getValue())
            this.stopInteract();
    }

    @Override
    protected void onStopInteract(int cloakProgress) {
        double cloakTime = power.getCloakTime().getValue();
        if (cloakProgress >= cloakTime)
            power.setStatus(CloakStatus.CLOAKED);

        this.progression.setValue(0);
        this.progression.reset(interactor);
    }

    @Override
    public boolean isInteracting() {
        return super.isInteracting() && interactor.getPlayer().isHandRaised();
    }

}
