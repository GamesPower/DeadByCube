package deadbycube.player.spectator;

import deadbycube.DeadByCube;
import deadbycube.player.DeadByCubePlayer;
import deadbycube.player.PlayerType;
import deadbycube.player.PlayerActionHandler;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class SpectatorPlayer extends DeadByCubePlayer {

    public SpectatorPlayer(DeadByCube plugin, Player player) {
        super(plugin, player);
    }

    @Override
    public void init() {
        super.init();
        player.setGameMode(GameMode.SPECTATOR);
    }

    @Override
    public void reset() {
        super.reset();
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
