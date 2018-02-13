package deadbycube.player.killer.interaction.attack;

import deadbycube.audio.WorldAudioManager;
import deadbycube.interaction.Interaction;
import deadbycube.interaction.InteractionManager;
import deadbycube.player.killer.KillerPlayer;
import deadbycube.player.survivor.SurvivorPlayer;
import deadbycube.util.MagicalValue;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class AttackLungeInteraction extends Interaction {

    protected static final String LUNGE_SPEED_MODIFIER = "attack.lunge";

    private static final double ATTACK_DISTANCE = 2;
    private static final double ACCURACY = .02;

    private boolean successful = false;

    public AttackLungeInteraction() {
        super("attack");
    }

    @Override
    protected void onInteract() {
        this.successful = false;

        KillerPlayer killer = (KillerPlayer) interactor;

        WorldAudioManager audioManager = killer.getPlugin().getAudioManager();
        audioManager.playSound("killer." + killer.getName() + ".weapon.arm", SoundCategory.MASTER, interactor.getPlayer().getLocation());

        killer.getWalkSpeed().addModifier(LUNGE_SPEED_MODIFIER, 50, MagicalValue.Operation.PERCENTAGE);
    }

    @Override
    protected void onUpdate(int tick) {
        KillerPlayer killer = (KillerPlayer) interactor;
        Player killerPlayer = killer.getPlayer();
        Location killerLocation = killerPlayer.getLocation();
        Location killerEyeLocation = killerPlayer.getEyeLocation();
        Vector killerDirection = killerLocation.getDirection();

        for (SurvivorPlayer survivor : killer.getPlugin().getPlayerList().getSurvivors()) {
            Player survivorPlayer = survivor.getPlayer();
            Location survivorLocation = survivorPlayer.getLocation();
            Vector survivorMinLocation = survivorLocation.clone().subtract(.6, 0, .6).toVector();
            Vector survivorMaxLocation = survivorLocation.clone().add(.6, 2, .6).toVector();

            for (double i = 1; i < ATTACK_DISTANCE; i += ACCURACY) {
                Vector hitPoint = killerEyeLocation.clone().add(killerDirection.clone().add(killerPlayer.getVelocity()).multiply(i)).toVector();
                killerPlayer.getWorld().spawnParticle(Particle.REDSTONE, hitPoint.toLocation(killerPlayer.getWorld()), 1, 0, 0, 0, 0);
                if (hitPoint.isInAABB(survivorMinLocation, survivorMaxLocation)) {
                    killerPlayer.sendMessage("HIT: " + survivorPlayer.getDisplayName());
                    survivorPlayer.sendMessage("HIT: " + survivorPlayer.getDisplayName());
                    WorldAudioManager audioManager = killer.getPlugin().getAudioManager();
                    audioManager.playSound("survivor.dwight.scream.short", SoundCategory.VOICE, survivorLocation);
                    /*Location survivorEyeLocation = survivorPlayer.getEyeLocation();
                    Vector direction = survivorEyeLocation.subtract(killerEyeLocation).toVector();
                    killerPlayer.teleport(killerLocation.setDirection(direction));*/
                    this.successful = true;
                    this.stopInteract();
                    return;
                }
            }
        }

        if (tick == killer.getLungeDuration().getValue()) {
            this.stopInteract();
        }
    }

    @Override
    protected void onStopInteract(int tick) {
        interactor.getWalkSpeed().removeModifier(LUNGE_SPEED_MODIFIER);

        KillerPlayer killer = (KillerPlayer) interactor;
        WorldAudioManager audioManager = killer.getPlugin().getAudioManager();
        audioManager.playSound("killer." + killer.getName() + ".weapon.missed", SoundCategory.MASTER, interactor.getPlayer().getLocation());

        InteractionManager interactionManager = interactor.getInteractionManager();
        interactionManager.interact(new AttackRecoveryInteraction(successful ? 40 : 20));
    }

}
