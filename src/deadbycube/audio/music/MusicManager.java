package deadbycube.audio.music;

import deadbycube.audio.PlayerAudioManager;
import deadbycube.player.DeadByCubePlayer;
import org.bukkit.Bukkit;
import org.bukkit.SoundCategory;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MusicManager {

    private static final Random RANDOM = new Random();

    private final DeadByCubePlayer deadByCubePlayer;

    private List<MusicRegistry> availableMusicList;
    private MusicRegistry currentMusic;
    private BukkitTask bukkitTask;

    public MusicManager(DeadByCubePlayer deadByCubePlayer) {
        this.deadByCubePlayer = deadByCubePlayer;
    }

    private void startPlaying() {
        this.currentMusic = availableMusicList.get(RANDOM.nextInt(availableMusicList.size()));
        PlayerAudioManager audioManager = deadByCubePlayer.getAudioManager();
        audioManager.playSound(currentMusic.getKey(), SoundCategory.MUSIC, 1000f, 1f);
        this.bukkitTask = Bukkit.getScheduler().runTaskLater(deadByCubePlayer.getPlugin(), this::startPlaying, currentMusic.getDuration());
    }

    public void stopPlaying() {
        if (currentMusic != null && bukkitTask != null) {
            PlayerAudioManager audioManager = deadByCubePlayer.getAudioManager();
            audioManager.stopSound(currentMusic.getKey());
            this.bukkitTask.cancel();
        }
    }

    public void setMusics(MusicRegistry... musics) {
        this.stopPlaying();
        this.availableMusicList = Arrays.asList(musics);
        this.startPlaying();
    }

}
