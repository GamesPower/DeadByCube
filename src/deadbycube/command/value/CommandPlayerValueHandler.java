package deadbycube.command.value;

import deadbycube.command.exception.CommandParseException;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandPlayerValueHandler extends CommandValueHandler<Player> {

    public CommandPlayerValueHandler() {
        super(Player.class);
    }

    @Override
    public Player fromString(Class<Player> playerClass, String value) throws CommandParseException {
        Player player = Bukkit.getPlayer(value);
        if (player == null)
            throw new CommandParseException("Player \"" + value + "\" not found");
        else
            return player;
    }

    @Override
    public List<String> tabComplete(Class<Player> playerClass) {
        ArrayList<String> stringList = new ArrayList<>();
        for (Player player : Bukkit.getOnlinePlayers())
            stringList.add(player.getName());
        return stringList;
    }
}
