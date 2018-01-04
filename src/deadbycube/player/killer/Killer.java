package deadbycube.player.killer;

import deadbycube.DeadByCube;
import deadbycube.player.DeadByCubePlayer;
import deadbycube.player.PlayerType;
import deadbycube.player.actionhandler.KillerActionHandler;
import deadbycube.player.killer.power.Power;
import deadbycube.player.killer.power.PowerRegistry;
import deadbycube.util.Tickable;
import deadbycube.util.EditableValue;
import org.bukkit.entity.Player;

public abstract class Killer extends DeadByCubePlayer {

    private final String name;
    private final Tickable tickable;
    private final EditableValue terrorRadius = new EditableValue(32d);

    private Power power;

    protected Killer(DeadByCube plugin, Player player, String name, PowerRegistry power) {
        super(plugin, player);
        this.name = name;
        this.tickable = new Tickable(plugin, this::update);

        this.power = power.create(this);
    }

    @Override
    public final void init() {
        player.setFoodLevel(0); // prevent sprint

        this.tickable.startTask();
        this.power.init(false);
    }

    @Override
    public final void reset() {
        this.tickable.stopTask();
        power.reset();
    }

    abstract void update();

    @Override
    public final KillerActionHandler createActionHandler() {
        return new KillerActionHandler(this);
    }

    @Override
    public final PlayerType getType() {
        return PlayerType.KILLER;
    }

    public EditableValue getTerrorRadius() {
        return terrorRadius;
    }

    public Power getPower() {
        return power;
    }

    public void setPower(PowerRegistry power) {
        boolean using = false;
        if (this.power != null) {
            using = this.power.isUsing();
            this.power.reset();
        }
        this.power = power.create(this);
        this.power.init(using);
    }

}
