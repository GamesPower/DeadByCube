package deadbycube.player.killer;

import deadbycube.DeadByCube;
import deadbycube.audio.WorldAudioManager;
import deadbycube.audio.SoundRegistry;
import deadbycube.player.killer.power.PowerRegistry;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;

public class KillerTrapper extends Killer {

    private byte breathTick = 0;

    public KillerTrapper(DeadByCube plugin, Player player) {
        super(plugin, player, "trapper", PowerRegistry.BEAR_TRAP);
    }

    @Override
    void update() {
        if (++breathTick == 35) {
            breathTick = 0;
            WorldAudioManager audioManager = plugin.getAudioManager();
            audioManager.playSound(SoundRegistry.KILLER_TRAPPER_BREATH, SoundCategory.VOICE, player.getLocation(), .1f, 1);
        }
    }

}
