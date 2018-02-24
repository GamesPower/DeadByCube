package deadbycube.player.killer.power.dreammaster.interaction;

import deadbycube.interaction.Interaction;
import deadbycube.player.killer.KillerPlayer;
import deadbycube.player.killer.power.dreammaster.PowerDreamMaster;
import deadbycube.player.survivor.SurvivorPlayer;
import deadbycube.util.AxisAlignedBB;
import deadbycube.util.MathUtils;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

public class PullInDreamInteraction extends Interaction {

    private static final String PULLED_IN_DREAM_MODIFIER = "power.dream_master.pulled_in_dream";
    private static final double AUTO_AIM_WIDTH = .8;
    private static final double AUTO_AIM_HEIGHT = 2;

    private final PowerDreamMaster power;

    private BukkitTask slowdownTask;

    public PullInDreamInteraction(PowerDreamMaster power) {
        super("pull_in_dream");

        this.power = power;
    }

    @Override
    protected void onInteract() {

    }

    @Override
    protected void onUpdate(int tick) {
        KillerPlayer killer = (KillerPlayer) interactor;
        Player killerPlayer = killer.getPlayer();
        Location killerEyeLocation = killerPlayer.getEyeLocation();

        for (SurvivorPlayer survivor : killer.getPlugin().getPlayerList().getSurvivors()) {
            Player survivorPlayer = survivor.getPlayer();
            Location survivorLocation = survivorPlayer.getLocation();
            Vector min = survivorLocation.clone().subtract(AUTO_AIM_WIDTH, 0, AUTO_AIM_WIDTH).toVector();
            Vector max = survivorLocation.clone().add(AUTO_AIM_WIDTH, AUTO_AIM_HEIGHT, AUTO_AIM_WIDTH).toVector();

            if (MathUtils.isLookingAt(killerEyeLocation.clone(), new AxisAlignedBB(min, max), power.getRange().getValue(), .1) && killerPlayer.hasLineOfSight(survivorPlayer)) {

                World world = survivorPlayer.getWorld();
                world.spawnParticle(Particle.REDSTONE, survivorLocation.add(0, 1, 0), 1, .2, .2, .2, 0);
            }
        }
    }

    @Override
    protected void onStopInteract(int tick) {

    }

    @Override
    public boolean isInteracting() {
        return super.isInteracting() && interactor.getPlayer().isHandRaised();
    }

}
