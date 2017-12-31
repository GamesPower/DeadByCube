package deadbycube.player.killer.power.evilwithin;

import deadbycube.audio.PlayerAudioManager;
import deadbycube.audio.SoundRegistry;
import deadbycube.player.killer.Killer;
import deadbycube.player.killer.power.Power;
import deadbycube.player.survivor.Survivor;
import org.bukkit.SoundCategory;
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
        PlayerAudioManager audioManager = killer.getAudioManager();
        Player player = killer.getPlayer();
        audioManager.playSound(SoundRegistry.KILLER_SHAPE_STALK_ON, SoundCategory.PLAYERS, player.getLocation(), 1, 1);
        audioManager.playSoundLater(SoundRegistry.KILLER_SHAPE_STALK_ON, SoundCategory.PLAYERS, player.getLocation(), 1, 1, 15);
    }

    @Override
    protected void onUpdate() {
        stalked++;
        this.onStalk();

        //Player player = killer.getPlayer();
        /*for (Survivor survivor : killer.getPlugin().getPlayerList().getSurvivors()) {
            if (player.hasLineOfSight(survivor.getPlayer())) {
                stalked++;
                this.onStalk();
            }
        }*/
    }

    @Override
    protected void onStopUse() {
        PlayerAudioManager audioManager = killer.getAudioManager();
        Player player = killer.getPlayer();

        audioManager.playSound(SoundRegistry.KILLER_SHAPE_STALK_OFF, SoundCategory.PLAYERS, player.getLocation(), 1, 1);
        audioManager.playSoundLater(SoundRegistry.KILLER_SHAPE_STALK_OFF, SoundCategory.PLAYERS, player.getLocation(), 1, 1, 15);
    }

}
