package deadbycube.player.killer.power;

import deadbycube.player.killer.KillerPlayer;
import deadbycube.registry.PowerRegistry;
import deadbycube.util.ItemStackBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public abstract class Power {

    protected final KillerPlayer killer;

    protected Power(KillerPlayer killer) {
        this.killer = killer;
    }

    public void init() {
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
    }

    public abstract void reset();

    public KillerPlayer getKiller() {
        return killer;
    }

}
