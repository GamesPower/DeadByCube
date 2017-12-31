package deadbycube.player.survivor;

import deadbycube.DeadByCube;
import deadbycube.audio.PlayerAudioManager;
import deadbycube.audio.SoundRegistry;
import deadbycube.player.killer.Killer;
import deadbycube.util.Tickable;
import org.bukkit.Location;
import org.bukkit.SoundCategory;

public class HeartbeatManager {

    private final Survivor survivor;
    private final Tickable tickable;

    private int delay = -1;
    private float volume = 1f;

    HeartbeatManager(Survivor survivor) {
        this.survivor = survivor;
        this.tickable = new Tickable(survivor.getPlugin(), this::update);
    }

    public void init() {
        this.tickable.startTask();
    }

    public void reset() {
        this.tickable.stopTask();
    }

    private void update() {
        if (delay >= 0)
            delay--;
        else {
            DeadByCube plugin = survivor.getPlugin();
            Location survivorLocation = survivor.getPlayer().getLocation();

            for (Killer killer : plugin.getPlayerList().getKillers()) {
                Location killerLocation = killer.getPlayer().getLocation();

                double terrorRadius = killer.getTerrorRadius().getValue();
                double distance = Math.max(4, killerLocation.distance(survivorLocation));

                if (distance > terrorRadius)
                    continue;

                int delay = (int) ((distance / terrorRadius) * 10) + 8;
                if (this.delay == -1 || this.delay > delay) {
                    this.delay = delay;
                    this.volume = (float) Math.min(1, ((terrorRadius / distance) / terrorRadius) + .75);
                }
            }

            if (delay != -1) {
                PlayerAudioManager audioManager = survivor.getAudioManager();
                audioManager.playSound(SoundRegistry.SURVIVOR_HEARTBEAT, SoundCategory.MASTER, survivorLocation, volume, 1);
            }
        }
    }

}
