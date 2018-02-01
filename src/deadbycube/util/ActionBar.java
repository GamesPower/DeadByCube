package deadbycube.util;

import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import org.bukkit.entity.Player;

public class ActionBar {

    private final String json;

    public ActionBar(BaseComponent... baseComponents) {
        this.json = ComponentSerializer.toString(baseComponents);
    }

    public void send(Player... players) {
        try {
            Object packetPlayOutChat = createPacket();
            for (Player player : players) {
                Object entityPlayer = player.getClass().getMethod("getHandle").invoke(player);
                Object playerConnection = NMSUtils.getField(entityPlayer, "playerConnection");
                playerConnection.getClass().getMethod("sendPacket", NMSUtils.getNMSClass("Packet")).invoke(playerConnection, packetPlayOutChat);
            }
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    private Object createPacket() {
        try {
            Class<?> clsEnumTitleAction = NMSUtils.getNMSClass("PacketPlayOutTitle$EnumTitleAction");
            Object titleAction = clsEnumTitleAction.getMethod("valueOf", String.class).invoke(null, "ACTIONBAR");

            Class<?> clsIChatBaseComponent = NMSUtils.getNMSClass("IChatBaseComponent");
            Object chatBaseComponent = NMSUtils.getNMSClass("IChatBaseComponent$ChatSerializer").getMethod("a", String.class).invoke(null, json);

            return NMSUtils.getNMSClass("PacketPlayOutTitle").getConstructor(clsEnumTitleAction, clsIChatBaseComponent).newInstance(titleAction, chatBaseComponent);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof ActionBar && ((ActionBar) obj).json.equals(json);
    }
}
