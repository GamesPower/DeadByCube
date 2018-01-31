package deadbycube.audio;

import deadbycube.player.DeadByCubePlayer;
import org.bukkit.Location;
import org.bukkit.SoundCategory;

import java.util.function.Function;

public interface AudioManager {

    void playSound(String sound, SoundCategory category, Location location, float volume, float pitch);

    void playSound(String sound, SoundCategory category, Location location, float volume, float pitch, Function<DeadByCubePlayer, Boolean> function);

    void playSound(String sound, SoundCategory category, float volume, float pitch);

    void playSound(String sound, SoundCategory category, float volume, float pitch, Function<DeadByCubePlayer, Boolean> function);

    void stopSound(String sound);

    default void playSound(String sound, SoundCategory category) {
        this.playSound(sound, category, 1, 1);
    }

    default void playSound(String sound, SoundCategory category, Location location) {
        this.playSound(sound, category, location, 1, 1);
    }

    default void playSound(SoundRegistry sound, SoundCategory category, Location location) {
        this.playSound(sound, category, location, 1, 1);
    }

    default void playSound(SoundRegistry sound, SoundCategory category, Location location, float volume, float pitch) {
        this.playSound(sound.getKey(), category, location, volume, pitch);
    }

    default void playSound(SoundRegistry sound, SoundCategory category, Location location, float volume, float pitch, Function<DeadByCubePlayer, Boolean> function) {
        this.playSound(sound.getKey(), category, location, volume, pitch, function);
    }

    default void playSound(SoundRegistry sound, SoundCategory category) {
        this.playSound(sound.getKey(), category, 1, 1);
    }

    default void playSound(SoundRegistry sound, SoundCategory category, float volume, float pitch) {
        this.playSound(sound.getKey(), category, volume, pitch);
    }

    default void playSound(SoundRegistry sound, SoundCategory category, float volume, float pitch, Function<DeadByCubePlayer, Boolean> function) {
        this.playSound(sound.getKey(), category, volume, pitch, function);
    }

    default void stopSound(SoundRegistry sound) {
        this.stopSound(sound.getKey());
    }

}
