package deadbycube.player.killer.power.evilwithin;

import deadbycube.player.killer.KillerPlayer;
import deadbycube.player.killer.power.Power;
import deadbycube.util.Progression;
import org.bukkit.boss.BarColor;

abstract class PowerEvilWithin extends Power {

    final Progression progression;

    PowerEvilWithin(KillerPlayer killer, int required) {
        super(killer);

        this.progression = new Progression("power.evil_within.action", BarColor.WHITE);
        this.progression.setMaxValue(required);
    }

    @Override
    public void init() {
        super.init();

        this.progression.display(killer);
    }

    @Override
    public void reset() {
        this.progression.reset();
    }

    abstract void onNextLevel();

}
