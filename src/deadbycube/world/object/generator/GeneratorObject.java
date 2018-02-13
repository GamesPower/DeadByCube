package deadbycube.world.object.generator;

import deadbycube.DeadByCube;
import deadbycube.interaction.InteractionActionBinding;
import deadbycube.player.survivor.SurvivorPlayer;
import deadbycube.util.MagicalValue;
import deadbycube.util.TickLoop;
import deadbycube.world.object.WorldObject;
import deadbycube.world.object.generator.interaction.BreakInteraction;
import deadbycube.world.object.generator.interaction.RepairInteraction;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;

public class GeneratorObject extends WorldObject {

    private static final double CHARGE = 1600d;

    private final TickLoop tickLoop;
    private final RepairInteraction[] repairInteractions;
    private final BreakInteraction[] breakInteractions;

    private boolean completed = false;
    private double charge = 0d;
    private double maxCharge = CHARGE;

    public GeneratorObject(Location location, BlockFace... faces) {
        super(location);

        this.tickLoop = new TickLoop(this::update);

        this.repairInteractions = new RepairInteraction[faces.length];
        this.breakInteractions = new BreakInteraction[faces.length];
        for (int i = 0; i < faces.length; i++) {
            BlockFace face = faces[i];
            Location interactionLocation = location.clone().add(face.getModX() * 0.6, face.getModY() * 0.6, face.getModZ() * 0.6);
            this.repairInteractions[i] = new RepairInteraction(this, face, interactionLocation, .8, 60);
            this.breakInteractions[i] = new BreakInteraction(this, interactionLocation, .8, 60);
        }
    }

    public void init() {
        this.tickLoop.startTask();
    }

    private void update() {
        for (int i = 0; i < repairInteractions.length; i++) {
            RepairInteraction repairInteraction = repairInteractions[i];
            if (repairInteraction != null && repairInteraction.isInteracting()) {
                SurvivorPlayer survivor = (SurvivorPlayer) repairInteraction.getPlayer();
                MagicalValue repairSpeed = survivor.getRepairSpeed();
                if (i > 1)
                    this.charge += repairSpeed.getValue() * 0.9;
                else
                    this.charge += repairSpeed.getValue();
            }
        }

        if (!completed && charge >= maxCharge) {
            this.completed = true;
            this.charge = maxCharge;
            this.onCompleted();
        }
    }

    private void onCompleted() {
        for (RepairInteraction repairInteraction : repairInteractions) {
            if (repairInteraction.isInteracting()) {
                repairInteraction.stopInteract();
                DeadByCube.getInstance().getPlayerList().getSurvivors().forEach(survivorPlayer ->
                        survivorPlayer.getInteractionManager().unregisterInteraction(InteractionActionBinding.ATTACK, repairInteraction)
                );
            }
        }
        for (BreakInteraction breakInteraction : breakInteractions) {
            if (breakInteraction.isInteracting()) {
                breakInteraction.stopInteract();
                DeadByCube.getInstance().getPlayerList().getKillers().forEach(survivorPlayer ->
                        survivorPlayer.getInteractionManager().unregisterInteraction(InteractionActionBinding.ATTACK, breakInteraction)
                );
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
