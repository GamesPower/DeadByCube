package deadbycube.player;

import deadbycube.DeadByCube;
import deadbycube.audio.PlayerAudioManager;
import org.bukkit.entity.Player;

public abstract class DbcPlayer {

    protected final DeadByCube plugin;
    protected final Player player;

    private final PlayerActionHandler actionHandler;
    private final PlayerAudioManager audioManager;

    protected DbcPlayer(DeadByCube plugin, Player player) {
        this.plugin = plugin;
        this.player = player;

        this.actionHandler = createActionHandler();
        this.audioManager = new PlayerAudioManager(this);
    }

    public abstract void init();

    public abstract void reset();

    public abstract PlayerActionHandler createActionHandler();

    public abstract PlayerType getType();

    public PlayerActionHandler getActionHandler() {
        return actionHandler;
    }

    public PlayerAudioManager getAudioManager() {
        return audioManager;
    }

    public DeadByCube getPlugin() {
        return plugin;
    }

    public Player getPlayer() {
        return player;
    }

}
