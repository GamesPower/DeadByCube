package deadbycube.player.killer;

import deadbycube.DeadByCube;
import deadbycube.player.DbcPlayer;
import deadbycube.player.PlayerType;
import deadbycube.player.killer.power.Power;
import deadbycube.util.Tickable;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public abstract class Killer extends DbcPlayer {

    private static final int DEFAULT_STUN_TIME = 30;
    private static final float STUN_WALK_SPEED = 0.0F;
    private static final float DEFAULT_WALK_SPEED = 0.2f;

    private Power power;
    //private final LivingEntity entity;

    Killer(DeadByCube plugin, Player player, EntityType entityType) {
        super(plugin, player);

        /*this.entity = (LivingEntity) plugin.getMap().getWorld().spawnEntity(getEntityLocation(), entityType);
        this.initEntity();*/
    }

    @Override
    protected void reset() {
        power.reset();
    }

    public Power getPower() {
        return power;
    }

    public void setPower(Power power) {
        this.power = power;
    }

    public void stun() {
        this.stun(DEFAULT_STUN_TIME);
    }

    private void stun(int stunTime) {
        player.setWalkSpeed(STUN_WALK_SPEED);

        Location playerLocation = player.getLocation();
        playerLocation.setPitch(80);
        player.teleport(playerLocation);

        player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, stunTime + 20, 0, false, false));
        Bukkit.getScheduler().runTaskLater(plugin, () -> player.setWalkSpeed(DEFAULT_WALK_SPEED), stunTime);
    }

    @Override
    public KillerActionHandler createActionHandler() {
        return new KillerActionHandler(this);
    }

    @Override
    public PlayerType getType() {
        return PlayerType.KILLER;
    }


    /*private void initEntity() {
        this.entity.setGravity(false);
        this.entity.setInvulnerable(true);
        this.entity.setSilent(true);
        this.entity.setAI(false);
        this.entity.setCanPickupItems(false);
        this.entity.setCollidable(false);
    }

    private Location getEntityLocation() {
        Location playerLocation = player.getLocation();
        Vector playerDirection = playerLocation.getDirection().setY(0);
        return playerLocation.subtract(playerDirection);
    }*/

}
