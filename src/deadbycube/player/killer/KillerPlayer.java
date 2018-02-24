package deadbycube.player.killer;

import deadbycube.DeadByCube;
import deadbycube.interaction.InteractionActionBinding;
import deadbycube.player.DeadByCubePlayer;
import deadbycube.player.PlayerType;
import deadbycube.player.killer.interaction.attack.AttackLungeInteraction;
import deadbycube.player.killer.interaction.stun.StunInteraction;
import deadbycube.player.killer.power.Power;
import deadbycube.player.survivor.heartbeat.HeartbeatEmitter;
import deadbycube.registry.PowerRegistry;
import deadbycube.registry.SkinRegistry;
import deadbycube.util.MagicalValue;
import deadbycube.util.TickLoop;
import org.bukkit.Location;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;

public abstract class KillerPlayer extends DeadByCubePlayer implements HeartbeatEmitter {

    public static final double WALK_SPEED = 0.3;
    public static final double TERROR_RADIUS = 32;
    private static final int FOOD_LEVEL = 0;

    private final TickLoop tickLoop;
    private final byte breathDelay;

    private final MagicalValue terrorRadius = new MagicalValue(32d);
    private final MagicalValue breathVolume = new MagicalValue(0.1f);
    private final MagicalValue lungeSpeed = new MagicalValue(WALK_SPEED * 1.5);
    private final MagicalValue lungeDuration = new MagicalValue(16);
    private final MagicalValue missedAttackCooldown = new MagicalValue(20);
    private final MagicalValue successfulAttackCooldown = new MagicalValue(40);

    private final AttackLungeInteraction attackLungeInteraction = new AttackLungeInteraction();
    private final StunInteraction stunInteraction = new StunInteraction();

    private Power power;
    private byte breathTick = 0;

    protected KillerPlayer(DeadByCube plugin, Player player, String name, byte breathDelay, SkinRegistry skin, PowerRegistry power) {
        super(plugin, player, name, skin);
        this.tickLoop = new TickLoop(this::update);
        this.breathDelay = breathDelay;

        this.power = power.create(this);
    }

    @Override
    public void init() {
        super.init();

        this.walkSpeed.setBaseValue(WALK_SPEED);
        this.player.setFoodLevel(FOOD_LEVEL);

        this.interactionManager.registerInteraction(InteractionActionBinding.ATTACK, attackLungeInteraction);

        this.lungeDuration.setBaseValue(16);

        this.tickLoop.startTask();
        this.power.init();
    }

    @Override
    public void reset() {
        super.reset();

        this.tickLoop.stopTask();
        this.power.reset();
    }

    public void stun(int duration) {
        this.stunInteraction.setDuration(duration);
        interactionManager.interact(stunInteraction);
    }

    void playBreathSound() {
        plugin.getAudioManager().playSound("killer." + name + ".breath", SoundCategory.VOICE, player.getLocation(), (float) breathVolume.getValue(), 1);
    }

    protected void update() {
        if (++breathTick == breathDelay) {
            this.playBreathSound();
            this.breathTick = 0;
        }
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

    public MagicalValue getLungeSpeed() {
        return lungeSpeed;
    }

    public MagicalValue getLungeDuration() {
        return lungeDuration;
    }

    public MagicalValue getMissedAttackCooldown() {
        return missedAttackCooldown;
    }

    public MagicalValue getSuccessfulAttackCooldown() {
        return successfulAttackCooldown;
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


}
