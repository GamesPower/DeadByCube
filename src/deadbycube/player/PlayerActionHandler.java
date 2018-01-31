package deadbycube.player;

public abstract class PlayerActionHandler<T extends DeadByCubePlayer> {

    protected T player;

    protected PlayerActionHandler(T player) {
        this.player = player;
    }

    public abstract void attack();

    public abstract void interact();

}
