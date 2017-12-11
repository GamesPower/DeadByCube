package deadbycube.gui;

import deadbycube.item.ItemStackBuilder;
import deadbycube.player.killer.power.Powers;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

public class GuiSelectKiller extends Gui {

    public GuiSelectKiller() {
        super("select_killer", 36);
    }

    @Override
    public void build() {
        set(10, new GuiButton(
                createPowerItemStack(Powers.TRAP),
                (plugin, player) -> player.closeInventory()
        ));
        set(11, new GuiButton(
                createPowerItemStack(Powers.BELL),
                (plugin, player) -> player.closeInventory()
        ));
        set(12, new GuiButton(
                createPowerItemStack(Powers.CHAINSAW),
                (plugin, player) -> player.closeInventory()
        ));
        set(13, new GuiButton(
                createPowerItemStack(Powers.BREATH),
                (plugin, player) -> player.closeInventory()
        ));
        set(14, new GuiButton(
                createPowerItemStack(Powers.HUNTING_HATCHETS),
                (plugin, player) -> player.closeInventory()
        ));
        set(15, new GuiButton(
                createPowerItemStack(Powers.EVIL_WITHIN_1),
                (plugin, player) -> player.closeInventory()
        ));
        set(16, new GuiButton(
                createPowerItemStack(Powers.BLACKENED_CATALYST),
                (plugin, player) -> player.closeInventory()
        ));
        set(21, new GuiButton(
                createPowerItemStack(Powers.CARTERS_SPARK),
                (plugin, player) -> player.closeInventory()
        ));
        set(22, new GuiButton(
                createPowerItemStack(Powers.BUBBAS_CHAINSAW),
                (plugin, player) -> player.closeInventory()
        ));
        set(23, new GuiButton(
                createPowerItemStack(Powers.DREAM_MASTER),
                (plugin, player) -> player.closeInventory()
        ));
    }

    private ItemStack createPowerItemStack(Powers power) {
        return new ItemStackBuilder()
                .setMaterial(Material.DIAMOND_HOE)
                .setData((byte) power.ordinal())
                .setLocalizedName("killer.power." + power.getName())
                .setUnbreakable(true)
                .setItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE)
                .build();
    }

}
