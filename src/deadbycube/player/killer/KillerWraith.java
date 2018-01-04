package deadbycube.player.killer;

import deadbycube.DeadByCube;
import deadbycube.audio.WorldAudioManager;
import deadbycube.audio.SoundRegistry;
import deadbycube.player.killer.power.PowerRegistry;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;

public class KillerWraith extends Killer {

    private byte breathTick = 0;

    public KillerWraith(DeadByCube plugin, Player player) {
        super(plugin, player, "wraith", PowerRegistry.INVISIBILITY_BELL);
    }

    @Override
    void update() {
        if (++breathTick == 15) {
            breathTick = 0;
            WorldAudioManager audioManager = plugin.getAudioManager();
            audioManager.playSound(SoundRegistry.KILLER_WRAITH_BREATH, SoundCategory.VOICE, player.getLocation(), .1f, 1);
        }
    }
}
