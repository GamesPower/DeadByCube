package deadbycube.player.killer.power.evilwithin;

import deadbycube.audio.WorldAudioManager;
import deadbycube.player.killer.KillerPlayer;
import deadbycube.registry.PowerRegistry;
import deadbycube.registry.SoundRegistry;
import deadbycube.util.MagicalValue;
import org.bukkit.SoundCategory;

public class PowerEvilWithin1 extends PowerEvilWithin {

    public PowerEvilWithin1(KillerPlayer killer) {
        super(killer);
    }

    @Override
    public void init() {
        super.init();

        killer.getTerrorRadius().setBaseValue(8);
        killer.getLungeDuration().setBaseValue(5);
        killer.getWalkSpeed().setBaseValue(KillerPlayer.WALK_SPEED - (KillerPlayer.WALK_SPEED * (11.5 / 100)));
    }

    @Override
    public void reset() {
    }

    @Override
    public void onStalk(int stalk) {
        if (stalk == 200) {
            WorldAudioManager audioManager = killer.getPlugin().getAudioManager();
            audioManager.playSound(SoundRegistry.POWER_EVIL_WITHIN_LEVEL_1, SoundCategory.MASTER);
            killer.setPower(PowerRegistry.EVIL_WITHIN_2);
        }
    }
}