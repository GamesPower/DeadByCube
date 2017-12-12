package deadbycube.item;

import org.apache.commons.lang.ArrayUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemStackBuilder {

    private Material material = Material.STONE;
    private int amount = 1;
    private short damage = 0;
    private byte data = 0;

    private String localizedName = null;
    private boolean unbreakable = false;

    private ItemFlag[] flags = {};

    public ItemStackBuilder setMaterial(Material material) {
        this.material = material;
        return this;
    }

    public ItemStackBuilder setAmount(int amount) {
        this.amount = amount;
        return this;
    }

    public ItemStackBuilder setDamage(short damage) {
        this.damage = damage;
        return this;
    }

    public ItemStackBuilder setData(byte data) {
        this.data = data;
        return this;
    }

    public ItemStackBuilder setLocalizedName(String localizedName) {
        this.localizedName = localizedName;
        return this;
    }

    public ItemStackBuilder setUnbreakable(boolean unbreakable) {
        this.unbreakable = unbreakable;
        return this;
    }

    public ItemStackBuilder setFlags(ItemFlag... flags) {
        this.flags = flags;
        return this;
    }

    public ItemStackBuilder addFlags(ItemFlag flag) {
        this.flags = (ItemFlag[]) ArrayUtils.add(flags, flag);
        return this;
    }

    public ItemStack build() {
        ItemStack itemStack = new ItemStack(material, amount, damage, data);

        ItemMeta itemMeta = itemStack.getItemMeta();

        if (localizedName != null)
            itemMeta.setLocalizedName(localizedName);
        itemMeta.setUnbreakable(unbreakable);
        itemMeta.addItemFlags(flags);

        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

}
