package deadbycube.player.killer.power;

import deadbycube.player.killer.Killer;
import deadbycube.util.MathUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class PowerBreath extends Power {

    private static final float DISTANCE_FULL_BLINK = 20.0f;
    private static final int MAX_CHARGE_TIME = 20;

    private static final float NORMAL_WALK_SPEED = 0.19f;
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
    }

    @Override
    protected void onUpdate() {
        if (chargeTime < MAX_CHARGE_TIME)
            chargeTime++;
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

        player.sendMessage("- Real Distance: " + oldLocation.distance(player.getLocation()));

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
    }

}
