package deadbycube.player.lobby;

import deadbycube.DeadByCube;
import deadbycube.player.DeadByCubePlayer;
import deadbycube.player.PlayerType;
import org.bukkit.entity.Player;

public class LobbyPlayer extends DeadByCubePlayer {

    protected LobbyPlayer(DeadByCube plugin, Player player) {
        super(plugin, player);
    }

    @Override
    public PlayerType getType() {
        return PlayerType.LOBBY;
    }

}
