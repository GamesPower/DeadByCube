package deadbycube.player.killer.huntress;

import deadbycube.DeadByCube;
import deadbycube.audio.WorldAudioManager;
import deadbycube.player.killer.interaction.attack.AttackLungeInteraction;
import deadbycube.registry.SoundRegistry;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;

public class AttackHuntressLungeInteraction extends AttackLungeInteraction {

    @Override
    protected void onStopInteract(int tick) {
        super.onStopInteract(tick);

        Player player = interactor.getPlayer();
        DeadByCube plugin = interactor.getPlugin();
        WorldAudioManager audioManager = plugin.getAudioManager();
        audioManager.playSound(SoundRegistry.KILLER_HUNTRESS_ATTACK, SoundCategory.MASTER, player.getLocation());
    }

}
