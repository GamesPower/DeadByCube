package deadbycube.util;

import deadbycube.DeadByCube;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

public class Tickable {

    private static final JavaPlugin JAVA_PLUGIN = JavaPlugin.getPlugin(DeadByCube.class);

    private final long period;
    private final Runnable runnable;

    private BukkitTask task;

    public Tickable(Runnable runnable) {
        this(0L, runnable);
    }

    private Tickable(long period, Runnable runnable) {
        this.period = period;
        this.runnable = runnable;
    }

    public boolean isTicking() {
        return task != null;
    }

    public void startTask() {
        this.stopTask();

        BukkitScheduler scheduler = Bukkit.getScheduler();
        this.task = scheduler.runTaskTimer(JAVA_PLUGIN, runnable, 0L, period);
    }

    public void stopTask() {
        if (task != null) {
            this.task.cancel();
            this.task = null;
        }
    }

}
