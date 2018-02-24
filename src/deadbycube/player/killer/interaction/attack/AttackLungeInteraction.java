package deadbycube.player.killer.interaction.attack;

import deadbycube.DeadByCube;
import deadbycube.audio.WorldAudioManager;
import deadbycube.interaction.Interaction;
import deadbycube.interaction.InteractionManager;
import deadbycube.player.killer.KillerPlayer;
import deadbycube.player.survivor.SurvivorPlayer;
import org.bukkit.Location;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class AttackLungeInteraction extends Interaction {

    private static final double ATTACK_DISTANCE = 2;
    private static final double ACCURACY = .02;

    private boolean successful = false;

    public AttackLungeInteraction() {
        super("attack");
    }

    @Override
    protected void onInteract() {
        this.successful = false;

        Player player = interactor.getPlayer();
        WorldAudioManager audioManager = interactor.getPlugin().getAudioManager();

        audioManager.playSound("killer." + interactor.getName() + ".weapon.arm", SoundCategory.MASTER, player.getLocation());

        KillerPlayer killer = (KillerPlayer) interactor;
        interactor.getWalkSpeed().forceValue(killer.getLungeSpeed().getValue());
    }

    @Override
    protected void onUpdate(int tick) {
        DeadByCube plugin = interactor.getPlugin();
        Player player = interactor.getPlayer();

        Location playerLocation = player.getLocation();
        Location playerEyeLocation = player.getEyeLocation();
        Vector locationDirection = playerLocation.getDirection();

        for (SurvivorPlayer survivor : plugin.getPlayerList().getSurvivors()) {
            Player survivorPlayer = survivor.getPlayer();
            Location survivorLocation = survivorPlayer.getLocation();
            Vector survivorMinLocation = survivorLocation.clone().subtract(.6, 0, .6).toVector();
            Vector survivorMaxLocation = survivorLocation.clone().add(.6, 2, .6).toVector();

            for (double i = 1; i < ATTACK_DISTANCE; i += ACCURACY) {
                //player.getWorld().spawnParticle(Particle.REDSTONE, hitPoint.toLocation(player.getWorld()), 1, 0, 0, 0, 0);

                Vector hitPoint = playerEyeLocation.clone().add(locationDirection.clone().add(player.getVelocity()).multiply(i)).toVector();
                if (hitPoint.isInAABB(survivorMinLocation, survivorMaxLocation) && player.hasLineOfSight(survivorPlayer)) {
                    survivor.hit();

                    this.successful = true;
                    this.stopInteract();
                    return;
                }
            }
        }

        if (tick >= ((KillerPlayer) interactor).getLungeDuration().getValue())
            this.stopInteract();
    }

    @Override
    protected void onStopInteract(int tick) {
        interactor.getWalkSpeed().resetValue();

        KillerPlayer killer = (KillerPlayer) interactor;
        WorldAudioManager audioManager = killer.getPlugin().getAudioManager();
        audioManager.playSound("killer." + killer.getName() + ".weapon.attack", SoundCategory.MASTER, interactor.getPlayer().getLocation());

        InteractionManager interactionManager = interactor.getInteractionManager();
        interactionManager.interact(new AttackRecoveryInteraction(successful ? killer.getSuccessfulAttackCooldown() : killer.getMissedAttackCooldown()));
    }

}
