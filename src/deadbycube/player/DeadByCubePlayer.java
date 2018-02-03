package deadbycube.player;

import deadbycube.DeadByCube;
import deadbycube.audio.PlayerAudioManager;
import deadbycube.interaction.PlayerInteractionManager;
import deadbycube.util.MagicalValue;
import org.bukkit.entity.Player;

public abstract class DeadByCubePlayer {

    protected final DeadByCube plugin;
    protected final Player player;

    private final PlayerAudioManager audioManager;
    private final PlayerInteractionManager interactionManager;
    private final MagicalValue walkSpeed;

    private boolean sneaking;

    protected DeadByCubePlayer(DeadByCube plugin, Player player) {
        this.plugin = plugin;
        this.player = player;

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
        this.interactionManager.init();
    }

    public void reset() {
        this.audioManager.getMusicManager().stopPlaying();
        this.interactionManager.reset();

        this.setSneaking(false);
        this.setHidden(false);
    }

    public abstract PlayerType getType();

    public boolean isSneaking() {
        return sneaking;
    }

    public void setSneaking(boolean sneaking) {
        this.sneaking = sneaking;
    }

    public void setHidden(boolean hidden) {
        for (DeadByCubePlayer deadByCubePlayer : plugin.getPlayerList().getPlayers()) {
            if (hidden)
                deadByCubePlayer.getPlayer().hidePlayer(plugin, player);
            else
                deadByCubePlayer.getPlayer().showPlayer(plugin, player);
        }
    }

    public MagicalValue getWalkSpeed() {
        return walkSpeed;
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
