package deadbycube.player.killer.power;

import deadbycube.audio.WorldAudioManager;
import deadbycube.player.killer.Killer;
import deadbycube.util.MathUtils;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class PowerCartersSpark extends Power {

    private static final int SHOCK_DISTANCE = 10;
    private static final int SHOCK_CHARGE_TIME = 30;
    private static final int SHOCK_ANGLE = 35;

    private int chargeTime;

    public PowerCartersSpark(Killer killer) {
        super(killer);
    }

    @Override
    public boolean canUse() {
        return chargeTime == 0;
    }

    @Override
    protected void onUse() {
        Player player = killer.getPlayer();
        WorldAudioManager audioManager = killer.getPlugin().getAudioManager();
        Location soundLocation = player.getLocation();

        /*audioManager.playGlobalSound(SoundRegistry.KILLER_DOCTOR_CHARGE, SoundCategory.MASTER, soundLocation, 1, 1);
        audioManager.playGlobalSound(SoundRegistry.KILLER_DOCTOR_CHARGE_BASS, SoundCategory.MASTER, soundLocation, 1, 1);
        audioManager.playGlobalSound(SoundRegistry.KILLER_DOCTOR_CHARGE_HIGH, SoundCategory.MASTER, soundLocation, 1, 1);*/
    }

    @Override
    protected void onUpdate() {
        Player player = killer.getPlayer();
        World world = player.getWorld();

        Location particleLocation = player.getLocation().add(0, .8, 0);
        for (int i = 0; i < (chargeTime / 2); i++)
            world.spawnParticle(Particle.SMOKE_NORMAL, particleLocation, 1, .2, .35, .2, 0);

        if (++chargeTime == SHOCK_CHARGE_TIME) {
            this.stopUse();
        }
    }

    @Override
    protected void onStopUse() {
        WorldAudioManager audioManager = killer.getPlugin().getAudioManager();
        if (chargeTime == SHOCK_CHARGE_TIME) {
            Player player = killer.getPlayer();
            World world = player.getWorld();

            float distanceStep = .5f;
            float angleStep = 3.2f;

            /*Location soundLocation = player.getLocation();
            audioManager.playGlobalSound(SoundRegistry.KILLER_DOCTOR_ATTACK, SoundCategory.MASTER, soundLocation, 1, 1);
            audioManager.playGlobalSound(SoundRegistry.KILLER_DOCTOR_ATTACK_READY, SoundCategory.MASTER, soundLocation, 1, 1);*/

            for (float distance = 1.5f; distance < SHOCK_DISTANCE; distance += distanceStep) {
                for (double angle = -SHOCK_ANGLE; angle <= SHOCK_ANGLE; angle += angleStep) {
                    Location location = player.getLocation();
                    location = location.add(
                            MathUtils.getDirection(location.getYaw() + angle, 0).multiply(distance)
                    );
                    world.spawnParticle(Particle.CLOUD, location, 0, 1, 0, 1, 0);
                }
            }

            //audioManager.playGlobalSoundLater(SoundRegistry.KILLER_DOCTOR_LAUGH, SoundCategory.MASTER, player.getLocation(), 1, 1, 10);

        } else {
            /*audioManager.stopSound(SoundRegistry.KILLER_DOCTOR_CHARGE);
            audioManager.stopSound(SoundRegistry.KILLER_DOCTOR_CHARGE_BASS);
            audioManager.stopSound(SoundRegistry.KILLER_DOCTOR_CHARGE_HIGH);*/
        }

        this.chargeTime = 0;
    }

}
