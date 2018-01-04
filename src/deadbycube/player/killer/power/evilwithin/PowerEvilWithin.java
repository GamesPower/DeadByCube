package deadbycube.player.killer.power.evilwithin;

import deadbycube.audio.PlayerAudioManager;
import deadbycube.audio.SoundRegistry;
import deadbycube.player.killer.Killer;
import deadbycube.player.killer.power.Power;
import deadbycube.player.survivor.Survivor;
import org.bukkit.Location;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

abstract class PowerEvilWithin extends Power {

    private static final double MAX_STALK_DISTANCE = 40;
    private static final double MAX_STALK_ANGLE = Math.toRadians(30);

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
        audioManager.playSound(SoundRegistry.POWER_EVIL_WITHIN_STALK_ON, SoundCategory.PLAYERS, player.getLocation(), .2f, 1);
        audioManager.playSoundLater(SoundRegistry.POWER_EVIL_WITHIN_STALK_ON, SoundCategory.PLAYERS, player.getLocation(), .2f, 1, 15);
    }

    @Override
    protected void onUpdate() {
        Player killerPlayer = killer.getPlayer();
        for (Survivor survivor : killer.getPlugin().getPlayerList().getSurvivors()) {
            Player survivorPlayer = survivor.getPlayer();

            Location survivorEyeLocation = survivorPlayer.getEyeLocation();
            Location killerEyeLocation = killerPlayer.getEyeLocation();

            double distance = survivorEyeLocation.distance(killerEyeLocation);
            if (distance < MAX_STALK_DISTANCE) {
                Vector dirToDestination = survivorEyeLocation.toVector().subtract(killerEyeLocation.toVector());
                Vector playerDirection = killerEyeLocation.getDirection();

                if (dirToDestination.angle(playerDirection) < MAX_STALK_ANGLE) {
                    if (killerPlayer.hasLineOfSight(survivorPlayer)) {
                        stalked++;
                        onStalk();
                    }
                }
            }
        }
    }

    @Override
    protected void onStopUse() {
        PlayerAudioManager audioManager = killer.getAudioManager();
        Player player = killer.getPlayer();

        audioManager.playSound(SoundRegistry.POWER_EVIL_WITHIN_STALK_OFF, SoundCategory.PLAYERS, player.getLocation(), .2f, 1);
        audioManager.playSoundLater(SoundRegistry.POWER_EVIL_WITHIN_STALK_OFF, SoundCategory.PLAYERS, player.getLocation(), .2f, 1, 15);
    }

}
