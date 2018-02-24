package deadbycube.player.survivor.heartbeat;

import deadbycube.audio.PlayerAudioManager;
import deadbycube.player.survivor.SurvivorPlayer;
import deadbycube.registry.SoundRegistry;
import deadbycube.util.TickLoop;
import org.bukkit.Location;
import org.bukkit.SoundCategory;

import java.util.ArrayList;

public class HeartbeatManager {

    private final SurvivorPlayer survivor;
    private final TickLoop tickLoop;
    private final ArrayList<HeartbeatEmitter> heartbeatEmitterList = new ArrayList<>();

    private int delay = 0;
    private float volume = 0;

    public HeartbeatManager(SurvivorPlayer survivor) {
        this.survivor = survivor;
        this.tickLoop = new TickLoop(this::update);
    }

    private void update() {
        if (delay-- == 0) {
            Location survivorLocation = survivor.getPlayer().getLocation();

            for (HeartbeatEmitter heartbeatEmitter : heartbeatEmitterList) {
                Location heartbeatLocation = heartbeatEmitter.getLocation();
                double terrorRadius = heartbeatEmitter.getTerrorRadius().getValue();
                double distance = Math.max(2, heartbeatLocation.distance(survivorLocation));

                if (distance > terrorRadius)
                    continue;

                int delay = (int) ((distance / terrorRadius) * 10) + 8;
                if (this.delay == -1 || this.delay > delay) {
                    this.delay = delay;
                    this.volume = (float) Math.min(1, ((terrorRadius / distance) / terrorRadius) + .75);
                }
            }

            if (delay > -1) {
                PlayerAudioManager audioManager = survivor.getAudioManager();
                audioManager.playSound(SoundRegistry.SURVIVOR_HEARTBEAT, SoundCategory.MASTER, survivorLocation, volume);
            } else
                this.delay = 0;
        }
    }

    public void init() {
        this.tickLoop.startTask();
    }

    public void reset() {
        this.tickLoop.stopTask();
    }

    public void registerHeartbeatEmitter(HeartbeatEmitter heartbeatEmitter) {
        this.heartbeatEmitterList.add(heartbeatEmitter);
    }

    /*public void unregisterHeartbeatEmitter(HeartbeatEmitter heartbeatEmitter) {
        this.heartbeatEmitterList.remove(heartbeatEmitter);
    }*/

}
