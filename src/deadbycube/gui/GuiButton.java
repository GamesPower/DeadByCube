package deadbycube.gui;

import deadbycube.DeadByCube;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

class GuiButton {

    final ItemStack itemStack;
    private final GuiButtonAction buttonAction;

    GuiButton(ItemStack itemStack, GuiButtonAction buttonAction) {
        this.itemStack = itemStack;
        this.buttonAction = buttonAction;
    }

    void onClick(DeadByCube plugin, Player player) {
        this.buttonAction.click(plugin, player);
    }

}
