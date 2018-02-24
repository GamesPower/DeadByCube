package deadbycube.player.killer.power.cartersspark.madness;

import deadbycube.audio.PlayerAudioManager;
import deadbycube.audio.WorldAudioManager;
import deadbycube.interaction.InteractionActionBinding;
import deadbycube.interaction.InteractionManager;
import deadbycube.player.killer.power.cartersspark.interaction.SnapOutOfItInteraction;
import deadbycube.player.survivor.SurvivorPlayer;
import deadbycube.registry.SoundRegistry;
import deadbycube.util.ProgressBar;
import deadbycube.util.TickLoop;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.SoundCategory;
import org.bukkit.boss.BarColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class MadnessManager {

    private final ArrayList<MadnessEmitter> madnessEmitterList = new ArrayList<>();

    private final SurvivorPlayer survivor;
    private final TickLoop tickLoop;
    private final SnapOutOfItInteraction snapOutOfItInteraction;

    private final ProgressBar madnessProgressBar = new ProgressBar("Madness", BarColor.WHITE);

    private byte particleTick = 4;

    private MadnessTier tier;
    private double madness;

    public MadnessManager(SurvivorPlayer survivor) {
        this.survivor = survivor;
        this.tickLoop = new TickLoop(this::update);
        this.snapOutOfItInteraction = new SnapOutOfItInteraction();
    }

    public void init() {
        this.tickLoop.startTask();
        this.setTier(MadnessTier.TIER_0, false);
    }

    public void reset() {
        this.tickLoop.stopTask();
        this.madnessProgressBar.reset(survivor.getPlayer());
    }

    private void update() {
        if (tier == MadnessTier.TIER_3)
            return;
        this.particleTick--;

        Player survivorPlayer = survivor.getPlayer();
        Location survivorLocation = survivorPlayer.getLocation();

        for (MadnessEmitter madnessEmitter : madnessEmitterList) {
            double distance = survivorLocation.distance(madnessEmitter.getLocation());
            double emitterRadius = madnessEmitter.getRadius().getValue();
            if (distance > emitterRadius)
                continue;

            this.incrementMadness(Math.min(4, 1 / (distance / emitterRadius)) * madnessEmitter.getStaticFieldMultiplier().getValue());
            if (particleTick <= 0) {
                this.particleTick = 4;
                survivorPlayer.getWorld().spawnParticle(
                        Particle.CLOUD,
                        survivorLocation, 2,
                        .2, 0, .2,
                        0
                );
            }
        }
    }

    public void incrementMadness(double madness) {
        if (tier == MadnessTier.TIER_3)
            return;

        this.madness += madness;
        this.madnessProgressBar.setValue(this.madness);

        if (this.madness >= tier.getRequiredMadness())
            this.incrementTier();
    }

    private void incrementTier() {
        if (tier == MadnessTier.TIER_3)
            return;

        MadnessTier[] madnessTiers = MadnessTier.values();
        int newTierIndex = tier.ordinal() + 1;
        this.setTier(madnessTiers[newTierIndex], true);

        WorldAudioManager worldAudioManager = survivor.getPlugin().getAudioManager();
        worldAudioManager.playSound("survivor." + survivor.getName() + ".scream.long", SoundCategory.VOICE, survivor.getPlayer().getLocation());
    }

    public void setTier(MadnessTier tier, boolean incremented) {
        this.madness = 0;
        this.tier = tier;
        this.madnessProgressBar.setMaxValue(tier.getRequiredMadness());

        if (tier.hasDisplay() && !madnessProgressBar.isDisplayed(survivor.getPlayer()))
            this.madnessProgressBar.display(survivor.getPlayer());

        if (incremented) {
            PlayerAudioManager playerAudioManager = survivor.getAudioManager();
            switch (tier) {
                case TIER_1:
                    playerAudioManager.playSound(SoundRegistry.POWER_CARTERS_SPARK_INSANITY_TIER_1, SoundCategory.MASTER);
                    break;
                case TIER_2:
                    playerAudioManager.playSound(SoundRegistry.POWER_CARTERS_SPARK_INSANITY_TIER_2, SoundCategory.MASTER);
                    break;
                case TIER_3:
                    playerAudioManager.playSound(SoundRegistry.POWER_CARTERS_SPARK_INSANITY_TIER_3, SoundCategory.MASTER);

                    InteractionManager interactionManager = survivor.getInteractionManager();
                    interactionManager.registerInteraction(InteractionActionBinding.SNEAK, snapOutOfItInteraction);
                    break;
                default:
                    break;
            }
        }
    }

    public void snapOutOfIt() {
        PlayerAudioManager audioManager = survivor.getAudioManager();
        audioManager.playSound(SoundRegistry.POWER_CARTERS_SPARK_INSANITY_SNAP_OUT_OF_IT, SoundCategory.MASTER);

        InteractionManager interactionManager = survivor.getInteractionManager();
        interactionManager.unregisterInteraction(InteractionActionBinding.SNEAK, snapOutOfItInteraction);

        this.setTier(MadnessTier.TIER_2, false);
    }

    public void registerMadnessEmitter(MadnessEmitter madnessEmitter) {
        this.madnessEmitterList.add(madnessEmitter);
    }

    public void unregisterMadnessEmitter(MadnessEmitter madnessEmitter) {
        this.madnessEmitterList.remove(madnessEmitter);
    }

    public MadnessTier getTier() {
        return tier;
    }

}
