package deadbycube.command.debug;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketContainer;
import deadbycube.command.Command;
import deadbycube.command.CommandManager;
import deadbycube.command.exception.CommandExecutionException;
import deadbycube.command.function.FunctionInfo;
import deadbycube.player.DeadByCubePlayer;
import deadbycube.player.PlayerType;
import deadbycube.player.killer.KillerPlayer;
import deadbycube.player.killer.power.cartersspark.madness.MadnessTier;
import deadbycube.player.killer.power.huntinghatchets.PowerHuntingHatchets;
import deadbycube.player.survivor.SurvivorPlayer;
import deadbycube.registry.PowerRegistry;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

public class CommandDebug extends Command {

    private static final PacketType CHANGE_GAME_STATE_PACKET_TYPE = PacketType.findCurrent(PacketType.Protocol.PLAY, PacketType.Sender.SERVER, "GameStateChange");

    public CommandDebug(CommandManager commandManager) {
        super(commandManager, "debug");
    }

    @FunctionInfo(name = "tickable", requiredArgs = 1)
    private void tickable(CommandSender commandSender) {
        this.sendMessage(commandSender, plugin.getServer().getScheduler().getPendingTasks().toString(), ChatColor.GREEN);
    }

    @FunctionInfo(name = "shader", requiredArgs = 1)
    private void shader(CommandSender commandSender, Float value) throws CommandExecutionException, InvocationTargetException {
        Player player = getPlayer(commandSender);

        PacketContainer changeGameStatePacket = new PacketContainer(CHANGE_GAME_STATE_PACKET_TYPE);
        changeGameStatePacket.getIntegers().write(0, 7);
        changeGameStatePacket.getFloat().write(0, value);

        ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();
        protocolManager.sendServerPacket(player, changeGameStatePacket);
    }

    @FunctionInfo(name = "stun", requiredArgs = 1)
    private void stun(CommandSender commandSender, Integer duration) throws CommandExecutionException {
        Player player = getPlayer(commandSender);
        DeadByCubePlayer deadByCubePlayer = plugin.getPlayerList().getPlayer(player);

        if (deadByCubePlayer.getType() != PlayerType.KILLER)
            return;

        KillerPlayer killer = (KillerPlayer) deadByCubePlayer;
        killer.stun(duration);
    }

    @FunctionInfo(name = "madness", requiredArgs = 1)
    private void madness(CommandSender commandSender, MadnessTier tier, Boolean incremented) throws CommandExecutionException {
        Player player = getPlayer(commandSender);
        DeadByCubePlayer deadByCubePlayer = plugin.getPlayerList().getPlayer(player);

        if (deadByCubePlayer.getType() != PlayerType.SURVIVOR)
            return;

        SurvivorPlayer survivor = (SurvivorPlayer) deadByCubePlayer;
        survivor.getMadnessManager().setTier(tier, incremented);
    }

    @FunctionInfo(name = "reload_hatchets", requiredArgs = 1)
    private void reloadHatchets(CommandSender commandSender) throws CommandExecutionException {
        Player player = getPlayer(commandSender);
        DeadByCubePlayer deadByCubePlayer = plugin.getPlayerList().getPlayer(player);

        if (!PowerRegistry.HUNTING_HATCHETS.hasPower(deadByCubePlayer))
            return;

        KillerPlayer killer = (KillerPlayer) deadByCubePlayer;
        PowerHuntingHatchets power = (PowerHuntingHatchets) killer.getPower();
        power.reloadHatchets();
    }

}
