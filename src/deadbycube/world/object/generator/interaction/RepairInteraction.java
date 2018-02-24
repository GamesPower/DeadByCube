package deadbycube.world.object.generator.interaction;

import deadbycube.interaction.WorldInteraction;
import deadbycube.player.DeadByCubePlayer;
import deadbycube.player.PlayerType;
import deadbycube.player.survivor.SurvivorPlayer;
import deadbycube.util.MagicalValue;
import deadbycube.util.ProgressBar;
import deadbycube.world.object.generator.GeneratorObject;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.BlockFace;
import org.bukkit.boss.BarColor;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class RepairInteraction extends WorldInteraction {

    private final GeneratorObject generatorObject;
    private final ArmorStand armorStand;
    private final ProgressBar progressBar;

    public RepairInteraction(GeneratorObject generatorObject, BlockFace face, Location location, double distance, double angle) {
        super("repair", location, distance, angle, PlayerType.SURVIVOR);
        this.generatorObject = generatorObject;
        this.progressBar = new ProgressBar("repair", BarColor.WHITE);

        this.armorStand = (ArmorStand) location.getWorld().spawnEntity(
                location.clone()
                        .add(face.getModX() * .75, (face.getModY() * .75), face.getModZ() * .75)
                        .setDirection(generatorObject.getLocation().clone().subtract(location).toVector())
                , EntityType.ARMOR_STAND);
        this.armorStand.setGravity(false);
        this.armorStand.setVisible(false);
        this.armorStand.setInvulnerable(true);
        this.armorStand.setMarker(true);
        this.armorStand.setSmall(true);
        this.armorStand.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(0);
    }

    @Override
    public boolean canInteract(DeadByCubePlayer deadByCubePlayer) {
        return super.canInteract(deadByCubePlayer);
    }

    @Override
    protected void onInteract() {
        this.progressBar.display(interactor.getPlayer());
        Player player = interactor.getPlayer();
        armorStand.addPassenger(player);
    }

    @Override
    protected void onUpdate(int repairTick) {
        SurvivorPlayer survivorPlayer = (SurvivorPlayer) this.interactor;
        MagicalValue repairSpeed = survivorPlayer.getRepairSpeed();

        if (repairTick % 2 == 0) {
            this.progressBar.setColorFromValue(repairSpeed);
            this.progressBar.setMaxValue(generatorObject.getMaxCharge());
            this.progressBar.setValue(generatorObject.getCharge());
        }

        // TODO Play repair sounds
    }

    @Override
    protected void onStopInteract(int repairTick) {
        Player player = interactor.getPlayer();
        if (armorStand.getPassengers().contains(player))
            armorStand.removePassenger(player);
        this.progressBar.reset(interactor.getPlayer());
    }

    @Override
    public boolean isInteracting() {
        return super.isInteracting() && armorStand.getPassengers().contains(interactor.getPlayer());
    }
}
