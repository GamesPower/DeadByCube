package deadbycube.command;

import deadbycube.DeadByCube;
import deadbycube.command.exception.CommandParseException;
import deadbycube.command.game.CommandGame;
import deadbycube.command.role.CommandRole;
import deadbycube.command.structure.CommandStructure;
import deadbycube.command.value.CommandDoubleValueHandler;
import deadbycube.command.value.CommandEnumValueHandler;
import deadbycube.command.value.CommandPlayerValueHandler;
import deadbycube.command.value.CommandValueHandler;
import org.bukkit.command.PluginCommand;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class CommandManager {

    private final DeadByCube plugin;
    private final CommandValueHandler[] commandValueHandlers = {
            new CommandDoubleValueHandler(),
            new CommandEnumValueHandler(),
            new CommandPlayerValueHandler()
    };

    public CommandManager(DeadByCube plugin) {
        this.plugin = plugin;
    }


    public void registerCommands() {
        this.register(new CommandGame(this));
        this.register(new CommandStructure(this));
        this.register(new CommandRole(this));
        this.register(new CommandHeartbeat(this));
    }

    @SuppressWarnings("unchecked")
    public boolean isValidValue(Class<?> aClass) {
        for (CommandValueHandler commandValueHandler : commandValueHandlers) {
            if (commandValueHandler.getTClass().isAssignableFrom(aClass))
                return true;
        }
        try {
            aClass.getConstructor(String.class);
            return true;
        } catch (NoSuchMethodException e) {
            return false;
        }
    }

    @SuppressWarnings("unchecked")
    public Object parseValue(Class<?> aClass, String value) throws CommandParseException {
        for (CommandValueHandler commandValueHandler : commandValueHandlers) {
            if (commandValueHandler.getTClass().isAssignableFrom(aClass))
                return commandValueHandler.fromString(aClass, value);
        }
        try {
            Constructor<?> constructor = aClass.getConstructor(String.class);
            return constructor.newInstance(value);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            throw new CommandParseException(e);
        }
    }

    private void register(Command command) {
        PluginCommand pluginCommand = plugin.getCommand(command.getName());
        pluginCommand.setExecutor(command);
        pluginCommand.setTabCompleter(command);
    }

    public DeadByCube getPlugin() {
        return plugin;
    }

}
