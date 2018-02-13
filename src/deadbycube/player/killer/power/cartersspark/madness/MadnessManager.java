package deadbycube.player.killer.power.cartersspark.madness;

import deadbycube.player.survivor.SurvivorPlayer;
import deadbycube.util.TickLoop;

import java.util.ArrayList;

public class MadnessManager {

    private final SurvivorPlayer survivor;
    private final TickLoop tickLoop;
    private final ArrayList<MadnessEmitter> madnessEmitterList = new ArrayList<>();

    private MadnessLevel level = MadnessLevel.LEVEL_0;
    private int percentage = 0;

    public MadnessManager(SurvivorPlayer survivor) {
        this.survivor = survivor;
        this.tickLoop = new TickLoop(this::update);
    }

    private void update() {

    }

    public void init() {
        this.tickLoop.startTask();
    }

    public void registerMadnessEmitter(MadnessEmitter madnessEmitter) {
        this.madnessEmitterList.add(madnessEmitter);
    }

    public void unregisterMadnessEmitter(MadnessEmitter madnessEmitter) {
        this.madnessEmitterList.remove(madnessEmitter);
    }

}
