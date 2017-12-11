package deadbycube.player.killer.power;

import deadbycube.player.killer.Killer;
import deadbycube.util.DBDSounds;
import deadbycube.util.MathUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class PowerBreath extends Power {

    private static final float DISTANCE_FULL_BLINK = 24.0f;
    private static final int MAX_CHARGE_TIME = 50;

    private static final float NORMAL_WALK_SPEED = 0.23f;
    private static final float STUN_WALK_SPEED = 0.04f;

    private static final int STUN_BASE_TIME = 40;
    private static final int STUN_TIME_MULTIPLIER = 10;

    private int chargeTime;

    public PowerBreath(Killer killer) {
        super(killer);

        killer.getPlayer().setWalkSpeed(NORMAL_WALK_SPEED);
    }

    @Override
    public boolean canUse() {
        return true;
    }

    @Override
    protected void onUse() {
        this.chargeTime = 0;

        Player player = killer.getPlayer();
        World world = player.getWorld();
        world.playSound(player.getLocation(), DBDSounds.KILLER_NURSE_BLINK_CHARGE, 1, 1);
        world.playSound(player.getLocation(), DBDSounds.KILLER_NURSE_TELEPORT_CHARGING, 1, 1);
    }

    @Override
    protected void onUpdate() {
        if (chargeTime < MAX_CHARGE_TIME) {
            chargeTime++;
            killer.getPlayer().sendTitle("Charge time", String.valueOf(chargeTime), 0, 10, 0);
        } else if (chargeTime == MAX_CHARGE_TIME) {
            Player player = killer.getPlayer();
            player.getWorld().playSound(player.getLocation(), DBDSounds.KILLER_NURSE_BLINK_CHARGE_FULL, 1, 1);
            chargeTime++;
        }
    }

    @Override
    protected void onStopUse() {
        Player player = killer.getPlayer();

        float distanceMultiplier = (float) chargeTime / (float) MAX_CHARGE_TIME;
        float distance = distanceMultiplier * DISTANCE_FULL_BLINK;

        player.sendMessage("==============================");
        player.sendMessage("Charge Time: " + chargeTime);
        player.sendMessage("Distance Multiplier: " + distanceMultiplier);
        player.sendMessage("Theoretical Distance: " + distance);

        Location oldLocation = player.getLocation();
        Location playerLocation = player.getLocation();
        Vector direction = MathUtils.getDirection(playerLocation.getYaw(), 0);

        player.teleport(playerLocation.add(
                direction.getX() * distance,
                0,
                direction.getZ() * distance
        ));

        World world = player.getWorld();
        world.playSound(player.getLocation(), DBDSounds.KILLER_NURSE_BLINK_APPEAR, 1, 1);
        world.playSound(player.getLocation(), DBDSounds.KILLER_NURSE_TELEPORT_APPEAR, 100, 1);

        player.sendMessage("- Real Distance: " + oldLocation.distance(player.getLocation()));

        Bukkit.getScheduler().runTaskLater(killer.getPlugin(), () -> {
            world.playSound(player.getLocation(), DBDSounds.KILLER_NURSE_TELEPORT_DISAPPEAR, 1, 1);

            // Stun the nurse post tp
            int additionalStunTime = Math.round(distanceMultiplier * (float) STUN_TIME_MULTIPLIER);
            int stunTime = STUN_BASE_TIME + additionalStunTime;

            player.sendMessage("==============================");
            player.sendMessage("Stun Time Multiplier: " + STUN_TIME_MULTIPLIER);
            player.sendMessage("Additional Stun Time: " + additionalStunTime);
            player.sendMessage("- Stun Time: " + stunTime);

            player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, stunTime + 20, 0, false, false));
            player.setWalkSpeed(STUN_WALK_SPEED);
            Bukkit.getScheduler().runTaskLater(killer.getPlugin(), () -> player.setWalkSpeed(NORMAL_WALK_SPEED), stunTime);
        }, 40);
    }

}
