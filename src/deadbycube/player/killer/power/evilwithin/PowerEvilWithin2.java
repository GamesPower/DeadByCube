package deadbycube.player.killer.power.evilwithin;

import deadbycube.audio.AudioManager;
import deadbycube.audio.PlayerAudioManager;
import deadbycube.audio.SoundRegistry;
import deadbycube.player.killer.Killer;
import deadbycube.player.killer.power.PowerRegistry;
import org.bukkit.SoundCategory;

public class PowerEvilWithin2 extends PowerEvilWithin {

    public PowerEvilWithin2(Killer killer) {
        super(killer);
    }

    @Override
    void onStalk() {
        if (stalked == 100) {
            AudioManager audioManager = killer.getPlugin().getAudioManager();
            audioManager.playGlobalSound(SoundRegistry.KILLER_SHAPE_STALK_LEVEL_3, SoundCategory.MASTER, killer.getPlayer().getLocation(), 100, 1);
            killer.setPower(PowerRegistry.EVIL_WITHIN_3);
        }
    }


}
