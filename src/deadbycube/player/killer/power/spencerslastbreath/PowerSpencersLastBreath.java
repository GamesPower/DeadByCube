package deadbycube.player.killer.power.spencerslastbreath;

import deadbycube.interaction.InteractionActionBinding;
import deadbycube.interaction.InteractionManager;
import deadbycube.player.killer.KillerPlayer;
import deadbycube.player.killer.power.Power;
import deadbycube.player.killer.power.spencerslastbreath.interaction.BlinkInteraction;
import deadbycube.player.killer.power.spencerslastbreath.interaction.BlinkStunInteraction;
import deadbycube.player.killer.power.spencerslastbreath.interaction.ChargeBlinkInteraction;
import deadbycube.player.killer.power.spencerslastbreath.interaction.attack.BlinkAttackLungeInteraction;
import deadbycube.util.MagicalValue;
import deadbycube.util.MathUtils;
import deadbycube.util.TickLoop;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

public class PowerSpencersLastBreath extends Power {

    private static final float WALK_SPEED = 0.245f;

    private final ChargeBlinkInteraction chargeBlinkInteraction;
    private final BlinkInteraction blinkInteraction;
    private final BlinkStunInteraction blinkStunInteraction;
    private final BlinkAttackLungeInteraction blinkAttackLungeInteraction;

    private final MagicalValue chargeTime = new MagicalValue(40);
    private final MagicalValue fullBlinkDistance = new MagicalValue(20);
    private final MagicalValue blinkMovementSpeed = new MagicalValue(.85);
    private final MagicalValue chainBlinkWindow = new MagicalValue(40);
    private final MagicalValue chainBlink = new MagicalValue(1);
    private final MagicalValue blinkHitCooldown = new MagicalValue(20);

    private int remainingBlinks = 0;
    private BukkitTask blinkStunTask;

    public PowerSpencersLastBreath(KillerPlayer killer) {
        super(killer);
        this.chargeBlinkInteraction = new ChargeBlinkInteraction(this);
        this.blinkInteraction = new BlinkInteraction(this);
        this.blinkStunInteraction = new BlinkStunInteraction(this);
        this.blinkAttackLungeInteraction = new BlinkAttackLungeInteraction(this);
    }

    @Override
    public void init() {
        super.init();

        killer.getWalkSpeed().setBaseValue(WALK_SPEED);

        InteractionManager interactionManager = killer.getInteractionManager();
        interactionManager.registerInteraction(InteractionActionBinding.USE, chargeBlinkInteraction);

        this.updateRemainingBlinks();
    }

    @Override
    public void reset() {
        killer.getWalkSpeed().setBaseValue(KillerPlayer.WALK_SPEED);
    }

    public void updateRemainingBlinks() {
        this.remainingBlinks = (int) (1 + chainBlink.getValue());
    }

    public void blink(int chargeProgress) {
        if (this.blinkStunTask != null)
            this.blinkStunTask.cancel();

        double distanceMultiplier = chargeProgress / chargeTime.getValue();
        double distance = distanceMultiplier * fullBlinkDistance.getValue();

        Player player = killer.getPlayer().getPlayer();
        Location playerLocation = player.getLocation();
        Vector direction = MathUtils.direction(playerLocation.getYaw(), 0);
        blinkInteraction.setDestinationLocation(playerLocation.add(
                direction.getX() * distance,
                direction.getY() * distance,
                direction.getZ() * distance
        ));

        InteractionManager interactionManager = killer.getInteractionManager();
        interactionManager.interact(blinkInteraction);

        interactionManager.unregisterInteraction(InteractionActionBinding.ATTACK, killer.getAttackLungeInteraction());
        interactionManager.registerInteraction(InteractionActionBinding.ATTACK, blinkAttackLungeInteraction);

        if (--remainingBlinks == 0)
            interactionManager.unregisterInteraction(InteractionActionBinding.USE, chargeBlinkInteraction);
    }

    public void createStunTask() {
        this.blinkStunTask = TickLoop.runLater((long) chainBlinkWindow.getValue(), this::stun);
    }

    public void stun() {
        if (blinkStunTask != null && !blinkStunTask.isCancelled())
            this.blinkStunTask.cancel();

        InteractionManager interactionManager = killer.getInteractionManager();

        if (remainingBlinks == 0)
            interactionManager.registerInteraction(InteractionActionBinding.USE, chargeBlinkInteraction);
        this.remainingBlinks = 0;

        if (chargeBlinkInteraction.isInteracting())
            this.chargeBlinkInteraction.stopInteract();

        interactionManager.interact(blinkStunInteraction);

        interactionManager.unregisterInteraction(InteractionActionBinding.ATTACK, blinkAttackLungeInteraction);
        interactionManager.registerInteraction(InteractionActionBinding.ATTACK, killer.getAttackLungeInteraction());
    }

    public MagicalValue getChargeTime() {
        return chargeTime;
    }

    public MagicalValue getBlinkMovementSpeed() {
        return blinkMovementSpeed;
    }

    public MagicalValue getBlinkHitCooldown() {
        return blinkHitCooldown;
    }
}
