package deadbycube.gui;

import deadbycube.item.ItemStackBuilder;
import deadbycube.player.killer.power.PowerRegistry;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

class GuiSelectKiller extends Gui {

    GuiSelectKiller() {
        super("select_killer", 36);

        this.build();
    }

    private void build() {
        set(10, new GuiButton(
                createPowerItemStack(PowerRegistry.BEAR_TRAP),
                (plugin, player) -> player.closeInventory()
        ));
        set(11, new GuiButton(
                createPowerItemStack(PowerRegistry.INVISIBILITY_BELL),
                (plugin, player) -> player.closeInventory()
        ));
        set(12, new GuiButton(
                createPowerItemStack(PowerRegistry.CHAINSAW),
                (plugin, player) -> player.closeInventory()
        ));
        set(13, new GuiButton(
                createPowerItemStack(PowerRegistry.SPENCERS_LAST_BREATH),
                (plugin, player) -> player.closeInventory()
        ));
        set(14, new GuiButton(
                createPowerItemStack(PowerRegistry.HUNTING_HATCHETS),
                (plugin, player) -> player.closeInventory()
        ));
        set(15, new GuiButton(
                createPowerItemStack(PowerRegistry.EVIL_WITHIN_1),
                (plugin, player) -> player.closeInventory()
        ));
        set(16, new GuiButton(
                createPowerItemStack(PowerRegistry.BLACKENED_CATALYST),
                (plugin, player) -> player.closeInventory()
        ));
        set(21, new GuiButton(
                createPowerItemStack(PowerRegistry.CARTERS_SPARK),
                (plugin, player) -> player.closeInventory()
        ));
        set(22, new GuiButton(
                createPowerItemStack(PowerRegistry.BUBBAS_CHAINSAW),
                (plugin, player) -> player.closeInventory()
        ));
        set(23, new GuiButton(
                createPowerItemStack(PowerRegistry.DREAM_MASTER),
                (plugin, player) -> player.closeInventory()
        ));
    }

    private ItemStack createPowerItemStack(PowerRegistry power) {
        return new ItemStackBuilder()
                .setMaterial(Material.DIAMOND_HOE)
                .setData((byte) power.ordinal())
                .setLocalizedName("killer.power." + power.getName())
                .setUnbreakable(true)
                .setFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE)
                .build();
    }

}
