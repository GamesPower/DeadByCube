package deadbycube.player.killer.huntress;

import deadbycube.DeadByCube;
import deadbycube.interaction.InteractionActionBinding;
import deadbycube.player.killer.KillerPlayer;
import deadbycube.player.killer.interaction.attack.AttackLungeInteraction;
import deadbycube.registry.PowerRegistry;
import deadbycube.registry.SkinRegistry;
import deadbycube.world.DeadByCubeWorld;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Minecart;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class KillerHuntress extends KillerPlayer {

    private static final Vector MINECART_VELOCITY = new Vector(.2, 0, 0);

    private final AttackLungeInteraction attackLungeInteraction;

    private Minecart minecart;

    public KillerHuntress(DeadByCube plugin, Player player) {
        super(plugin, player, "huntress", (byte) 30, SkinRegistry.DEFAULT, PowerRegistry.HUNTING_HATCHETS);

        this.attackLungeInteraction = new AttackHuntressLungeInteraction();
    }

    @Override
    public void init() {
        super.init();

        this.interactionManager.unregisterInteraction(InteractionActionBinding.ATTACK, getAttackLungeInteraction());
        this.interactionManager.registerInteraction(InteractionActionBinding.ATTACK, attackLungeInteraction);

        this.minecart = (Minecart) player.getWorld().spawnEntity(player.getLocation(), EntityType.MINECART);
        this.minecart.setInvulnerable(true);
        this.minecart.setGravity(false);
    }

    @Override
    public void reset() {
        super.reset();

        this.minecart.remove();
    }

    @Override
    protected void update() {
        super.update();

        this.minecart.setVelocity(MINECART_VELOCITY);

        Location location = player.getLocation();
        location.setY(DeadByCubeWorld.WORLD_Y - 1);
        this.minecart.teleport(location);
    }

}
