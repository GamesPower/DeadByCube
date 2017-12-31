package deadbycube.player.actionhandler;

import deadbycube.player.DeadByCubePlayer;

public abstract class PlayerActionHandler<T extends DeadByCubePlayer> {

    protected T player;

    PlayerActionHandler(T player) {
        this.player = player;
    }

    public abstract void attack();

    public abstract void interact();

}
