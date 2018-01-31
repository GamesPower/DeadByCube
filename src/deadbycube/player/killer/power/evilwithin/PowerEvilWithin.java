package deadbycube.player.killer.power.evilwithin;

import deadbycube.DeadByCube;
import deadbycube.audio.PlayerAudioManager;
import deadbycube.audio.SoundRegistry;
import deadbycube.player.killer.KillerPlayer;
import deadbycube.player.killer.power.Power;
import deadbycube.player.survivor.SurvivorPlayer;
import deadbycube.util.EntityUtils;
import deadbycube.util.Progression;
import org.bukkit.Location;
import org.bukkit.SoundCategory;
import org.bukkit.boss.BarColor;
import org.bukkit.entity.Player;

abstract class PowerEvilWithin extends Power {

    private static final double MIN_DISTANCE = 0;
    private static final double MAX_DISTANCE = 40;
    private static final double FIELD_OF_VIEW = 80;

    final Progression progression;

    private final int required;
    private int stalked = 0;
    private int soundTick = 0;

    PowerEvilWithin(KillerPlayer killer, int required) {
        super(killer);
        this.required = required;

        this.progression = new Progression("power.evil_within.action", BarColor.WHITE);
        this.progression.setMaxValue(required);
    }

    @Override
    public void init(boolean using) {
        super.init(using);

        this.progression.display(killer);
    }

    @Override
    public void reset() {
        this.progression.reset();

        super.reset();
    }

    abstract void onNextLevel();

    @Override
    public boolean canUse() {
        return true;
    }

    @Override
    protected void onUse() {
        PlayerAudioManager audioManager = killer.getAudioManager();
        Player player = killer.getPlayer();

        int currentTick = DeadByCube.getCurrentTick();
        if (currentTick - soundTick < 30)
            audioManager.stopSound(SoundRegistry.POWER_EVIL_WITHIN_STALK_OFF);
        this.soundTick = currentTick;

        audioManager.playSound(SoundRegistry.POWER_EVIL_WITHIN_STALK_ON, SoundCategory.PLAYERS, player.getLocation(), .2f, 1);
    }

    @Override
    protected void onUpdate() {
        Player killerPlayer = killer.getPlayer();
        for (SurvivorPlayer survivor : killer.getPlugin().getPlayerList().getSurvivors()) {
            Player survivorPlayer = survivor.getPlayer();

            Location survivorEyeLocation = survivorPlayer.getEyeLocation();
            Location killerEyeLocation = killerPlayer.getEyeLocation();

            if (EntityUtils.inFieldOfView(killerEyeLocation, survivorEyeLocation, FIELD_OF_VIEW, MIN_DISTANCE, MAX_DISTANCE) && killerPlayer.hasLineOfSight(survivorPlayer)) {

                if (++stalked == required) {
                    this.onNextLevel();
                    this.stopUse();
                }
                this.progression.setValue(stalked);

                return;
            }
        }
    }

    @Override
    protected void onStopUse() {
        PlayerAudioManager audioManager = killer.getAudioManager();
        Player player = killer.getPlayer();

        int currentTick = DeadByCube.getCurrentTick();
        if (currentTick - soundTick < 30)
            audioManager.stopSound(SoundRegistry.POWER_EVIL_WITHIN_STALK_ON);
        this.soundTick = currentTick;

        audioManager.playSound(SoundRegistry.POWER_EVIL_WITHIN_STALK_OFF, SoundCategory.PLAYERS, player.getLocation(), .2f, 1);
    }

}
