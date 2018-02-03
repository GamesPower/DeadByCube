package deadbycube.util;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;

public class TextComponentUtil {

    private TextComponentUtil() {
    }

    public static TextComponent create(String text, ChatColor color) {
        TextComponent textComponent = new TextComponent(text);
        textComponent.setColor(color);
        return textComponent;
    }

    public static BaseComponent[] buildSuccessComponent(BaseComponent... baseComponents) {
        return new ComponentBuilder("✔")
                .color(ChatColor.GREEN)
                .append(baseComponents)
                .create();
    }

    public static BaseComponent[] buildErrorComponent(BaseComponent... baseComponents) {
        return new ComponentBuilder("✖")
                .color(ChatColor.RED)
                .append(baseComponents)
                .create();
    }

}
