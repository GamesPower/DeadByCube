package deadbycube.player.killer.power.cartersspark.interaction;

import deadbycube.interaction.Interaction;
import deadbycube.interaction.InteractionActionBinding;
import deadbycube.player.killer.power.cartersspark.CartersSparkMode;
import deadbycube.player.killer.power.cartersspark.PowerCartersSpark;
import deadbycube.util.Progression;
import org.bukkit.boss.BarColor;

public class SwitchToPunishmentInteraction extends Interaction {

    private final PowerCartersSpark power;
    private final Progression progression;

    public SwitchToPunishmentInteraction(PowerCartersSpark power) {
        super(InteractionActionBinding.SNEAK, "switch_to_punishment");
        this.power = power;
        this.progression = new Progression("switch_to_punishment", BarColor.WHITE);
    }

    @Override
    public void onInteract() {
        this.progression.display(power.getKiller());
    }

    @Override
    public void onUpdate(int switchToPunishmentProgress) {
        double switchToPunishmentTime = power.getSwitchToPunishmentTime().getValue();

        if (switchToPunishmentProgress % 2 == 0) {
            this.progression.setMaxValue(switchToPunishmentTime);
            this.progression.setValue(switchToPunishmentProgress);
        }

        if (switchToPunishmentProgress >= switchToPunishmentTime)
            this.stopInteract();
    }

    @Override
    public void onStopInteract(int switchToPunishmentProgress) {
        double switchToPunishmentTime = power.getSwitchToPunishmentTime().getValue();

        if (switchToPunishmentProgress >= switchToPunishmentTime)
            power.setMode(CartersSparkMode.PUNISHMENT);

        this.progression.setValue(0);
        this.progression.reset(deadByCubePlayer);
    }

    @Override
    public boolean isInteracting() {
        return deadByCubePlayer.isSneaking();
    }

}
