package deadbycube;

import deadbycube.game.GameStatus;
import org.bukkit.World;

public interface DeadByCubeHandler {

    void init();

    void reset(DeadByCubeHandler newHandler);

    GameStatus getStatus();

    World getWorld();

}
