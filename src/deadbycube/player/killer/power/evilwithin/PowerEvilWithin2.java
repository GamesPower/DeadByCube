package deadbycube.player.killer.power.evilwithin;

import deadbycube.audio.WorldAudioManager;
import deadbycube.audio.SoundRegistry;
import deadbycube.player.killer.KillerPlayer;
import deadbycube.player.killer.power.PowerRegistry;
import org.bukkit.SoundCategory;

public class PowerEvilWithin2 extends PowerEvilWithin {

    public PowerEvilWithin2(KillerPlayer killer) {
        super(killer, 100);
    }

    @Override
    public void init() {
        super.init();

        killer.getTerrorRadius().setBaseValue(16);
    }

    @Override
    void onNextLevel() {
        WorldAudioManager audioManager = killer.getPlugin().getAudioManager();
        audioManager.playSound(SoundRegistry.POWER_EVIL_WITHIN_LEVEL_3, SoundCategory.MASTER);
        killer.setPower(PowerRegistry.EVIL_WITHIN_3);
    }

}
