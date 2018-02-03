package deadbycube.player.killer.power.evilwithin;

import deadbycube.audio.WorldAudioManager;
import deadbycube.audio.SoundRegistry;
import deadbycube.player.killer.KillerPlayer;
import deadbycube.player.killer.power.PowerRegistry;
import org.bukkit.SoundCategory;

public class PowerEvilWithin1 extends PowerEvilWithin {

    private static final int REQUIRED_STALKED = 100;

    public PowerEvilWithin1(KillerPlayer killer) {
        super(killer, REQUIRED_STALKED);
    }

    @Override
    public void init() {
        super.init();

        killer.getTerrorRadius().setBaseValue(8);
        this.progression.setMaxValue(REQUIRED_STALKED);
    }

    @Override
    void onNextLevel() {
        WorldAudioManager audioManager = killer.getPlugin().getAudioManager();
        audioManager.playSound(SoundRegistry.POWER_EVIL_WITHIN_LEVEL_1, SoundCategory.MASTER);
        killer.setPower(PowerRegistry.EVIL_WITHIN_2);
    }

}