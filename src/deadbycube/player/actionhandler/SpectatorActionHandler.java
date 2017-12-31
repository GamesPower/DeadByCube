package deadbycube.player.actionhandler;

import deadbycube.player.spectator.Spectator;

public class SpectatorActionHandler extends PlayerActionHandler<Spectator> {

    public SpectatorActionHandler(Spectator spectator) {
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
