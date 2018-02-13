package deadbycube.player.killer;

import deadbycube.DeadByCube;
import deadbycube.interaction.InteractionActionBinding;
import deadbycube.player.DeadByCubePlayer;
import deadbycube.player.PlayerType;
import deadbycube.player.killer.interaction.attack.AttackLungeInteraction;
import deadbycube.player.killer.power.Power;
import deadbycube.player.survivor.heartbeat.HeartbeatEmitter;
import deadbycube.registry.PowerRegistry;
import deadbycube.util.MagicalValue;
import deadbycube.util.TickLoop;
import org.bukkit.Location;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;

public abstract class KillerPlayer extends DeadByCubePlayer implements HeartbeatEmitter {

    public static final double WALK_SPEED = 0.3;
    public static final double TERROR_RADIUS = 32;
    private static final int FOOD_LEVEL = 0;

    private final String name;
    private final TickLoop tickLoop;

    private final MagicalValue terrorRadius = new MagicalValue(32d);
    private final MagicalValue breathVolume = new MagicalValue(0.1f);
    private final MagicalValue lungeSpeed = new MagicalValue(0.45);
    private final MagicalValue lungeDuration = new MagicalValue(15);

    private final AttackLungeInteraction attackLungeInteraction = new AttackLungeInteraction();

    private Power power;

    protected KillerPlayer(DeadByCube plugin, Player player, String name, PowerRegistry power) {
        super(plugin, player);
        this.name = name;
        this.tickLoop = new TickLoop(this::update);

        this.power = power.create(this);
    }

    void playBreathSound() {
        plugin.getAudioManager().playSound("killer." + name + ".breath", SoundCategory.VOICE, player.getLocation(), (float) breathVolume.getValue(), 1);
    }

    abstract void update();

    @Override
    public void init() {
        super.init();

        walkSpeed.setBaseValue(WALK_SPEED);
        player.setFoodLevel(FOOD_LEVEL);

        this.interactionManager.registerInteraction(InteractionActionBinding.ATTACK, attackLungeInteraction);
        this.interactionManager.updateInteractions();

        this.tickLoop.startTask();
        this.power.init();
    }

    @Override
    public void reset() {
        super.reset();

        this.tickLoop.stopTask();
        this.power.reset();
    }

    @Override
    public final PlayerType getType() {
        return PlayerType.KILLER;
    }

    @Override
    public Location getLocation() {
        return player.getLocation();
    }

    @Override
    public MagicalValue getTerrorRadius() {
        return terrorRadius;
    }

    public MagicalValue getBreathVolume() {
        return breathVolume;
    }

    public AttackLungeInteraction getAttackLungeInteraction() {
        return attackLungeInteraction;
    }

    public Power getPower() {
        return power;
    }

    public void setPower(PowerRegistry power) {
        if (this.power != null) {
            this.power.reset();
        }
        this.power = power.create(this);
        this.power.init();
    }

    public String getName() {
        return name;
    }


    public MagicalValue getLungeDuration() {
        return lungeDuration;
    }
}
