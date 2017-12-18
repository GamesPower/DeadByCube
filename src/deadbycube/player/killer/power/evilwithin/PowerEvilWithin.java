package deadbycube.player.killer.power.evilwithin;

import deadbycube.audio.DbcSounds;
import deadbycube.audio.PlayerAudioManager;
import deadbycube.player.killer.Killer;
import deadbycube.player.killer.power.Power;
import deadbycube.player.survivor.Survivor;
import org.bukkit.Bukkit;
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
        Player player = killer.getPlayer();
        PlayerAudioManager audioManager = killer.getAudioManager();
        audioManager.playSound(DbcSounds.KILLER_SHAPE_STALK_ON, player.getLocation(), 1, 1);
        audioManager.playSoundLater(DbcSounds.KILLER_SHAPE_STALK_ON, player.getLocation(), 1, 1, 15);
    }

    @Override
    protected void onUpdate() {
        Player player = killer.getPlayer();

        for (Survivor survivor : killer.getPlugin().getPlayerList().getSurvivors()) {
            if (player.hasLineOfSight(survivor.getPlayer())) {
                stalked++;
                this.onStalk();
                player.sendMessage("Stalk: " + stalked);
            }
        }
    }

    @Override
    protected void onStopUse() {
        Player player = killer.getPlayer();
        PlayerAudioManager audioManager = killer.getAudioManager();
        Bukkit.getScheduler().runTaskLater(killer.getPlugin(), () -> {
            audioManager.playSound(DbcSounds.KILLER_SHAPE_STALK_OFF, player.getLocation(), 1, 1);
            audioManager.playSoundLater(DbcSounds.KILLER_SHAPE_STALK_OFF, player.getLocation(), 1, 1, 15);
        }, Math.min(0, stalked - 40));

    }

}
