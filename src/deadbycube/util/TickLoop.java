package deadbycube.util;

import deadbycube.DeadByCube;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

public class TickLoop {

    private final long period;
    private final Runnable runnable;

    private BukkitTask task;

    public TickLoop(Runnable runnable) {
        this(0L, runnable);
    }

    private TickLoop(long period, Runnable runnable) {
        this.period = period;
        this.runnable = runnable;
    }

    public static BukkitTask runLater(long delay, Runnable runnable) {
        return Bukkit.getScheduler().runTaskLater(DeadByCube.getInstance(), runnable, delay);
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
