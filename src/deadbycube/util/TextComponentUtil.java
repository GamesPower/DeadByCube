package deadbycube.util;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ComponentBuilder;

public class TextComponentUtil {

    private TextComponentUtil() {
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
