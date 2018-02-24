package deadbycube.player.killer.power.huntinghatchets;

import deadbycube.DeadByCube;
import deadbycube.audio.WorldAudioManager;
import deadbycube.player.survivor.SurvivorPlayer;
import deadbycube.registry.SoundRegistry;
import deadbycube.util.AxisAlignedBB;
import deadbycube.util.ItemStackBuilder;
import deadbycube.util.MathUtils;
import deadbycube.util.TickLoop;
import deadbycube.world.DeadByCubeWorld;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.SoundCategory;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import java.util.List;

public class HatchetProjectile {

    public static final double HATCHET_OFFSET = .65;

    private static final double GRAVITY = .05;
    private static final ItemStack HATCHET_ITEM_STACK = new ItemStackBuilder(Material.IRON_AXE).build();

    private final TickLoop tickLoop;
    private final ArmorStand armorStand;
    private final float yaw;

    private final Vector velocity;
    private int tick = 0;

    public HatchetProjectile(Location location, float yaw, Vector velocity) {
        World world = location.getWorld();
        this.tickLoop = new TickLoop(this::update);
        this.armorStand = (ArmorStand) world.spawnEntity(location, EntityType.ARMOR_STAND);
        this.yaw = yaw;

        this.armorStand.setVisible(false);
        this.armorStand.setGravity(false);
        this.armorStand.setSmall(true);
        this.armorStand.setMarker(true);
        this.armorStand.setItemInHand(HATCHET_ITEM_STACK);

        this.velocity = velocity;

        this.tickLoop.startTask();
    }

    private void update() {
        tick++;

        // update hatchet animation
        this.armorStand.setRightArmPose(new EulerAngle(tick % 360, 0, 0));

        // update hatchet velocity / position
        this.velocity.subtract(new Vector(0, GRAVITY, 0));
        Location location = armorStand.getLocation().add(velocity);
        this.armorStand.teleport(location);

        // adds HATCHET_OFFSET and right offset for corresponding to the hatchet location
        Vector vector = MathUtils.direction(yaw, 0);
        location.add(-vector.getZ() * .2, HATCHET_OFFSET, vector.getX() * .2);

        // remove the hatchet if in block
        if (!location.getBlock().isEmpty() || location.getY() < DeadByCubeWorld.WORLD_Y)
            this.remove();

        // checks for hit
        DeadByCube plugin = DeadByCube.getInstance();
        List<SurvivorPlayer> survivors = plugin.getPlayerList().getSurvivors();
        for (SurvivorPlayer survivor : survivors) {
            Player survivorPlayer = survivor.getPlayer();
            AxisAlignedBB survivorAABB = AxisAlignedBB.fromEntity(survivorPlayer);

            AxisAlignedBB hatchetAABB = new AxisAlignedBB(
                    location.clone().subtract(.4, .4, .4).toVector(),
                    location.clone().add(.4, .4, .4).toVector()
            );

            if (hatchetAABB.isCollide(survivorAABB)) {
                survivor.hit();
                this.remove();
            }
        }

        // play throw loop sound each 6 ticks
        if (tick % 6 == 0) {
            WorldAudioManager audioManager = plugin.getAudioManager();
            audioManager.playSound(SoundRegistry.POWER_HUNTING_HATCHETS_THROW_LOOP, SoundCategory.MASTER, location);
        }
    }

    private void remove() {
        this.tickLoop.stopTask();
        this.armorStand.remove();
    }

}
