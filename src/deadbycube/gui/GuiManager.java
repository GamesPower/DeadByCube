package deadbycube.gui;

import deadbycube.DeadByCube;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;

public class GuiManager implements Listener {

    private final DeadByCube plugin;
    private final HashMap<Player, Gui> playerGuiMap = new HashMap<>();

    public GuiManager(DeadByCube plugin) {
        this.plugin = plugin;
    }

    public void openGui(Gui gui, Player player) {
        playerGuiMap.put(player, gui);
        player.openInventory(gui.createInventory());
    }

    public void closeGui(Player player) {
        playerGuiMap.remove(player);
        player.closeInventory();
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Gui playerGui = playerGuiMap.get(player);
        Inventory inventory = event.getInventory();

        if (playerGui != null && inventory.equals(playerGui.getInventory())) {

            playerGui.onClick(plugin, player, event.getClick(), event.getSlot());
            event.setCancelled(true);

        }
    }


}
