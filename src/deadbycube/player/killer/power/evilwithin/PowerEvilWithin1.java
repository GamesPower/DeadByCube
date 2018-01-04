package deadbycube.player.killer.power.evilwithin;

import deadbycube.audio.WorldAudioManager;
import deadbycube.audio.SoundRegistry;
import deadbycube.player.killer.Killer;
import deadbycube.player.killer.power.PowerRegistry;
import org.bukkit.SoundCategory;

public class PowerEvilWithin1 extends PowerEvilWithin {

    public PowerEvilWithin1(Killer killer) {
        super(killer);
    }

    @Override
    public void init(boolean using) {
        super.init(using);

        killer.getTerrorRadius().setBaseValue(8);
    }

    @Override
    void onStalk() {
        if (stalked == 100) {
            WorldAudioManager audioManager = killer.getPlugin().getAudioManager();
            audioManager.playSound(SoundRegistry.POWER_EVIL_WITHIN_LEVEL_1, SoundCategory.MASTER);
            killer.setPower(PowerRegistry.EVIL_WITHIN_2);
        }
    }

}