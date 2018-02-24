package deadbycube.player.killer.power.cartersspark.interaction;

import deadbycube.interaction.Interaction;
import deadbycube.player.killer.power.cartersspark.madness.MadnessManager;
import deadbycube.player.survivor.SurvivorPlayer;
import deadbycube.util.MagicalValue;
import deadbycube.util.ProgressBar;
import org.bukkit.boss.BarColor;

public class SnapOutOfItInteraction extends Interaction {

    private static final MagicalValue TIME = new MagicalValue(80);

    private final ProgressBar progressBar;

    public SnapOutOfItInteraction() {
        super("snap_out_of_it");

        this.progressBar = new ProgressBar("snap_out_of_it", BarColor.WHITE);
    }

    @Override
    protected void onInteract() {
        interactor.getWalkSpeed().forceValue(0);
    }

    @Override
    protected void onUpdate(int tick) {
        if (tick % 2 == 0) {
            this.progressBar.setColorFromValue(TIME);
            this.progressBar.setMaxValue(TIME.getValue());
            this.progressBar.setValue(tick);
        }

        if (tick >= TIME.getValue()) {
            this.stopInteract();
        }
    }

    @Override
    protected void onStopInteract(int tick) {
        interactor.getWalkSpeed().resetValue();

        if (tick >= 40) {
            SurvivorPlayer survivor = (SurvivorPlayer) interactor;
            MadnessManager madnessManager = survivor.getMadnessManager();
            madnessManager.snapOutOfIt();
        }
    }

    @Override
    public boolean isInteracting() {
        return super.isInteracting() && interactor.isSneaking();
    }
}
