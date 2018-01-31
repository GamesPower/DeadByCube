package deadbycube.player.spectator;

import deadbycube.player.PlayerActionHandler;

public class SpectatorActionHandler extends PlayerActionHandler<SpectatorPlayer> {

    SpectatorActionHandler(SpectatorPlayer spectator) {
        super(spectator);
    }

    @Override
    public void attack() {
        player.getPlayer().sendMessage("ATTACK");
    }

    @Override
    public void interact() {
        player.getPlayer().sendMessage("INTERACT");
    }

}
