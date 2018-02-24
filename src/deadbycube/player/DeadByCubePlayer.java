package deadbycube.player;

import deadbycube.DeadByCube;
import deadbycube.audio.PlayerAudioManager;
import deadbycube.interaction.InteractionManager;
import deadbycube.registry.SkinRegistry;
import deadbycube.util.MagicalValue;
import deadbycube.util.SkinUpdater;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class DeadByCubePlayer {

    protected final DeadByCube plugin;
    protected final Player player;

    protected final PlayerAudioManager audioManager;
    protected final InteractionManager interactionManager;

    protected final MagicalValue walkSpeed;
    protected final String name;
    private final SkinRegistry skin;

    private ItemStack offHandItem;
    private boolean sneaking;

    protected DeadByCubePlayer(DeadByCube plugin, Player player, String name, SkinRegistry skin) {
        this.plugin = plugin;
        this.player = player;
        this.name = name;
        this.skin = skin;

        this.audioManager = new PlayerAudioManager(this);
        this.interactionManager = new InteractionManager(this);

        this.walkSpeed = new MagicalValue(0.2) {
            @Override
            protected void updateValue() {
                super.updateValue();
                //player.sendMessage("Old walkSpeed: " + player.getWalkSpeed());
                //player.sendMessage("New walkSpeed: " + getValue());
                player.setWalkSpeed((float) getValue());
            }
        };
    }

    public abstract PlayerType getType();

    public void init() {
        this.interactionManager.init();
        this.player.getInventory().setHeldItemSlot(0);

        if (skin != null)
            SkinUpdater.setSkin(this, skin.toProperty());
    }

    public void reset() {
        this.interactionManager.reset();
        this.setSneaking(false);
        SkinUpdater.resetSkin(this);
    }

    public void setVisible(boolean visible) {
        Bukkit.getOnlinePlayers().forEach(p -> {
            if (visible)
                p.showPlayer(plugin, player);
            else
                p.hidePlayer(plugin, player);
        });
    }

    public ItemStack getOffHandItem() {
        return offHandItem;
    }

    public void setOffHandItem(ItemStack offHandItem) {
        this.offHandItem = offHandItem;
        this.player.getInventory().setItemInOffHand(offHandItem);
    }

    public void setOffHandItemAmount(int amount) {
        this.offHandItem.setAmount(amount);
        this.player.getInventory().setItemInOffHand(offHandItem);
    }

    public boolean isSneaking() {
        return sneaking;
    }

    public void setSneaking(boolean sneaking) {
        this.sneaking = sneaking;
    }

    public String getName() {
        return name;
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
