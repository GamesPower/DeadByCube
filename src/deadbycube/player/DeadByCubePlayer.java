package deadbycube.player;

import deadbycube.DeadByCube;
import deadbycube.audio.PlayerAudioManager;
import deadbycube.interaction.InteractionManager;
import deadbycube.util.MagicalValue;
import org.bukkit.entity.Player;

public abstract class DeadByCubePlayer {

    protected final DeadByCube plugin;
    protected final Player player;

    protected final PlayerAudioManager audioManager;
    protected final InteractionManager interactionManager;
    protected final MagicalValue walkSpeed;

    private boolean sneaking;

    protected DeadByCubePlayer(DeadByCube plugin, Player player) {
        this.plugin = plugin;
        this.player = player;

        this.audioManager = new PlayerAudioManager(this);
        this.interactionManager = new InteractionManager(this);

        this.walkSpeed = new MagicalValue(0.2) {
            @Override
            protected void updateValue() {
                super.updateValue();
                /*player.sendMessage("Old walkSpeed: " + player.getWalkSpeed());
                player.sendMessage("New walkSpeed: " + getValue());*/
                player.setWalkSpeed((float) getValue());
            }
        };
    }

    public void init() {
        this.interactionManager.init();
    }

    public void reset() {
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

    public InteractionManager getInteractionManager() {
        return interactionManager;
    }

    public Player getPlayer() {
        return player;
    }

    public DeadByCube getPlugin() {
        return plugin;
    }


}
