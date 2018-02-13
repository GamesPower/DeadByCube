package deadbycube.player.killer.power.cartersspark.interaction;

import deadbycube.interaction.Interaction;
import deadbycube.player.killer.power.cartersspark.CartersSparkMode;
import deadbycube.player.killer.power.cartersspark.PowerCartersSpark;
import deadbycube.util.MagicalValue;
import deadbycube.util.Progression;
import org.bukkit.boss.BarColor;

public class SwitchToPunishmentInteraction extends Interaction {

    private final PowerCartersSpark power;
    private final Progression progression;

    public SwitchToPunishmentInteraction(PowerCartersSpark power) {
        super("switch_to_punishment");
        this.power = power;
        this.progression = new Progression("switch_to_punishment", BarColor.WHITE);
    }

    @Override
    protected void onInteract() {
        this.progression.display(power.getKiller());
    }

    @Override
    protected void onUpdate(int switchToPunishmentProgress) {
        MagicalValue switchToPunishmentTime = power.getSwitchToPunishmentTime();

        if (switchToPunishmentProgress % 2 == 0) {
            this.progression.setMaxValue(switchToPunishmentTime.getValue());
            this.progression.setValue(switchToPunishmentProgress);
        }

        if (switchToPunishmentProgress >= switchToPunishmentTime.getValue())
            this.stopInteract();
    }

    @Override
    protected void onStopInteract(int switchToPunishmentProgress) {
        double switchToPunishmentTime = power.getSwitchToPunishmentTime().getValue();

        if (switchToPunishmentProgress >= switchToPunishmentTime)
            power.setMode(CartersSparkMode.PUNISHMENT);

        this.progression.setValue(0);
        this.progression.reset(interactor);
    }

    @Override
    public boolean isInteracting() {
        return super.isInteracting() && interactor.isSneaking();
    }

}
