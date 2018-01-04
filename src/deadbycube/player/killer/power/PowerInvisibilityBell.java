package deadbycube.player.killer.power;

import deadbycube.audio.AudioManager;
import deadbycube.audio.SoundRegistry;
import deadbycube.audio.WorldAudioManager;
import deadbycube.player.killer.Killer;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.SoundCategory;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PowerInvisibilityBell extends Power {

    private static final int CLOAK_TIME = 30;
    private static final int UNCLOAK_TIME = 40;
    private static final int WHOOSH_DISTANCE = 40;

    private CloakStatus status = CloakStatus.UNCLOAKED;
    private int cloakProgress;
    private int cloakTime;

    public PowerInvisibilityBell(Killer killer) {
        super(killer);
    }

    @Override
    public void init(boolean using) {
        super.init(using);

        this.setStatus(CloakStatus.CLOAKED, killer.getAudioManager());
    }

    @Override
    public void reset() {
        killer.getPlayer().removePotionEffect(PotionEffectType.INVISIBILITY);
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

        WorldAudioManager audioManager = killer.getPlugin().getAudioManager();
        audioManager.playSound(SoundRegistry.KILLER_WRAITH_WEAPON_ARM, SoundCategory.MASTER, killer.getPlayer().getLocation());
    }

    @Override
    public void onUpdate() {
        Player killerPlayer = killer.getPlayer();
        World world = killerPlayer.getWorld();
        WorldAudioManager audioManager = killer.getPlugin().getAudioManager();

        if (status == CloakStatus.CLOAKED) {
            if (cloakProgress >= (cloakTime * 0.12) && (cloakProgress % 4) == 0)
                world.spawnParticle(Particle.SMOKE_LARGE, killerPlayer.getLocation(), 5, .05, .25, .05, 0.015);

        } else if (status == CloakStatus.UNCLOAKED) {
            if (cloakProgress % 4 == 0)
                world.spawnParticle(Particle.SMOKE_LARGE, killerPlayer.getLocation(), 5, .05, .25, .05, 0.015);

            if (cloakProgress == (int) (cloakTime * .20)) {
                audioManager.playSound(SoundRegistry.POWER_INVISIBILITY_BELL_BELL_SINGLE, SoundCategory.MASTER, killerPlayer.getLocation());
                audioManager.playSound(SoundRegistry.POWER_INVISIBILITY_BELL_BELL_IRON, SoundCategory.MASTER, killerPlayer.getLocation());
                audioManager.playSound(SoundRegistry.POWER_INVISIBILITY_BELL_BURN, SoundCategory.MASTER, killerPlayer.getLocation());
                audioManager.playSound(SoundRegistry.POWER_INVISIBILITY_BELL_DISSOLVE, SoundCategory.MASTER, killerPlayer.getLocation());
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
    }

    private void setStatus(CloakStatus status, AudioManager audioManager) {
        if (this.status != status) {
            this.status = status;

            Player killerPlayer = killer.getPlayer();
            Location killerLocation = killerPlayer.getLocation();
            if (status == CloakStatus.CLOAKED) {
                audioManager.playSound(SoundRegistry.POWER_INVISIBILITY_BELL_VISIBLE, SoundCategory.MASTER, killerLocation, 10, 1, deadByCubePlayer -> deadByCubePlayer.getPlayer().getLocation().distance(killerLocation) < WHOOSH_DISTANCE);
                killerPlayer.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 999999, 0, false, false));
                killer.getTerrorRadius().forceValue(0d);
            } else if (status == CloakStatus.UNCLOAKED) {
                audioManager.playSound(SoundRegistry.POWER_INVISIBILITY_BELL_INVISIBLE, SoundCategory.MASTER, killerLocation, 10, 1, deadByCubePlayer -> deadByCubePlayer.getPlayer().getLocation().distance(killerLocation) < WHOOSH_DISTANCE);
                killerPlayer.removePotionEffect(PotionEffectType.INVISIBILITY);
                killer.getTerrorRadius().resetValue();
            }
        }
    }

    private enum CloakStatus {
        CLOAKED, UNCLOAKED
    }

}
