package deadbycube.util;

import deadbycube.DeadByCube;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

public class Tickable {

    private final DeadByCube plugin;

    private BukkitTask task;
    private final Runnable runnable;

    public Tickable(DeadByCube plugin, Runnable runnable) {
        this.plugin = plugin;
        this.runnable = runnable;
    }

    public void startTask() {
        this.stopTask();
        this.task = Bukkit.getScheduler().runTaskTimer(plugin, runnable, 0L, 1L);
    }

    public void stopTask() {
        if (task != null) {
            this.task.cancel();
            this.task = null;
        }
    }

}
