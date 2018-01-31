package deadbycube.game.attack;

import deadbycube.game.DeadByCubeGame;
import deadbycube.player.killer.KillerPlayer;

import java.util.HashMap;

public class AttackManager {

    public static final String LUNGE_SPEED_MODIFIER = "killer.attack.lunge";
    public static final String SUCCESSFUL_ATTACK_RECOVERY_SPEED_MODIFIER = "killer.attack.recovery.successful";
    public static final String MISSED_ATTACK_RECOVERY_SPEED_MODIFIER = "killer.attack.recovery.missed";

    private final DeadByCubeGame game;
    private final HashMap<KillerPlayer, KillerAttackManager> killerAttackManagerMap = new HashMap<>();

    public AttackManager(DeadByCubeGame game) {
        this.game = game;
    }

    public KillerAttackManager getKillerAttackManager(KillerPlayer killer) {
        return killerAttackManagerMap.computeIfAbsent(killer, KillerAttackManager::new);
    }

}
