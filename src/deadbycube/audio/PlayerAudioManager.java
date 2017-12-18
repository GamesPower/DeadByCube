package deadbycube.audio;

import deadbycube.player.DbcPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class PlayerAudioManager {

    private final DbcPlayer dbcPlayer;

    public PlayerAudioManager(DbcPlayer dbcPlayer) {
        this.dbcPlayer = dbcPlayer;
    }

    public void playSound(DbcSounds sound, Location location, float volume, float pitch) {
        dbcPlayer.getPlayer().playSound(location, sound.getKey(), volume, pitch);
    }

    public void playSoundLater(DbcSounds sound, Location location, float volume, float pitch, long delay) {
        Bukkit.getScheduler().runTaskLater(
                dbcPlayer.getPlugin(),
                () -> playSound(sound, location, volume, pitch),
                delay
        );
    }

}
