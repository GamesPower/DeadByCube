package deadbycube.player.killer;

import deadbycube.DeadByCube;
import deadbycube.player.killer.power.PowerBell;
import org.bukkit.Particle;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class KillerWraith extends Killer {

    public KillerWraith(DeadByCube plugin, Player player) {
        super(plugin, player, EntityType.BAT);
    }

    @Override
    protected void init() {
        this.setPower(new PowerBell(this));
    }

}
