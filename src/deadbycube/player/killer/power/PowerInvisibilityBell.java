package deadbycube.player.killer.power;

import deadbycube.audio.PlayerAudioManager;
import deadbycube.audio.SoundRegistry;
import deadbycube.player.killer.Killer;
import org.bukkit.Particle;
import org.bukkit.SoundCategory;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PowerInvisibilityBell extends Power {

    private CloakStatus status = CloakStatus.CLOAKED;
    private int cloakProgress;

    public PowerInvisibilityBell(Killer killer) {
        super(killer);
    }

    @Override
    public void init(boolean using) {
        super.init(using);

        Player player = killer.getPlayer();
        player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 999999, 0, false, false));
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
    }

    @Override
    public void onUpdate() {
        Player player = killer.getPlayer();
        World world = player.getWorld();

        if (cloakProgress >= (status.cloakTime * 0.12) && (cloakProgress % 4) == 0)
            world.spawnParticle(Particle.SMOKE_LARGE, player.getLocation(), 5, .05, .25, .05, 0.015);

        /*if (cloakProgress == (int) (cloakTime * 0.08) || cloakProgress == (int) (cloakTime * 0.60)) {
            world.playSound(player.getLocation(), DBDSounds.KILLER_WRAITH_BELL_SINGLE, .5f, 1);
        }*/

        if (++cloakProgress == status.cloakTime) this.stopUse();
    }

    @Override
    protected void onStopUse() {
        if (cloakProgress != status.cloakTime)
            return;

        this.status = status == CloakStatus.CLOAKED ? CloakStatus.UNCLOAKED : CloakStatus.CLOAKED;

        Player player = killer.getPlayer();
        PlayerAudioManager audioManager = killer.getAudioManager();
        if (status == CloakStatus.CLOAKED) {
            audioManager.playSound(SoundRegistry.KILLER_WRAITH_INVISIBILITY_ON, SoundCategory.MASTER, killer.getPlayer().getLocation(), 1000, 1);
            player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 999999, 0, false, false));
        } else if (status == CloakStatus.UNCLOAKED) {
            audioManager.playSound(SoundRegistry.KILLER_WRAITH_INVISIBILITY_OFF, SoundCategory.MASTER, killer.getPlayer().getLocation(), 1000, 1);
            player.removePotionEffect(PotionEffectType.INVISIBILITY);
        }
    }

    private enum CloakStatus {
        CLOAKED(40),
        UNCLOAKED(20);

        private final int cloakTime;

        CloakStatus(int cloakTime) {
            this.cloakTime = cloakTime;
        }
    }

}
