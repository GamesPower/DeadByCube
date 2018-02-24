package deadbycube.player.killer.power.cartersspark.interaction;

import deadbycube.interaction.Interaction;
import deadbycube.player.killer.power.cartersspark.CartersSparkMode;
import deadbycube.player.killer.power.cartersspark.PowerCartersSpark;
import deadbycube.util.MagicalValue;
import deadbycube.util.ProgressBar;
import org.bukkit.boss.BarColor;

public class SwitchToTreatmentInteraction extends Interaction {

    private final PowerCartersSpark power;
    private final ProgressBar progressBar;

    public SwitchToTreatmentInteraction(PowerCartersSpark power) {
        super("switch_to_treatment");
        this.power = power;
        this.progressBar = new ProgressBar("switch_to_treatment", BarColor.WHITE);
    }

    @Override
    protected void onInteract() {
        this.progressBar.display(interactor.getPlayer());
    }

    @Override
    protected void onUpdate(int switchToTreatmentProgress) {
        MagicalValue switchToTreatmentTime = power.getSwitchToTreatmentTime();

        if (switchToTreatmentProgress % 2 == 0) {
            this.progressBar.setColorFromValue(switchToTreatmentTime);
            this.progressBar.setMaxValue(switchToTreatmentTime.getValue());
            this.progressBar.setValue(switchToTreatmentProgress);
        }

        if (switchToTreatmentProgress >= switchToTreatmentTime.getValue())
            this.stopInteract();
    }

    @Override
    protected void onStopInteract(int switchToTreatmentProgress) {
        double switchToTreatmentTime = power.getSwitchToTreatmentTime().getValue();

        if (switchToTreatmentProgress >= switchToTreatmentTime)
            power.setMode(CartersSparkMode.TREATMENT);

        this.progressBar.setValue(0);
        this.progressBar.reset(interactor.getPlayer());
    }

    @Override
    public boolean isInteracting() {
        return super.isInteracting() && interactor.isSneaking();
    }

}
