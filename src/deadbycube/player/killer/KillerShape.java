package deadbycube.player.killer;

import deadbycube.DeadByCube;
import deadbycube.audio.AudioManager;
import deadbycube.audio.SoundRegistry;
import deadbycube.player.killer.power.PowerRegistry;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;

public class KillerShape extends Killer {

    private byte lastBreath = 0;

    public KillerShape(DeadByCube plugin, Player player) {
        super(plugin, player, "shape", PowerRegistry.EVIL_WITHIN_1);
    }

    @Override
    void update() {
        if (++lastBreath == 65) {
            lastBreath = 0;
            AudioManager audioManager = plugin.getAudioManager();
            audioManager.playGlobalSound(SoundRegistry.KILLER_SHAPE_BREATH, SoundCategory.VOICE, player.getLocation(), .2f, 1);
        }
    }

}
