package deadbycube.player;

public abstract class PlayerActionHandler<T extends DbcPlayer> {

    protected T player;

    public PlayerActionHandler(T player) {
        this.player = player;
    }

    public abstract void attack();

    public abstract void interact();

    public abstract void move();

    public abstract void toggleSneak(boolean sneaking);

}
