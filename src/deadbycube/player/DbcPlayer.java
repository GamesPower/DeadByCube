package deadbycube.player;

import deadbycube.DeadByCube;
import org.bukkit.entity.Player;

public abstract class DbcPlayer {

    protected final DeadByCube plugin;
    protected final Player player;
    private final PlayerActionHandler actionHandler;

    protected DbcPlayer(DeadByCube plugin, Player player) {
        this.plugin = plugin;
        this.player = player;
        this.actionHandler = createActionHandler();

        this.init();
    }

    protected abstract void init();

    protected abstract void reset();

    public abstract PlayerActionHandler createActionHandler();

    public abstract PlayerType getType();

    public DeadByCube getPlugin() {
        return plugin;
    }

    public Player getPlayer() {
        return player;
    }

    PlayerActionHandler getActionHandler() {
        return actionHandler;
    }

}
