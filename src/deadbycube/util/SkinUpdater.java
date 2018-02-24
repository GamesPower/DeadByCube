package deadbycube.util;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.*;
import deadbycube.player.DeadByCubePlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.HashMap;
import java.util.logging.Level;

public class SkinUpdater {

    private static final PacketType PLAYER_INFO_PACKET_TYPE = PacketType.findCurrent(PacketType.Protocol.PLAY, PacketType.Sender.SERVER, "PlayerInfo");
    private static final PacketType RESPAWN_PACKET_TYPE = PacketType.findCurrent(PacketType.Protocol.PLAY, PacketType.Sender.SERVER, "Respawn");
    private static final PacketType POSITION_PACKET_TYPE = PacketType.findCurrent(PacketType.Protocol.PLAY, PacketType.Sender.SERVER, "Position");

    private static final HashMap<DeadByCubePlayer, WrappedSignedProperty> PLAYER_SKIN_MAP = new HashMap<>();

    private SkinUpdater() {
    }

    public static void setSkin(DeadByCubePlayer deadByCubePlayer, WrappedSignedProperty signedProperty) {
        if (!Bukkit.getOnlineMode())
            return;

        Player player = deadByCubePlayer.getPlayer();

        WrappedGameProfile gameProfile = WrappedGameProfile.fromPlayer(player);
        PLAYER_SKIN_MAP.computeIfAbsent(deadByCubePlayer, p -> gameProfile.getProperties().get("textures").iterator().next());
        gameProfile.getProperties().clear();
        gameProfile.getProperties().put("textures", signedProperty);

        EnumWrappers.NativeGameMode gamemode = EnumWrappers.NativeGameMode.fromBukkit(player.getGameMode());

        PacketContainer removeInfo = new PacketContainer(PLAYER_INFO_PACKET_TYPE);
        removeInfo.getPlayerInfoAction().write(0, EnumWrappers.PlayerInfoAction.REMOVE_PLAYER);

        WrappedChatComponent displayName = WrappedChatComponent.fromText(player.getPlayerListName());
        PlayerInfoData playerInfoData = new PlayerInfoData(gameProfile, 0, gamemode, displayName);
        removeInfo.getPlayerInfoDataLists().write(0, Collections.singletonList(playerInfoData));

        PacketContainer addInfo = new PacketContainer(PLAYER_INFO_PACKET_TYPE);
        addInfo.getPlayerInfoAction().write(0, EnumWrappers.PlayerInfoAction.ADD_PLAYER);
        addInfo.getPlayerInfoDataLists().write(0, Collections.singletonList(playerInfoData));

        EnumWrappers.Difficulty difficulty = EnumWrappers.getDifficultyConverter().getSpecific(player.getWorld().getDifficulty());
        PacketContainer respawn = new PacketContainer(RESPAWN_PACKET_TYPE);
        respawn.getIntegers().write(0, player.getWorld().getEnvironment().getId());
        respawn.getDifficulties().write(0, difficulty);
        respawn.getGameModes().write(0, gamemode);
        respawn.getWorldTypeModifier().write(0, player.getWorld().getWorldType());

        Location location = player.getLocation();

        PacketContainer teleport = new PacketContainer(POSITION_PACKET_TYPE);
        teleport.getModifier().writeDefaults();
        teleport.getDoubles().write(0, location.getX());
        teleport.getDoubles().write(1, location.getY());
        teleport.getDoubles().write(2, location.getZ());
        teleport.getFloat().write(0, location.getYaw());
        teleport.getFloat().write(1, location.getPitch());
        teleport.getIntegers().writeSafely(0, -1337);

        try {
            ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
            protocolManager.sendServerPacket(player, removeInfo);
            protocolManager.sendServerPacket(player, addInfo);
            protocolManager.sendServerPacket(player, respawn);
            protocolManager.sendServerPacket(player, teleport);
        } catch (InvocationTargetException e) {
            Bukkit.getLogger().log(Level.SEVERE, "Unable to send the respawn packet", e);
        }

        deadByCubePlayer.setVisible(false);
        deadByCubePlayer.setVisible(true);
    }

    public static void resetSkin(DeadByCubePlayer deadByCubePlayer) {
        if (!Bukkit.getOnlineMode())
            return;

        WrappedSignedProperty signedProperty = PLAYER_SKIN_MAP.get(deadByCubePlayer);
        if (signedProperty != null)
            SkinUpdater.setSkin(deadByCubePlayer, signedProperty);
    }

}
