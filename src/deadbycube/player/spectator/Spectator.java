package deadbycube.player.spectator;

import deadbycube.DeadByCube;
import deadbycube.player.DeadByCubePlayer;
import deadbycube.player.PlayerType;
import deadbycube.player.actionhandler.PlayerActionHandler;
import deadbycube.player.actionhandler.SpectatorActionHandler;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class Spectator extends DeadByCubePlayer {

    public Spectator(DeadByCube plugin, Player player) {
        super(plugin, player);
    }

    @Override
    public void init() {
        player.setGameMode(GameMode.SPECTATOR);
    }

    @Override
    public void reset() {
        player.setGameMode(GameMode.ADVENTURE);
    }

    @Override
    public PlayerActionHandler createActionHandler() {
        return new SpectatorActionHandler(this);
    }

    @Override
    public PlayerType getType() {
        return PlayerType.SPECTATOR;
    }

}
