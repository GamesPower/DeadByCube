package deadbycube.util;

import deadbycube.player.DeadByCubePlayer;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;

public class Progression {

    private final BossBar bossBar;
    private double maxValue = 1;

    public Progression(String name, BarColor color) {
        this.bossBar = Bukkit.createBossBar(name, color, BarStyle.SOLID);
        this.bossBar.setProgress(0);
    }

    public void display(DeadByCubePlayer player) {
        this.bossBar.addPlayer(player.getPlayer());
    }

    public void reset(DeadByCubePlayer player) {
        this.bossBar.removePlayer(player.getPlayer());
    }

    public void reset() {
        this.bossBar.removeAll();
    }

    public void setValue(double value) {
        this.bossBar.setProgress(value / maxValue); //Math.min(1, (value / maxValue) + ((1 / maxValue) * 0.20))
    }

    public void setMaxValue(double maxValue) {
        this.maxValue = maxValue;
    }
}
