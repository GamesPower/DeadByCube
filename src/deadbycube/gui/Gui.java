package deadbycube.gui;

import deadbycube.DeadByCube;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;

class Gui {

    private final String name;
    private final int size;
    private final GuiButton[] buttons;

    private Inventory inventory;

    Gui(String name, int size) {
        this.name = name;
        this.size = size;
        this.buttons = new GuiButton[size];
    }

    void set(int index, GuiButton button) {
        if (index < size)
            buttons[index] = button;
        else
            throw new IndexOutOfBoundsException();
    }

    boolean add(GuiButton button) {
        for (int i = 0; i < buttons.length; i++) {
            if (buttons[i] == null) {
                buttons[i] = button;
                return true;
            }
        }
        return false;
    }

    Inventory createInventory() {
        this.inventory = Bukkit.createInventory(null, size, name);
        for (int i = 0; i < buttons.length; i++) {
            GuiButton button = buttons[i];
            if (button != null)
                inventory.setItem(i, button.itemStack);
        }
        return inventory;
    }

    void onClick(DeadByCube plugin, Player player, ClickType click, int slot) {
        if (0 <= slot && slot < buttons.length)
            buttons[slot].onClick(plugin, player);
    }

    Inventory getInventory() {
        return inventory;
    }

}
