package deadbycube.player;

import deadbycube.DeadByCube;
import deadbycube.audio.PlayerAudioManager;
import deadbycube.game.interaction.PlayerInteractionManager;
import deadbycube.player.actionhandler.PlayerActionHandler;
import deadbycube.util.EditableValue;
import org.bukkit.entity.Player;

public abstract class DeadByCubePlayer {

    protected final DeadByCube plugin;
    protected final Player player;

    private final PlayerActionHandler actionHandler;
    private final PlayerAudioManager audioManager;
    private final PlayerInteractionManager interactionManager;

    private final EditableValue walkSpeed = new EditableValue(0.2);

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

    public EditableValue getWalkSpeed() {
        return walkSpeed;
    }

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
