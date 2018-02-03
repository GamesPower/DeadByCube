package deadbycube.player.killer.power;

import deadbycube.player.killer.KillerPlayer;
import deadbycube.util.MathUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class PowerSpencersLastBreath extends Power {

    private static final float WALK_SPEED = 0.245f;

    public PowerSpencersLastBreath(KillerPlayer killer) {
        super(killer);
    }

    @Override
    public void init() {
        super.init();

        killer.getWalkSpeed().setBaseValue(WALK_SPEED);
    }

    @Override
    public void reset() {
    }

}
