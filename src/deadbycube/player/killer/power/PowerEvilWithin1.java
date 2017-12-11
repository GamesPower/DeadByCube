package deadbycube.player.killer.power;

import deadbycube.player.PlayerManager;
import deadbycube.player.killer.Killer;
import deadbycube.player.survivor.Survivor;
import org.bukkit.entity.Player;

public class PowerEvilWithin1 extends Power {

    private static final float NORMAL_WALK_SPEED = 0.2f;
    private static final int REQUIRED_EVIL_WITHIN_PER_LEVEL = 200;

    private int level = 1;
    private int stalked = 0;

    public PowerEvilWithin1(Killer killer) {
        super(killer);

        killer.getPlayer().setWalkSpeed(NORMAL_WALK_SPEED);
    }

    @Override
    public void reset() {
    }

    @Override
    public boolean canUse() {
        return true;
    }

    @Override
    protected void onUse() {
        killer.getPlayer().sendMessage("Désolé j'ai eu la flemme de trouver comment tester si un survivant est dans ta vue");
        this.stopUsing();
    }

    @Override
    protected void onUpdate() {
        Player player = killer.getPlayer();

        for (Survivor survivor : PlayerManager.getSurvivors()) {
            if (player.hasLineOfSight(survivor.getPlayer())) {
                player.sendMessage("Stalked: " + stalked);
                if (++stalked >= REQUIRED_EVIL_WITHIN_PER_LEVEL) {
                    player.sendMessage("Level up: " + level);
                    this.level++;
                    this.stalked = 0;
                }
            }
        }
    }

    @Override
    protected void onStopUse() {

    }
}
