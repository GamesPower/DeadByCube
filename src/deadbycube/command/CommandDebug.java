package deadbycube.command;

import deadbycube.command.function.FunctionInfo;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.CommandSender;

class CommandDebug extends Command {

    CommandDebug(CommandManager commandManager) {
        super(commandManager, "debug");
    }

    @FunctionInfo(name = "tickable", requiredArgs = 1)
    private void tickable(CommandSender commandSender) {
        this.sendMessage(commandSender, plugin.getServer().getScheduler().getPendingTasks().toString(), ChatColor.GREEN);
    }

}
