package deadbycube.player.killer.power;

import deadbycube.audio.AudioManager;
import deadbycube.audio.SoundRegistry;
import deadbycube.audio.WorldAudioManager;
import deadbycube.player.killer.KillerPlayer;
import deadbycube.util.MagicalValue;
import deadbycube.util.Progression;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.SoundCategory;
import org.bukkit.World;
import org.bukkit.boss.BarColor;
import org.bukkit.entity.Player;

public class PowerInvisibilityBell extends Power {

    private static final String CLOAKED_MODIFIER = "power.invisibility_bell.cloaked";

    private static final int CLOAK_TIME = 30;
    private static final double CLOAK_SPEED_BONUS = 0.12;
    private static final double CLOAK_BREATH_REDUCTION = .07;
    private static final int UNCLOAK_TIME = 50;
    private static final int WHOOSH_DISTANCE = 40;

    private final Progression progression;

    private CloakStatus status = CloakStatus.UNCLOAKED;
    private int cloakProgress;
    private int cloakTime;

    public PowerInvisibilityBell(KillerPlayer killer) {
        super(killer);

        this.progression = new Progression("power.invisibility_bell.action", BarColor.WHITE);
    }

    @Override
    public void init(boolean using) {
        super.init(using);

        this.progression.display(killer);
    }

    @Override
    public void reset() {
        this.progression.reset();

        super.reset();
    }

    @Override
    public boolean canUse() {
        return true;
    }

    @Override
    public void onUse() {
        this.cloakProgress = 0;
        this.cloakTime = status == CloakStatus.CLOAKED ? UNCLOAK_TIME : CLOAK_TIME;
        this.progression.setMaxValue(cloakTime);
    }

    @Override
    public void onUpdate() {
        if (cloakProgress % 2 == 0)
            this.progression.setValue(cloakProgress);

        Player killerPlayer = killer.getPlayer();
        World world = killerPlayer.getWorld();
        WorldAudioManager audioManager = killer.getPlugin().getAudioManager();
        Location particleLocation = killerPlayer.getLocation().add(0, .5, 0);

        if (status == CloakStatus.CLOAKED) {
            if ((cloakProgress % 4) == 0)
                world.spawnParticle(Particle.SMOKE_LARGE, particleLocation, 5, .05, .25, .05, 0.015);

            if (cloakProgress == 0)
                audioManager.playSound(SoundRegistry.POWER_INVISIBILITY_BELL_BURN, SoundCategory.MASTER, killer.getPlayer().getLocation());

            if (cloakProgress == (int) (cloakTime * .08))
                audioManager.playSound(SoundRegistry.KILLER_WRAITH_WEAPON_ARM, SoundCategory.MASTER, killer.getPlayer().getLocation());

            if (cloakProgress == (int) (cloakTime * .55)) {
                audioManager.playSound(SoundRegistry.POWER_INVISIBILITY_BELL_BELL_SINGLE, SoundCategory.MASTER, killerPlayer.getLocation());
                audioManager.playSound(SoundRegistry.POWER_INVISIBILITY_BELL_BELL_IRON, SoundCategory.MASTER, killerPlayer.getLocation());
            }

            if (cloakProgress == (int) (cloakTime * .90)) {
                audioManager.playSound(SoundRegistry.POWER_INVISIBILITY_BELL_BELL_SINGLE, SoundCategory.MASTER, killerPlayer.getLocation());
                audioManager.playSound(SoundRegistry.POWER_INVISIBILITY_BELL_BELL_IRON, SoundCategory.MASTER, killerPlayer.getLocation());
            }

        } else if (status == CloakStatus.UNCLOAKED) {
            if (cloakProgress >= (int) (cloakTime * .20) && cloakProgress % 4 == 0)
                world.spawnParticle(Particle.SMOKE_LARGE, particleLocation, 5, .05, .25, .05, 0.015);

            if (cloakProgress == 0)
                audioManager.playSound(SoundRegistry.KILLER_WRAITH_WEAPON_ARM, SoundCategory.MASTER, killer.getPlayer().getLocation());

            if (cloakProgress == (int) (cloakTime * .20)) {
                audioManager.playSound(SoundRegistry.POWER_INVISIBILITY_BELL_BELL_SINGLE, SoundCategory.MASTER, killerPlayer.getLocation());
                audioManager.playSound(SoundRegistry.POWER_INVISIBILITY_BELL_BELL_IRON, SoundCategory.MASTER, killerPlayer.getLocation());
                audioManager.playSound(SoundRegistry.POWER_INVISIBILITY_BELL_BURN, SoundCategory.MASTER, killerPlayer.getLocation());
            }

            if (cloakProgress == (int) (cloakTime * .65)) {
                audioManager.playSound(SoundRegistry.POWER_INVISIBILITY_BELL_BELL_SINGLE, SoundCategory.MASTER, killerPlayer.getLocation());
                audioManager.playSound(SoundRegistry.POWER_INVISIBILITY_BELL_BELL_IRON, SoundCategory.MASTER, killerPlayer.getLocation());
            }
        }

        /*if (cloakProgress == (int) (status.cloakTime * 0.08) || cloakProgress == (int) (status.cloakTime * 0.60)) {
            WorldAudioManager audioManager = killer.getPlugin().getAudioManager();
            audioManager.playGlobalSound(SoundRegistry.POWER_INVISIBILITY_BELL_BELL_SINGLE, SoundCategory.MASTER, killerPlayer.getLocation(), .5f, 1f);
        }*/

        if (++cloakProgress == cloakTime) this.stopUse();
    }

    @Override
    protected void onStopUse() {
        if (cloakProgress == cloakTime)
            this.setStatus(status == CloakStatus.CLOAKED ? CloakStatus.UNCLOAKED : CloakStatus.CLOAKED, killer.getPlugin().getAudioManager());
        this.progression.setValue(0);
    }

    private void setStatus(CloakStatus status, AudioManager audioManager) {
        if (this.status != status) {
            this.status = status;

            Player killerPlayer = killer.getPlayer();
            Location killerLocation = killerPlayer.getLocation();
            if (status == CloakStatus.CLOAKED) {
                audioManager.playSound(SoundRegistry.POWER_INVISIBILITY_BELL_VISIBLE, SoundCategory.MASTER, killerLocation, 15, 1, deadByCubePlayer -> deadByCubePlayer.getPlayer().getLocation().distance(killerLocation) < WHOOSH_DISTANCE);
                killer.setHidden(true);
                killer.getTerrorRadius().forceValue(0d);
                killer.getBreathVolume().addModifier(CLOAKED_MODIFIER, CLOAK_BREATH_REDUCTION, MagicalValue.Operation.SUBTRACT);
                killer.walkSpeed().addModifier(CLOAKED_MODIFIER, CLOAK_SPEED_BONUS, MagicalValue.Operation.MULTIPLY);
            } else if (status == CloakStatus.UNCLOAKED) {
                audioManager.playSound(SoundRegistry.POWER_INVISIBILITY_BELL_INVISIBLE, SoundCategory.MASTER, killerLocation, 15, 1, deadByCubePlayer -> deadByCubePlayer.getPlayer().getLocation().distance(killerLocation) < WHOOSH_DISTANCE);
                killer.setHidden(false);
                killer.getTerrorRadius().resetValue();
                killer.getBreathVolume().removeModifier(CLOAKED_MODIFIER);
                killer.walkSpeed().removeModifier(CLOAKED_MODIFIER);
            }
        }
    }

    private enum CloakStatus {
        CLOAKED, UNCLOAKED
    }

}
