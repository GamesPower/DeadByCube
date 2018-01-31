package deadbycube.player.killer.power.evilwithin;

import deadbycube.audio.SoundRegistry;
import deadbycube.audio.WorldAudioManager;
import deadbycube.player.killer.KillerPlayer;
import deadbycube.player.killer.power.PowerRegistry;
import org.bukkit.SoundCategory;

public class PowerEvilWithin3 extends PowerEvilWithin {

    public PowerEvilWithin3(KillerPlayer killer) {
        super(killer, 200);
    }

    @Override
    public void init(boolean using) {
        super.init(using);

        killer.getTerrorRadius().setBaseValue(32);
    }

    @Override
    void onNextLevel() {
        WorldAudioManager audioManager = killer.getPlugin().getAudioManager();
        audioManager.playSound(SoundRegistry.POWER_EVIL_WITHIN_LEVEL_2, SoundCategory.MASTER);
        killer.setPower(PowerRegistry.EVIL_WITHIN_2);
    }

}
