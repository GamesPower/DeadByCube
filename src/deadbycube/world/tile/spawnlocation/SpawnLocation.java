package deadbycube.world.tile.spawnlocation;

public class SpawnLocation {

    private final byte x, y, z;

    SpawnLocation(byte x, byte y, byte z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public byte getX() {
        return x;
    }

    public byte getY() {
        return y;
    }

    public byte getZ() {
        return z;
    }

}
