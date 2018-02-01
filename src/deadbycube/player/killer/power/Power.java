package deadbycube.player.killer.power;

import deadbycube.player.killer.KillerPlayer;
import deadbycube.util.ItemStackBuilder;
import deadbycube.util.Tickable;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public abstract class Power {

    protected final KillerPlayer killer;
    private final Tickable tickable;

    protected Power(KillerPlayer killer) {
        this.killer = killer;
        this.tickable = new Tickable(this::update);
    }

    public void init(boolean using) {
        PlayerInventory inventory = killer.getPlayer().getInventory();
        ItemStack itemInOffHand = inventory.getItemInOffHand();
        if (itemInOffHand.getType() == Material.BOW) {
            itemInOffHand.setDurability((short) PowerRegistry.getID(this));
        } else {
            inventory.setItemInOffHand(
                    new ItemStackBuilder()
                            .setMaterial(Material.BOW)
                            .setData((byte) PowerRegistry.getID(this))
                            .setFlags(ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ATTRIBUTES)
                            .setUnbreakable(true)
                            .build()
            );
        }
        inventory.setItem(9, new ItemStackBuilder().setMaterial(Material.ARROW).build());

        if (using)
            this.tickable.startTask();
    }

    public void reset() {
        this.tickable.stopTask();
    }

    private void update() {
        if (!isUsing())
            this.stopUse();
        else
            this.onUpdate();
    }

    public abstract boolean canUse();

    protected abstract void onUse();

    protected abstract void onUpdate();

    protected abstract void onStopUse();

    public boolean isUsing() {
        return killer.getPlayer().isHandRaised();
    }

    public void use() {
        this.onUse();
        this.tickable.startTask();
    }

    protected void stopUse() {
        this.tickable.stopTask();
        this.onStopUse();
    }

}
