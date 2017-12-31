package deadbycube.audio;

import deadbycube.player.DeadByCubePlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.SoundCategory;

public class PlayerAudioManager {

    private final DeadByCubePlayer deadByCubePlayer;

    public PlayerAudioManager(DeadByCubePlayer deadByCubePlayer) {
        this.deadByCubePlayer = deadByCubePlayer;
    }

    public void playSound(SoundRegistry sound, SoundCategory category, Location location, float volume, float pitch) {
        deadByCubePlayer.getPlayer().playSound(location, sound.getKey(), category, volume, pitch);
    }

    public void playSoundLater(SoundRegistry sound, SoundCategory category, Location location, float volume, float pitch, long delay) {
        Bukkit.getScheduler().runTaskLater(
                deadByCubePlayer.getPlugin(),
                () -> playSound(sound, category, location, volume, pitch),
                delay
        );
    }

    public void stopSound(SoundRegistry sound) {
        deadByCubePlayer.getPlayer().stopSound(sound.getKey());
    }

}
