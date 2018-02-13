package deadbycube.player.killer.power.evilwithin;

import deadbycube.audio.WorldAudioManager;
import deadbycube.player.killer.KillerPlayer;
import deadbycube.registry.PowerRegistry;
import deadbycube.registry.SoundRegistry;
import org.bukkit.SoundCategory;

public class PowerEvilWithin3 extends PowerEvilWithin {

    public PowerEvilWithin3(KillerPlayer killer) {
        super(killer);
    }

    @Override
    public void init() {
        super.init();

        killer.getTerrorRadius().setBaseValue(32);
    }

    @Override
    public void reset() {
    }

    @Override
    public void onStalk(int stalk) {
        if (stalk == 200) {
            WorldAudioManager audioManager = killer.getPlugin().getAudioManager();
            audioManager.playSound(SoundRegistry.POWER_EVIL_WITHIN_LEVEL_2, SoundCategory.MASTER);
            killer.setPower(PowerRegistry.EVIL_WITHIN_2);
        }
    }

}
