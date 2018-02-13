package deadbycube.player.killer.power.cartersspark.interaction;

import deadbycube.interaction.Interaction;
import deadbycube.player.killer.power.cartersspark.CartersSparkMode;
import deadbycube.player.killer.power.cartersspark.PowerCartersSpark;
import deadbycube.util.MagicalValue;
import deadbycube.util.Progression;
import org.bukkit.boss.BarColor;

public class SwitchToTreatmentInteraction extends Interaction {

    private final PowerCartersSpark power;
    private final Progression progression;

    public SwitchToTreatmentInteraction(PowerCartersSpark power) {
        super("switch_to_treatment");
        this.power = power;
        this.progression = new Progression("switch_to_treatment", BarColor.WHITE);
    }

    @Override
    protected void onInteract() {
        this.progression.display(power.getKiller());
    }

    @Override
    protected void onUpdate(int switchToTreatmentProgress) {
        MagicalValue switchToTreatmentTime = power.getSwitchToTreatmentTime();

        if (switchToTreatmentProgress % 2 == 0) {
            this.progression.setMaxValue(switchToTreatmentTime.getValue());
            this.progression.setValue(switchToTreatmentProgress);
        }

        if (switchToTreatmentProgress >= switchToTreatmentTime.getValue())
            this.stopInteract();
    }

    @Override
    protected void onStopInteract(int switchToTreatmentProgress) {
        double switchToTreatmentTime = power.getSwitchToTreatmentTime().getValue();

        if (switchToTreatmentProgress >= switchToTreatmentTime)
            power.setMode(CartersSparkMode.TREATMENT);

        this.progression.setValue(0);
        this.progression.reset(interactor);
    }

    @Override
    public boolean isInteracting() {
        return super.isInteracting() && interactor.isSneaking();
    }

}
