package deadbycube.player.killer.power.evilwithin;

import deadbycube.audio.WorldAudioManager;
import deadbycube.registry.SoundRegistry;
import deadbycube.player.killer.KillerPlayer;
import deadbycube.registry.PowerRegistry;
import org.bukkit.SoundCategory;

public class PowerEvilWithin2 extends PowerEvilWithin {

    public PowerEvilWithin2(KillerPlayer killer) {
        super(killer);
    }

    @Override
    public void init() {
        super.init();

        killer.getTerrorRadius().setBaseValue(16);
    }

    @Override
    public void reset() {
        killer.getTerrorRadius().setBaseValue(KillerPlayer.TERROR_RADIUS);
    }

    @Override
    public void onStalk(int stalk) {
        if(stalk == 200) {
            WorldAudioManager audioManager = killer.getPlugin().getAudioManager();
            audioManager.playSound(SoundRegistry.POWER_EVIL_WITHIN_LEVEL_3, SoundCategory.MASTER);
            killer.setPower(PowerRegistry.EVIL_WITHIN_3);
        }
    }

}
