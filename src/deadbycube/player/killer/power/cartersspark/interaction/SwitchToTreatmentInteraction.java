package deadbycube.player.killer.power.cartersspark.interaction;

import deadbycube.interaction.Interaction;
import deadbycube.interaction.InteractionActionBinding;
import deadbycube.player.killer.power.cartersspark.CartersSparkMode;
import deadbycube.player.killer.power.cartersspark.PowerCartersSpark;
import deadbycube.util.Progression;
import org.bukkit.boss.BarColor;

public class SwitchToTreatmentInteraction extends Interaction {

    private final PowerCartersSpark power;
    private final Progression progression;

    public SwitchToTreatmentInteraction(PowerCartersSpark power) {
        super(InteractionActionBinding.SNEAK, "switch_to_treatment");
        this.power = power;
        this.progression = new Progression("switch_to_treatment", BarColor.WHITE);
    }

    @Override
    public void onInteract() {
        this.progression.display(power.getKiller());
    }

    @Override
    public void onUpdate(int switchToTreatmentProgress) {
        double switchToTreatmentTime = power.getSwitchToTreatmentTime().getValue();

        if (switchToTreatmentProgress % 2 == 0) {
            this.progression.setMaxValue(switchToTreatmentTime);
            this.progression.setValue(switchToTreatmentProgress);
        }

        if (switchToTreatmentProgress >= switchToTreatmentTime)
            this.stopInteract();
    }

    @Override
    public void onStopInteract(int switchToTreatmentProgress) {
        double switchToTreatmentTime = power.getSwitchToTreatmentTime().getValue();

        if (switchToTreatmentProgress >= switchToTreatmentTime)
            power.setMode(CartersSparkMode.TREATMENT);

        this.progression.setValue(0);
        this.progression.reset(deadByCubePlayer);
    }

    @Override
    public boolean isInteracting() {
        return deadByCubePlayer.isSneaking();
    }

}
