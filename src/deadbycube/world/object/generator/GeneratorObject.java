package deadbycube.world.object.generator;

import deadbycube.DeadByCube;
import deadbycube.audio.WorldAudioManager;
import deadbycube.interaction.InteractionActionBinding;
import deadbycube.interaction.InteractionManager;
import deadbycube.player.PlayerList;
import deadbycube.player.killer.KillerPlayer;
import deadbycube.player.survivor.SurvivorPlayer;
import deadbycube.registry.SoundRegistry;
import deadbycube.util.MagicalValue;
import deadbycube.world.object.InteractableObject;
import deadbycube.world.object.generator.interaction.BreakInteraction;
import deadbycube.world.object.generator.interaction.RepairInteraction;
import org.bukkit.Location;
import org.bukkit.SoundCategory;
import org.bukkit.block.BlockFace;

public class GeneratorObject extends InteractableObject {

    private static final double CHARGE = 500d;

    private final RepairInteraction[] repairInteractions;
    private final BreakInteraction[] breakInteractions;

    private boolean completed = false;
    private double charge = 0d;
    private double maxCharge = CHARGE;

    private int soundTick = 0;

    public GeneratorObject(Location location, BlockFace... faces) {
        super(location);

        this.repairInteractions = new RepairInteraction[faces.length];
        this.breakInteractions = new BreakInteraction[faces.length];
        for (int i = 0; i < faces.length; i++) {
            BlockFace face = faces[i];
            Location interactionLocation = location.clone().add(face.getModX() * 0.6, face.getModY() * 0.6, face.getModZ() * 0.6);
            this.repairInteractions[i] = new RepairInteraction(this, face, interactionLocation, .8, 60);
            this.breakInteractions[i] = new BreakInteraction(this, interactionLocation, .8, 60);
        }
    }

    @Override
    protected void update() {

        if (!completed) {

            if (soundTick-- == 0) {
                DeadByCube plugin = DeadByCube.getInstance();
                WorldAudioManager audioManager = plugin.getAudioManager();
                audioManager.playSound(SoundRegistry.OBJECT_GENERATOR_PISTON, SoundCategory.MASTER, getLocation());
                this.soundTick = ((int) (-8 * (charge / maxCharge)) + 11);
                System.out.println("soundTick = " + soundTick);
            }

            int repairingSurvivors = 0;
            for (RepairInteraction repairInteraction : repairInteractions) {
                if (repairInteraction.isInteracting()) {
                    SurvivorPlayer survivor = (SurvivorPlayer) repairInteraction.getPlayer();
                    MagicalValue repairSpeed = survivor.getRepairSpeed();
                    if (repairingSurvivors++ > 1)
                        this.charge += repairSpeed.getValue() * 0.9;
                    else
                        this.charge += repairSpeed.getValue();
                }
            }

            if (charge >= maxCharge) {
                this.completed = true;
                this.charge = maxCharge;
                this.onCompleted();
            }

        } else if (soundTick-- == 0) {
            DeadByCube plugin = DeadByCube.getInstance();
            WorldAudioManager audioManager = plugin.getAudioManager();
            audioManager.playSound(SoundRegistry.OBJECT_GENERATOR_COMPLETED_LOOP, SoundCategory.MASTER, getLocation());
            this.soundTick = 70;
        }
    }

    private void onCompleted() {

        DeadByCube plugin = DeadByCube.getInstance();
        WorldAudioManager audioManager = plugin.getAudioManager();
        audioManager.playSound(SoundRegistry.OBJECT_GENERATOR_COMPLETED, SoundCategory.MASTER, getLocation());

        PlayerList playerList = plugin.getPlayerList();

        for (RepairInteraction repairInteraction : repairInteractions) {
            if (repairInteraction.isInteracting())
                repairInteraction.stopInteract();

            for (SurvivorPlayer survivorPlayer : playerList.getSurvivors()) {
                InteractionManager interactionManager = survivorPlayer.getInteractionManager();
                interactionManager.unregisterInteraction(InteractionActionBinding.ATTACK, repairInteraction);
            }
        }

        for (BreakInteraction breakInteraction : breakInteractions) {
            if (breakInteraction.isInteracting())
                breakInteraction.stopInteract();
            for (KillerPlayer killerPlayer : playerList.getKillers()) {
                InteractionManager interactionManager = killerPlayer.getInteractionManager();
                interactionManager.unregisterInteraction(InteractionActionBinding.ATTACK, breakInteraction);
            }
        }
    }

    public RepairInteraction[] getRepairInteractions() {
        return repairInteractions;
    }

    public BreakInteraction[] getBreakInteractions() {
        return breakInteractions;
    }

    public double getCharge() {
        return charge;
    }

    public double getMaxCharge() {
        return maxCharge;
    }

}
