package deadbycube.audio;

import deadbycube.DeadByCube;
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
    public void playSound(SoundRegistry sound, SoundCategory category, Location location, float volume, float pitch) {
        deadByCubePlayer.getPlayer().playSound(location, sound.getKey(), category, volume, pitch);
    }

    @Override
    public void playSound(SoundRegistry sound, SoundCategory category, Location location, float volume, float pitch, Function<DeadByCubePlayer, Boolean> function) {
        if (function.apply(deadByCubePlayer))
            this.playSound(sound, category, location, volume, pitch);
    }

    @Override
    public void playSound(SoundRegistry sound, SoundCategory category, float volume, float pitch) {
        this.playSound(sound, category, deadByCubePlayer.getPlayer().getLocation(), volume, pitch);
    }

    @Override
    public void playSound(SoundRegistry sound, SoundCategory category, float volume, float pitch, Function<DeadByCubePlayer, Boolean> function) {
        if (function.apply(deadByCubePlayer))
            this.playSound(sound, category, volume, pitch);
    }

    @Override
    public void playSoundLater(SoundRegistry sound, SoundCategory category, Location location, float volume, float pitch, long delay) {
        DeadByCube plugin = deadByCubePlayer.getPlugin();
        plugin.getServer().getScheduler().runTaskLater(plugin, () -> this.playSound(sound, category, location, volume, pitch), delay);
    }

    @Override
    public void stopSound(SoundRegistry sound) {
        deadByCubePlayer.getPlayer().stopSound(sound.getKey());
    }

}
