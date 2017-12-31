package deadbycube.player.killer;

import deadbycube.DeadByCube;
import deadbycube.audio.AudioManager;
import deadbycube.audio.SoundRegistry;
import deadbycube.player.killer.power.PowerRegistry;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;

public class KillerDoctor extends Killer {

    public KillerDoctor(DeadByCube plugin, Player player) {
        super(plugin, player, "doctor", PowerRegistry.CARTERS_SPARK);
    }

    @Override
    void update() {

    }
}
