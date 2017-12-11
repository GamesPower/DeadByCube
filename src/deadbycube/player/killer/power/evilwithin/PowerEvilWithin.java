package deadbycube.player.killer.power.evilwithin;

import deadbycube.player.PlayerManager;
import deadbycube.player.killer.Killer;
import deadbycube.player.killer.power.Power;
import deadbycube.player.survivor.Survivor;
import org.bukkit.entity.Player;

abstract class PowerEvilWithin extends Power {

    int stalked = 0;

    PowerEvilWithin(Killer killer) {
        super(killer);
    }

    abstract void onStalk();

    @Override
    public boolean canUse() {
        return true;
    }

    @Override
    protected void onUse() {
    }

    @Override
    protected void onUpdate() {
        Player player = killer.getPlayer();

        for (Survivor survivor : PlayerManager.getSurvivors()) {
            if (player.hasLineOfSight(survivor.getPlayer())) {
                stalked++;
                this.onStalk();
                player.sendMessage("Stalk: " + stalked);
            }
        }
    }

    @Override
    protected void onStopUse() {
    }

}
