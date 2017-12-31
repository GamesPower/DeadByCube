package deadbycube.audio;

import deadbycube.DeadByCube;
import deadbycube.player.DeadByCubePlayer;
import deadbycube.player.PlayerType;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.SoundCategory;
import org.bukkit.scheduler.BukkitScheduler;

public class AudioManager {

    private final DeadByCube plugin;

    public AudioManager(DeadByCube plugin) {
        this.plugin = plugin;
    }

    public void stopSound(SoundRegistry sound) {
        for (DeadByCubePlayer deadByCubePlayer : plugin.getPlayerList().getPlayers()) {
            deadByCubePlayer.getAudioManager().stopSound(sound);
        }
    }

    public void playGlobalSoundLater(SoundRegistry sound, SoundCategory category, Location location, float volume, float pitch, long delay) {
        Server server = plugin.getServer();
        BukkitScheduler scheduler = server.getScheduler();
        scheduler.runTaskLater(plugin, () -> playGlobalSound(sound, category, location, volume, pitch), delay);
    }

    public void playGlobalSound(SoundRegistry sound, SoundCategory category, Location location, float volume, float pitch) {
        for (DeadByCubePlayer deadByCubePlayer : plugin.getPlayerList().getPlayers())
            if (deadByCubePlayer.getType() != PlayerType.SPECTATOR)
                deadByCubePlayer.getAudioManager().playSound(sound, category, location, volume, pitch);
    }

    public void playKillerSound(SoundRegistry sound, SoundCategory category, Location location, float volume, float pitch) {
        for (DeadByCubePlayer deadByCubePlayer : plugin.getPlayerList().getKillers())
            deadByCubePlayer.getAudioManager().playSound(sound, category, location, volume, pitch);
    }

    public void playSurvivorSound(SoundRegistry sound, SoundCategory category, Location location, float volume, float pitch) {
        for (DeadByCubePlayer deadByCubePlayer : plugin.getPlayerList().getSurvivors())
            deadByCubePlayer.getAudioManager().playSound(sound, category, location, volume, pitch);
    }

}
