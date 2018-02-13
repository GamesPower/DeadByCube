package deadbycube;

import deadbycube.audio.WorldAudioManager;
import deadbycube.command.CommandManager;
import deadbycube.game.DeadByCubeGame;
import deadbycube.game.GameStatus;
import deadbycube.lobby.DeadByCubeLobby;
import deadbycube.player.PlayerList;
import deadbycube.structure.StructureManager;
import deadbycube.util.NMSUtils;
import org.bukkit.Server;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class DeadByCube extends JavaPlugin {

    private static DeadByCube instance;

    public static DeadByCube getInstance() {
        return instance;
    }

    public static int getCurrentTick() {
        return (int) NMSUtils.getStaticField("MinecraftServer", "currentTick");
    }

    private final PlayerList playerList = new PlayerList();
    private final CommandManager commandManager = new CommandManager(this);
    private final StructureManager structureManager = new StructureManager(this);
    private final WorldAudioManager audioManager = new WorldAudioManager(this);

    private DeadByCubeHandler handler;

    @Override
    public void onEnable() {
        DeadByCube.instance = this;

        this.saveDefaultConfig();

        this.commandManager.registerCommands();

        this.handler = new DeadByCubeLobby(this);
        this.handler.init();

        /* LOBBY SNOW GENERATOR

        Random random = new Random();
        getServer().getScheduler().runTaskTimer(this, () -> {
            for (Player player : Bukkit.getOnlinePlayers()) {
                for (int i = 0; i < 25; i++) {
                    int x = random.nextInt(40) - 20;
                    int y = random.nextInt(18) + 2;
                    int z = random.nextInt(40) - 20;

                    Location location = player.getLocation();
                    player.spawnParticle(
                            Particle.FALLING_DUST,
                            location.getX() + x, player.getWorld().getHighestBlockYAt(x, z) + y, location.getZ() + z, 1,
                            0, 0, 0,
                            1, new MaterialData(Material.SNOW)
                    );
                }

            }
        }, 0L, 0L);*/
    }

    @Override
    public void onDisable() {
        if (handler.getStatus() == GameStatus.IN_GAME) {
            Server server = getServer();
            server.unloadWorld(handler.getWorld(), false);
        }
        this.saveConfig();
    }

    public void startGame() {
        this.setHandler(new DeadByCubeGame(this));
    }

    public void stopGame() {
        this.setHandler(new DeadByCubeLobby(this));
    }

    private void setHandler(DeadByCubeHandler handler) {
        this.handler.reset(handler);
        this.handler = handler;
        this.handler.init();
    }

    public StructureManager getStructureManager() {
        return structureManager;
    }

    public WorldAudioManager getAudioManager() {
        return audioManager;
    }

    public PlayerList getPlayerList() {
        return playerList;
    }

    public DeadByCubeHandler getHandler() {
        return handler;
    }

}
