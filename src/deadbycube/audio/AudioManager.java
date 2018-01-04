package deadbycube.audio;

import deadbycube.player.DeadByCubePlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;

import java.util.function.Function;

public interface AudioManager {

    default void playSound(SoundRegistry sound, SoundCategory category, Location location) {
        this.playSound(sound, category, location, 1, 1);
    }

    void playSound(SoundRegistry sound, SoundCategory category, Location location, float volume, float pitch);

    void playSound(SoundRegistry sound, SoundCategory category, Location location, float volume, float pitch, Function<DeadByCubePlayer, Boolean> function);

    default void playSound(SoundRegistry sound, SoundCategory category) {
        this.playSound(sound, category, 1, 1);
    }

    void playSound(SoundRegistry sound, SoundCategory category, float volume, float pitch);

    void playSound(SoundRegistry sound, SoundCategory category, float volume, float pitch, Function<DeadByCubePlayer, Boolean> function);

    void playSoundLater(SoundRegistry sound, SoundCategory category, Location location, float volume, float pitch, long delay);

    void stopSound(SoundRegistry sound);

}
