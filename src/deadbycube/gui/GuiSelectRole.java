package deadbycube.gui;

import deadbycube.item.ItemStackBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;

public class GuiSelectRole extends Gui {

    public GuiSelectRole() {
        super("select_role", 27);

        this.build();
    }

    private void build() {
        set(12, new GuiButton(new ItemStackBuilder()
                .setMaterial(Material.GOLD_HOE)
                .setData((byte) 0)
                .setLocalizedName("role.killer.name")
                .setUnbreakable(true)
                .setFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE)
                .build()
                , (plugin, player) -> player.closeInventory()));
        set(14, new GuiButton(new ItemStackBuilder()
                .setMaterial(Material.GOLD_HOE)
                .setData((byte) 1)
                .setLocalizedName("role.survivor.name")
                .setUnbreakable(true)
                .setFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE)
                .build()
                , (plugin, player) -> player.closeInventory()));
    }

}
