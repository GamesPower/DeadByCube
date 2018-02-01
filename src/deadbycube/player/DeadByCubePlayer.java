package deadbycube.player;

import deadbycube.DeadByCube;
import deadbycube.audio.PlayerAudioManager;
import deadbycube.interaction.PlayerInteractionManager;
import deadbycube.util.MagicalValue;
import org.bukkit.entity.Player;

public abstract class DeadByCubePlayer {

    protected final DeadByCube plugin;
    protected final Player player;

    private final PlayerActionHandler actionHandler;
    private final PlayerAudioManager audioManager;
    private final PlayerInteractionManager interactionManager;

    private final MagicalValue walkSpeed;

    protected DeadByCubePlayer(DeadByCube plugin, Player player) {
        this.plugin = plugin;
        this.player = player;

        this.actionHandler = createActionHandler();
        this.audioManager = new PlayerAudioManager(this);
        this.interactionManager = new PlayerInteractionManager(this);

        this.walkSpeed = new MagicalValue(0.2) {
            @Override
            protected void updateValue() {
                super.updateValue();
                player.setWalkSpeed((float) getValue());
            }
        };
    }

    public void init() {
    }

    public void reset() {
        this.audioManager.getMusicManager().stopPlaying();
    }

    public abstract PlayerActionHandler createActionHandler();

    public abstract PlayerType getType();

    public void setHidden(boolean hidden) {
        for (DeadByCubePlayer deadByCubePlayer : plugin.getPlayerList().getPlayers()) {
            if (hidden)
                deadByCubePlayer.getPlayer().hidePlayer(plugin, player);
            else
                deadByCubePlayer.getPlayer().showPlayer(plugin, player);
        }
    }

    public MagicalValue walkSpeed() {
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
