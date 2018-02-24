package deadbycube.player.killer.power;

import deadbycube.player.killer.KillerPlayer;
import deadbycube.registry.PowerRegistry;
import deadbycube.util.ItemStackBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;

public abstract class Power {

    protected final KillerPlayer killer;

    protected Power(KillerPlayer killer) {
        this.killer = killer;
    }

    public void init() {
        killer.setOffHandItem(new ItemStackBuilder(Material.SHIELD)
                .setData((byte) PowerRegistry.getID(this))
                .setFlags(ItemFlag.HIDE_UNBREAKABLE, ItemFlag.HIDE_ATTRIBUTES)
                .setUnbreakable(true)
                .build()
        );
    }

    public abstract void reset();

    public KillerPlayer getKiller() {
        return killer;
    }

}
