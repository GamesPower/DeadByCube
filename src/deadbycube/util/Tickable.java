package deadbycube.util;

import deadbycube.DeadByCube;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

public class Tickable {

    private final DeadByCube plugin;
    private final Runnable runnable;

    private BukkitTask task;

    public Tickable(DeadByCube plugin, Runnable runnable) {
        this.plugin = plugin;
        this.runnable = runnable;
    }

    public void startTask() {
        this.stopTask();

        BukkitScheduler scheduler = plugin.getServer().getScheduler();
        this.task = scheduler.runTaskTimer(plugin, runnable, 0L, 0L);
    }

    public void stopTask() {
        if (task != null) {
            this.task.cancel();
            this.task = null;
        }
    }

}
