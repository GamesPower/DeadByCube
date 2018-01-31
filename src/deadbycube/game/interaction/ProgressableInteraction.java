package deadbycube.game.interaction;

import deadbycube.player.DeadByCubePlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;

public abstract class ProgressableInteraction extends Interaction {

    private final BossBar bossBar;
    private final double maxProgress;
    private double progress = 0;

    private byte progressSyncTick = 0;

    public ProgressableInteraction(InteractionType type, String name, Location location, double distance, double maxProgress) {
        super(type, name, location, distance);
        this.bossBar = Bukkit.createBossBar("name", BarColor.WHITE, BarStyle.SOLID);
        this.maxProgress = maxProgress;
    }

    protected abstract void onInteractionCompleted();

    @Override
    void update() {
        for (DeadByCubePlayer deadByCubePlayer : players)
            this.addProgress(deadByCubePlayer.repairSpeed().getValue());
        if (++progressSyncTick == 4) {
            this.progressSyncTick = 0;
            this.bossBar.setProgress(progress / maxProgress);
        }
    }

    private void addProgress(double value) {
        this.progress += value;
        if (progress >= maxProgress) {
            this.onInteractionCompleted();
        }
    }

}
