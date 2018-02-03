package deadbycube.game.attack.state;

import deadbycube.audio.WorldAudioManager;
import deadbycube.game.attack.AttackManager;
import deadbycube.game.attack.KillerAttackManager;
import deadbycube.player.killer.KillerPlayer;
import deadbycube.player.survivor.SurvivorPlayer;
import deadbycube.util.MagicalValue;
import deadbycube.util.MathUtils;
import org.bukkit.Location;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class LungeAttackHandler extends AttackHandler {

    private static final int ATTACK_ANGLE = 20;
    private static final double ATTACK_DISTANCE = 1.5;

    private final MagicalValue lungeDuration = new MagicalValue(10d);

    public LungeAttackHandler(KillerAttackManager attackManager) {
        super(attackManager);
    }

    @Override
    public void update(int attackTick) {
        double lungeDurationValue = lungeDuration.getValue();
        if (attackTick == 0)
            this.startLunge();
        else if (2 < attackTick && attackTick < lungeDurationValue)
            this.processLunge();
        else if (attackTick == lungeDurationValue) {
            KillerPlayer killer = attackManager.getKiller();
            WorldAudioManager audioManager = killer.getPlugin().getAudioManager();
            audioManager.playSound("killer." + killer.getName() + ".weapon.missed", SoundCategory.MASTER, killer.getPlayer().getLocation());
            killer.getWalkSpeed().removeModifier(AttackManager.LUNGE_SPEED_MODIFIER);

            attackManager.setHandler(new MissedAttackRecoveryHandler(attackManager));
        }
    }

    private void startLunge() {
        KillerPlayer killer = attackManager.getKiller();
        WorldAudioManager audioManager = killer.getPlugin().getAudioManager();

        audioManager.playSound("killer." + killer.getName() + ".weapon.arm", SoundCategory.MASTER, killer.getPlayer().getLocation());
        killer.getWalkSpeed().addModifier(AttackManager.LUNGE_SPEED_MODIFIER, 1.05, MagicalValue.Operation.MULTIPLY);
    }

    private void processLunge() {
        KillerPlayer killer = attackManager.getKiller();

        Player killerPlayer = killer.getPlayer();
        Location killerLocation = killerPlayer.getLocation();

        Vector killerDirection = MathUtils.direction(killerLocation.getYaw(), 0);
        for (SurvivorPlayer survivor : killer.getPlugin().getPlayerList().getSurvivors()) {
            Player survivorPlayer = survivor.getPlayer();
            Location survivorLocation = survivorPlayer.getLocation();

            double distance = killerLocation.distance(survivorLocation);
            if (distance < ATTACK_DISTANCE) {
                Vector killerToSurvivorDirection = survivorLocation.subtract(killerLocation).toVector();
                double angle = Math.toDegrees(killerToSurvivorDirection.angle(killerDirection));
                if (-ATTACK_ANGLE < angle && angle < ATTACK_ANGLE) {

                    WorldAudioManager audioManager = killer.getPlugin().getAudioManager();
                    audioManager.playSound("survivor.dwight.scream.short", SoundCategory.VOICE, survivorPlayer.getLocation());

                    attackManager.setHandler(new SuccessfulAttackRecoveryHandler(attackManager));
                    return;

                }
            }
        }
    }

}
