package deadbycube.player.killer.power.cartersspark.interaction;

import deadbycube.interaction.Interaction;
import deadbycube.player.killer.power.cartersspark.CartersSparkMode;
import deadbycube.player.killer.power.cartersspark.PowerCartersSpark;
import deadbycube.util.MagicalValue;
import deadbycube.util.ProgressBar;
import org.bukkit.boss.BarColor;

public class SwitchToPunishmentInteraction extends Interaction {

    private final PowerCartersSpark power;
    private final ProgressBar progressBar;

    public SwitchToPunishmentInteraction(PowerCartersSpark power) {
        super("switch_to_punishment");
        this.power = power;
        this.progressBar = new ProgressBar("switch_to_punishment", BarColor.WHITE);
    }

    @Override
    protected void onInteract() {
        this.progressBar.display(interactor.getPlayer());
    }

    @Override
    protected void onUpdate(int switchToPunishmentProgress) {
        MagicalValue switchToPunishmentTime = power.getSwitchToPunishmentTime();

        if (switchToPunishmentProgress % 2 == 0) {
            this.progressBar.setColorFromValue(switchToPunishmentTime);
            this.progressBar.setMaxValue(switchToPunishmentTime.getValue());
            this.progressBar.setValue(switchToPunishmentProgress);
        }

        if (switchToPunishmentProgress >= switchToPunishmentTime.getValue())
            this.stopInteract();
    }

    @Override
    protected void onStopInteract(int switchToPunishmentProgress) {
        double switchToPunishmentTime = power.getSwitchToPunishmentTime().getValue();
        if (switchToPunishmentProgress >= switchToPunishmentTime)
            power.setMode(CartersSparkMode.PUNISHMENT);

        this.progressBar.setValue(0);
        this.progressBar.reset(interactor.getPlayer());
    }

    @Override
    public boolean isInteracting() {
        return super.isInteracting() && interactor.isSneaking();
    }

}
