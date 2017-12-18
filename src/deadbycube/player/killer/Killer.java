package deadbycube.player.killer;

import deadbycube.DeadByCube;
import deadbycube.player.DbcPlayer;
import deadbycube.player.PlayerType;
import deadbycube.player.killer.power.Power;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public abstract class Killer extends DbcPlayer {

    public static final int DEFAULT_STUN_TIME = 30;
    private static final float STUN_WALK_SPEED = 0.0F;
    public static final float DEFAULT_WALK_SPEED = 0.26f; //0.248f
    private static final int DEFAULT_FOOD_LEVEL = 2;

    private final String name;

    private Power power;
    private float walkSpeed = DEFAULT_WALK_SPEED;

    Killer(DeadByCube plugin, Player player, String name) {
        super(plugin, player);

        this.name = name;

        player.setFoodLevel(DEFAULT_FOOD_LEVEL);
    }

    public void setWalkSpeedModifier(float walkSpeedModifier) {
        this.player.setWalkSpeed(walkSpeed * walkSpeedModifier);
    }

    public void stun(int stunTime) {
        player.setWalkSpeed(STUN_WALK_SPEED);

        Location playerLocation = player.getLocation();
        playerLocation.setPitch(80);
        player.teleport(playerLocation);

        player.getWorld().playSound(playerLocation, "killer." + name + ".stun", 1, 1);

        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, stunTime + 20, 0, false, false));
        Bukkit.getScheduler().runTaskLater(plugin, () -> player.setWalkSpeed(walkSpeed), stunTime);
    }

    @Override
    public void reset() {
        power.reset();
    }

    @Override
    public KillerActionHandler createActionHandler() {
        return new KillerActionHandler(this);
    }

    @Override
    public PlayerType getType() {
        return PlayerType.KILLER;
    }

    public Power getPower() {
        return power;
    }

    public void setPower(Power power) {
        if (this.power != null)
            this.power.reset();
        this.power = power;
    }

    public void setWalkSpeed(float walkSpeed) {
        this.walkSpeed = walkSpeed;
    }

}
