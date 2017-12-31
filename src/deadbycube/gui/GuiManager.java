package deadbycube.gui;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import deadbycube.DeadByCube;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

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

    public Gui parse(InputStream inputStream) throws Exception {
        JsonElement rootElement = new JsonParser().parse(new InputStreamReader(inputStream));

        if (!rootElement.isJsonObject())
            throw new GuiException("Invalid root element");

        JsonObject rootObject = rootElement.getAsJsonObject();

        Gui gui = new Gui(
                rootObject.get("name").getAsString(),
                rootObject.get("size").getAsInt()
        );

        JsonElement buttonsElement = rootObject.get("buttons");
        if (!buttonsElement.isJsonObject())
            throw new GuiException("Invalid buttons element");
        JsonArray buttonsArray = buttonsElement.getAsJsonArray();

        for (JsonElement buttonElement : buttonsArray) {
            if (!buttonElement.isJsonObject())
                throw new GuiException("Invalid button element");
            JsonObject buttonObject = buttonElement.getAsJsonObject();

            int position = buttonObject.get("position").getAsInt();
            JsonElement itemElement = buttonObject.get("item");

            if(!itemElement.isJsonObject())
                throw new GuiException("Invalid item element");

            JsonObject itemObject = itemElement.getAsJsonObject();

            Map<String, Object> itemPropertyMap = itemObject.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey,
                    Map.Entry::getValue, (a,b)->b));
            ItemStack itemStack = ItemStack.deserialize(itemPropertyMap);

            gui.set(position, new GuiButton(itemStack, (plugin1, player) -> player.closeInventory()));
        }

        return gui;
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
