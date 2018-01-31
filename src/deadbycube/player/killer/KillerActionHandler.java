package deadbycube.player.killer;

import deadbycube.game.DeadByCubeGame;
import deadbycube.game.attack.KillerAttackManager;
import deadbycube.player.killer.power.Power;
import deadbycube.player.PlayerActionHandler;

public class KillerActionHandler extends PlayerActionHandler<KillerPlayer> {

    KillerActionHandler(KillerPlayer killer) {
        super(killer);
    }

    @Override
    public void attack() {
        DeadByCubeGame game = player.getPlugin().getGame();
        KillerAttackManager killerAttackManager = game.getAttackManager().getKillerAttackManager(player);
        if (killerAttackManager.canAttack())
            killerAttackManager.attack();
    }

    @Override
    public void interact() {
        Power power = player.getPower();
        if (power != null && power.canUse())
            power.use();
    }

}
