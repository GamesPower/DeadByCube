package deadbycube.player.lobby;

import deadbycube.DeadByCube;
import deadbycube.player.DbcPlayer;
import deadbycube.player.PlayerActionHandler;
import deadbycube.player.PlayerType;
import org.bukkit.entity.Player;

public class LobbyPlayer extends DbcPlayer {

    public LobbyPlayer(DeadByCube plugin, Player player) {
        super(plugin, player);
    }

    @Override
    public void init() {
    }

    @Override
    public void reset() {
    }

    @Override
    public PlayerActionHandler createActionHandler() {
        return new LobbyPlayerActionHandler(this);
    }

    @Override
    public PlayerType getType() {
        return PlayerType.LOBBY;
    }

}
