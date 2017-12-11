package deadbycube.command;

import deadbycube.DeadByCube;
import deadbycube.gui.GuiManager;
import deadbycube.gui.GuiSelectKiller;
import deadbycube.gui.GuiSelectRole;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class CommandGui extends AbstractCommand {

    public CommandGui(DeadByCube plugin) {
        super(plugin, "gui");
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        if (commandSender instanceof Player) {
            GuiManager guiManager = plugin.getGuiManager();

            switch (args[0]) {
                case "select_role":
                    guiManager.openGui(new GuiSelectRole(), (Player) commandSender);
                    break;
                case "select_killer":
                    guiManager.openGui(new GuiSelectKiller(), (Player) commandSender);
                    break;
                default:
                    return false;
            }
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        return Arrays.asList("select_role", "select_killer");
    }
}
