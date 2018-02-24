package deadbycube.util;

import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

public class AxisAlignedBB {

    private final Vector min;
    private final Vector max;

    public AxisAlignedBB(Vector min, Vector max) {
        this.min = min;
        this.max = max;
    }

    public static AxisAlignedBB fromEntity(Entity entity) {
        double width = entity.getWidth();
        return new AxisAlignedBB(
                entity.getLocation().subtract(width, 0, width).toVector(),
                entity.getLocation().add(width, entity.getHeight(), width).toVector()
        );
    }

    public boolean isCollide(Vector vector) {
        return vector.isInAABB(min, max);
    }

    public boolean isCollide(AxisAlignedBB aabb) {
        return (min.getX() <= aabb.max.getX() && max.getX() >= aabb.min.getX()) &&
                (min.getY() <= aabb.max.getY() && max.getY() >= aabb.min.getY()) &&
                (min.getZ() <= aabb.max.getZ() && max.getZ() >= aabb.min.getZ());
    }

}
