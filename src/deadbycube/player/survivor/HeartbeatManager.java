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

    private boolean heartbeat = false;
    private int delay = 0;
    private float volume = 0;

    HeartbeatManager(Survivor survivor) {
        this.survivor = survivor;
        this.tickable = new Tickable(survivor.getPlugin(), this::update);
    }

    private void update() {
        if (delay-- == 0) {
            DeadByCube plugin = survivor.getPlugin();
            Location survivorLocation = survivor.getPlayer().getLocation();

            for (Killer killer : plugin.getPlayerList().getKillers()) {
                Location killerLocation = killer.getPlayer().getLocation();

                double terrorRadius = killer.getTerrorRadius().getValue();
                double distance = Math.max(2, killerLocation.distance(survivorLocation));

                if (distance > terrorRadius)
                    continue;

                int delay = (int) ((distance / terrorRadius) * 10) + 8;
                if (this.delay == -1 || this.delay > delay) {
                    this.heartbeat = true;
                    this.delay = delay;
                    this.volume = (float) Math.min(1, ((terrorRadius / distance) / terrorRadius) + .75);
                }
            }

            if (heartbeat) {
                this.heartbeat = false;
                PlayerAudioManager audioManager = survivor.getAudioManager();
                audioManager.playSound(SoundRegistry.SURVIVOR_HEARTBEAT, SoundCategory.MASTER, survivorLocation, volume, 1);
            } else
                this.delay = 0;
        }
    }

    public void init() {
        this.tickable.startTask();
    }

    public void reset() {
        this.tickable.stopTask();
    }

}
