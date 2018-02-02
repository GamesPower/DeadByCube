package deadbycube;

import deadbycube.game.GameStatus;
import org.bukkit.World;

public interface DeadByCubeHandler {

    void init();

    void reset();

    GameStatus getStatus();

    World getWorld();

}
