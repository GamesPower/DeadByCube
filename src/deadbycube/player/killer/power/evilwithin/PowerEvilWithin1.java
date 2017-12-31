package deadbycube.player.killer.power.evilwithin;

import deadbycube.audio.AudioManager;
import deadbycube.audio.PlayerAudioManager;
import deadbycube.audio.SoundRegistry;
import deadbycube.player.killer.Killer;
import deadbycube.player.killer.power.PowerRegistry;
import org.bukkit.SoundCategory;

public class PowerEvilWithin1 extends PowerEvilWithin {

    public PowerEvilWithin1(Killer killer) {
        super(killer);
    }

    @Override
    void onStalk() {
        if (stalked == 100) {
            AudioManager audioManager = killer.getPlugin().getAudioManager();
            audioManager.playGlobalSound(SoundRegistry.KILLER_SHAPE_STALK_LEVEL_1, SoundCategory.MASTER, killer.getPlayer().getLocation(), 100, 1);
            killer.setPower(PowerRegistry.EVIL_WITHIN_2);
        }
    }

}
