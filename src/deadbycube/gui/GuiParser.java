package deadbycube.gui;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import deadbycube.util.ItemStackBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class GuiParser {

    private JsonObject guiObject;

    private GuiParser(JsonObject guiObject) {
        this.guiObject = guiObject;
    }

    public static Gui parse(InputStream inputStream) throws GuiException {
        JsonElement jsonElement = new JsonParser().parse(new InputStreamReader(inputStream));
        if (!jsonElement.isJsonObject())
            throw new GuiException("Invalid gui root");
        return new GuiParser(jsonElement.getAsJsonObject()).parse();
    }

    private Gui parse() throws GuiException {
        JsonElement idElement = guiObject.get("id");
        JsonElement sizeElement = guiObject.get("size");

        if (idElement == null || !idElement.isJsonPrimitive())
            throw new GuiException("Invalid or missing id field");
        if (sizeElement == null || !sizeElement.isJsonPrimitive())
            throw new GuiException("Invalid or missing size field");


        Gui gui = new Gui(
                idElement.getAsString(),
                sizeElement.getAsInt()
        );

        JsonElement buttonsElement = guiObject.get("buttons");
        if (buttonsElement == null || !buttonsElement.isJsonArray())
            throw new GuiException("Missing or invalid buttons array");

        for (JsonElement buttonElement : buttonsElement.getAsJsonArray()) {
            if (!buttonElement.isJsonObject())
                throw new GuiException("Invalid button element");
            JsonObject buttonObject = buttonElement.getAsJsonObject();

            JsonElement positionElement = buttonObject.get("position");
            if (positionElement == null || !positionElement.isJsonPrimitive() || !positionElement.getAsJsonPrimitive().isNumber())
                throw new GuiException("Missing or invalid button object");

            int position = positionElement.getAsInt();

            JsonElement itemStackElement = buttonObject.get("item_stack");
            if (itemStackElement == null || !itemStackElement.isJsonObject())
                throw new GuiException("Missing or invalid item stack object");

            gui.set(position, new GuiButton(
                    parseItemStack(itemStackElement.getAsJsonObject())
                    , (plugin, player) -> player.closeInventory()
            ));
        }

        return gui;
    }

    private ItemStack parseItemStack(JsonObject itemStackObject) throws GuiException {
        JsonElement materialElement = itemStackObject.get("material");
        if (materialElement == null || !materialElement.isJsonPrimitive() || !materialElement.getAsJsonPrimitive().isString())
            throw new GuiException("Missing material");

        Material material = Material.getMaterial(materialElement.getAsString());
        int amount = 1;
        byte data = 0;
        String localizedName = null;
        boolean unbreakable = false;
        ArrayList<ItemFlag> flagList = new ArrayList<>();

        if (material == null)
            throw new GuiException("Invalid material");

        JsonElement dataElement = itemStackObject.get("data");
        if (dataElement != null)
            data = dataElement.getAsByte();

        JsonElement localizedNameElement = itemStackObject.get("localized_name");
        if (localizedNameElement != null)
            localizedName = localizedNameElement.getAsString();

        JsonElement unbreakableElement = itemStackObject.get("unbreakable");
        if (unbreakableElement != null)
            unbreakable = unbreakableElement.getAsBoolean();

        JsonElement flagsElement = itemStackObject.get("flags");
        if (flagsElement != null && flagsElement.isJsonArray())
            for (JsonElement flagElement : flagsElement.getAsJsonArray())
                flagList.add(ItemFlag.valueOf(flagElement.getAsString()));

        return new ItemStackBuilder(material)
                .setAmount(amount)
                .setData(data)
                .setLocalizedName(localizedName)
                .setUnbreakable(unbreakable)
                .setFlags(flagList.toArray(new ItemFlag[flagList.size()]))
                .build();
    }

}
