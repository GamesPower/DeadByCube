package deadbycube.util;

import deadbycube.DeadByCube;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;

public class Tickable {

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
        this.task = Bukkit.getScheduler().runTaskTimer(DeadByCube.getInstance(), runnable, 0L, period);
    }

    public void stopTask() {
        if (task != null) {
            this.task.cancel();
            this.task = null;
        }
    }

}
