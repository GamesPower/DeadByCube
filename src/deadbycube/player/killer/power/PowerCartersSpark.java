package deadbycube.player.killer.power;

import deadbycube.audio.PlayerAudioManager;
import deadbycube.player.killer.Killer;
import deadbycube.util.DBDSounds;
import deadbycube.util.MathUtils;
import org.bukkit.Bukkit;
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

        killer.setWalkSpeed(Killer.DEFAULT_WALK_SPEED);
    }

    @Override
    public boolean canUse() {
        return chargeTime == 0;
    }

    @Override
    protected void onUse() {
        Player player = killer.getPlayer();
        killer.setWalkSpeedModifier(3);

        World world = player.getWorld();
        Location location = player.getLocation();
        world.playSound(location, DBDSounds.KILLER_DOCTOR_CHARGE, 1, 1);
        world.playSound(location, DBDSounds.KILLER_DOCTOR_CHARGE_BASS, 1, 1);
        world.playSound(location, DBDSounds.KILLER_DOCTOR_CHARGE_HIGH, 1, 1);
    }

    @Override
    protected void onUpdate() {
        Player player = killer.getPlayer();
        World world = player.getWorld();

        Location particleLocation = player.getLocation().add(0, .8, 0);
        for (int i = 0; i < (chargeTime / 2); i++)
            world.spawnParticle(Particle.SMOKE_NORMAL, particleLocation, 1, .2, .35, .2, 0);

        if (++chargeTime >= SHOCK_CHARGE_TIME) {
            killer.setWalkSpeedModifier(1);

            float distanceStep = .5f;
            float angleStep = 3.2f;

            Location soundLocation = player.getLocation();
            world.playSound(soundLocation, DBDSounds.KILLER_DOCTOR_ATTACK_READY, 1, 1);
            world.playSound(soundLocation, DBDSounds.KILLER_DOCTOR_ATTACK, 1f, 1f);

            for (float distance = 1.5f; distance < SHOCK_DISTANCE; distance += distanceStep) {
                for (double angle = -SHOCK_ANGLE; angle <= SHOCK_ANGLE; angle += angleStep) {
                    Location location = player.getLocation();
                    location = location.add(
                            MathUtils.getDirection(location.getYaw() + angle, 0).multiply(distance)
                    );
                    world.spawnParticle(Particle.CLOUD, location, 0, 1, 0, 1, 0);
                }
            }

            Bukkit.getScheduler().runTaskLater(killer.getPlugin(), () -> world.playSound(player.getLocation(), DBDSounds.KILLER_DOCTOR_LAUGH, 1, 1), 10);
            this.stopUpdate();
        }
    }

    @Override
    protected void onStopUse() {
        this.chargeTime = 0;
        killer.setWalkSpeedModifier(1);
    }

}
