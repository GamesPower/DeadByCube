package deadbycube.audio;

import deadbycube.DeadByCube;
import deadbycube.player.DeadByCubePlayer;
import deadbycube.player.PlayerType;
import org.bukkit.Location;
import org.bukkit.SoundCategory;

import java.util.function.Function;

public class WorldAudioManager implements AudioManager {

    private final DeadByCube plugin;

    public WorldAudioManager(DeadByCube plugin) {
        this.plugin = plugin;
    }

    @Override
    public void playSound(String sound, SoundCategory category, Location location, float volume, float pitch) {
        for (DeadByCubePlayer deadByCubePlayer : plugin.getPlayerList().getPlayers())
            if (deadByCubePlayer.getType() != PlayerType.SPECTATOR)
                deadByCubePlayer.getAudioManager().playSound(sound, category, location, volume, pitch);
    }

    @Override
    public void playSound(String sound, SoundCategory category, Location location, float volume, float pitch, Function<DeadByCubePlayer, Boolean> function) {
        for (DeadByCubePlayer deadByCubePlayer : plugin.getPlayerList().getPlayers())
            if (function.apply(deadByCubePlayer) && deadByCubePlayer.getType() != PlayerType.SPECTATOR)
                deadByCubePlayer.getAudioManager().playSound(sound, category, location, volume, pitch);
    }

    @Override
    public void playSound(String sound, SoundCategory category, float volume, float pitch) {
        for (DeadByCubePlayer deadByCubePlayer : plugin.getPlayerList().getPlayers())
            if (deadByCubePlayer.getType() != PlayerType.SPECTATOR)
                deadByCubePlayer.getAudioManager().playSound(sound, category, deadByCubePlayer.getPlayer().getLocation(), volume, pitch);
    }

    @Override
    public void playSound(String sound, SoundCategory category, float volume, float pitch, Function<DeadByCubePlayer, Boolean> function) {
        for (DeadByCubePlayer deadByCubePlayer : plugin.getPlayerList().getPlayers())
            if (function.apply(deadByCubePlayer) && deadByCubePlayer.getType() != PlayerType.SPECTATOR)
                deadByCubePlayer.getAudioManager().playSound(sound, category, deadByCubePlayer.getPlayer().getLocation(), volume, pitch);
    }

    @Override
    public void stopSound(String sound) {
        for (DeadByCubePlayer deadByCubePlayer : plugin.getPlayerList().getPlayers()) {
            deadByCubePlayer.getAudioManager().stopSound(sound);
        }
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
