package deadbycube.world.tile;

import deadbycube.world.object.WorldObject;

public class WorldTile {

    public static final int SIZE = 13;

    private final WorldObject[] worldObjects;

    public WorldTile(WorldObject[] worldObjects) {
        this.worldObjects = worldObjects;
    }

    public WorldObject[] getWorldObjects() {
        return worldObjects;
    }

}
