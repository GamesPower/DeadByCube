package deadbycube.audio;

import deadbycube.player.DeadByCubePlayer;
import org.bukkit.Location;
import org.bukkit.SoundCategory;

import java.util.function.Function;

public class PlayerAudioManager implements AudioManager {

    private final DeadByCubePlayer deadByCubePlayer;

    public PlayerAudioManager(DeadByCubePlayer deadByCubePlayer) {
        this.deadByCubePlayer = deadByCubePlayer;
    }

    @Override
    public void playSound(String sound, SoundCategory category, Location location, float volume, float pitch) {
        deadByCubePlayer.getPlayer().playSound(location, sound, category, volume, pitch);
    }

    @Override
    public void playSound(String sound, SoundCategory category, Location location, float volume, float pitch, Function<DeadByCubePlayer, Boolean> function) {
        if (function.apply(deadByCubePlayer))
            this.playSound(sound, category, location, volume, pitch);
    }

    @Override
    public void playSound(String sound, SoundCategory category, float volume, float pitch) {
        this.playSound(sound, category, deadByCubePlayer.getPlayer().getLocation(), volume, pitch);
    }

    @Override
    public void playSound(String sound, SoundCategory category, float volume, float pitch, Function<DeadByCubePlayer, Boolean> function) {
        if (function.apply(deadByCubePlayer))
            this.playSound(sound, category, volume, pitch);
    }

    @Override
    public void stopSound(String sound) {
        deadByCubePlayer.getPlayer().stopSound(sound);
    }


}
