package deadbycube.player.killer.power;

import deadbycube.player.killer.Killer;
import deadbycube.util.DBDSounds;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class PowerBell extends Power {

    private static final float CLOAKED_WALK_SPEED = 0.3f;
    private static final float UNCLOAKED_WALK_SPEED = 0.2f;

    private CloakStatus status = CloakStatus.CLOAKED;
    private int cloakTime;
    private int cloakProgress;

    public PowerBell(Killer killer) {
        super(killer);

        killer.getPlayer().setWalkSpeed(CLOAKED_WALK_SPEED);
    }

    @Override
    public boolean canUse() {
        return true;
    }

    @Override
    public void reset() {
        killer.getPlayer().removePotionEffect(PotionEffectType.INVISIBILITY);
        super.reset();
    }

    @Override
    public void onUse() {
        this.cloakProgress = 0;
        this.cloakTime = status.cloakTime;
    }

    @Override
    public void onUpdate() {
        Player player = killer.getPlayer();
        World playerWorld = player.getWorld();

        if (cloakProgress >= (cloakTime * 0.10))
            playerWorld.spawnParticle(Particle.SMOKE_LARGE, player.getLocation(), 5, .05, .25, .05, 0.015);

        if (++cloakProgress >= cloakTime) {
            this.toggleStatus();

            if (status == CloakStatus.CLOAKED) {
                playerWorld.playSound(player.getLocation(), DBDSounds.KILLER_WRAITH_INVISIBILITY_ON, 200, 1);
                player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 999999, 0, false, false));
                player.setWalkSpeed(CLOAKED_WALK_SPEED);
            } else {
                playerWorld.playSound(player.getLocation(), DBDSounds.KILLER_WRAITH_INVISIBILITY_OFF, 200, 1);
                player.removePotionEffect(PotionEffectType.INVISIBILITY);
                player.setWalkSpeed(UNCLOAKED_WALK_SPEED);
            }

            this.stopUsing();
        }
    }

    @Override
    protected void onStopUse() {
    }

    private void toggleStatus() {
        this.status = status.equals(CloakStatus.CLOAKED) ? CloakStatus.UNCLOAKED : CloakStatus.CLOAKED;
    }


    private enum CloakStatus {
        CLOAKED(40),
        UNCLOAKED(30);

        private final int cloakTime;

        CloakStatus(int cloakTime) {
            this.cloakTime = cloakTime;
        }
    }

}
