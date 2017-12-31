package deadbycube.player;

import deadbycube.DeadByCube;
import deadbycube.audio.PlayerAudioManager;
import deadbycube.game.interaction.PlayerInteractionManager;
import deadbycube.player.actionhandler.PlayerActionHandler;
import org.bukkit.entity.Player;

public abstract class DeadByCubePlayer {

    protected final DeadByCube plugin;
    protected final Player player;

    private final PlayerActionHandler actionHandler;
    private final PlayerAudioManager audioManager;
    private final PlayerInteractionManager interactionManager;

    protected DeadByCubePlayer(DeadByCube plugin, Player player) {
        this.plugin = plugin;
        this.player = player;

        this.actionHandler = createActionHandler();
        this.audioManager = new PlayerAudioManager(this);
        this.interactionManager = new PlayerInteractionManager(this);
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

    public PlayerInteractionManager getInteractionManager() {
        return interactionManager;
    }

    public Player getPlayer() {
        return player;
    }

    public DeadByCube getPlugin() {
        return plugin;
    }

}
