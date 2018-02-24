package deadbycube.util;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

public class ProgressBar {

    private final BossBar bossBar;
    private double maxValue = 1;

    public ProgressBar(String name, BarColor color) {
        this.bossBar = Bukkit.createBossBar(name, color, BarStyle.SOLID);
        this.bossBar.setProgress(0);
    }

    public void display(Player player) {
        this.bossBar.addPlayer(player);
    }

    public void reset(Player player) {
        this.bossBar.removePlayer(player);
    }

    public void reset() {
        this.bossBar.removeAll();
    }

    public void setValue(double value) {
        this.bossBar.setProgress(Math.max(0, Math.min(1, value / maxValue)));
    }

    public void setMaxValue(double maxValue) {
        this.maxValue = maxValue;
    }

    public void setColor(BarColor color) {
        this.bossBar.setColor(color);
    }

    public void setColorFromValue(MagicalValue magicalValue) {
        if (magicalValue.getValue() < magicalValue.getBaseValue())
            this.bossBar.setColor(BarColor.YELLOW);
        if (magicalValue.getValue() > magicalValue.getBaseValue())
            this.bossBar.setColor(BarColor.RED);
        else
            this.bossBar.setColor(BarColor.WHITE);
    }

    public boolean isDisplayed(Player player) {
        return bossBar.getPlayers().contains(player);
    }

}
