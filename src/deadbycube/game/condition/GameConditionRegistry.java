package deadbycube.game.condition;

import deadbycube.player.DeadByCubePlayer;
import deadbycube.player.PlayerType;
import deadbycube.player.killer.power.cartersspark.madness.MadnessTier;
import deadbycube.player.survivor.SurvivorPlayer;

public enum GameConditionRegistry {

    IN_MADNESS_3(deadByCubePlayer -> deadByCubePlayer.getType() == PlayerType.SURVIVOR && ((SurvivorPlayer) deadByCubePlayer).getMadnessManager().getTier() == MadnessTier.TIER_3);

    private final GameCondition condition;

    GameConditionRegistry(GameCondition condition) {
        this.condition = condition;
    }

    public boolean test(DeadByCubePlayer deadByCubePlayer) {
        return condition.test(deadByCubePlayer);
    }

}
