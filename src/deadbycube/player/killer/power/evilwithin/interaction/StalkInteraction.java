package deadbycube.player.killer.power.evilwithin.interaction;

import deadbycube.DeadByCube;
import deadbycube.audio.PlayerAudioManager;
import deadbycube.interaction.Interaction;
import deadbycube.player.killer.KillerPlayer;
import deadbycube.player.killer.power.evilwithin.PowerEvilWithin;
import deadbycube.player.survivor.SurvivorPlayer;
import deadbycube.registry.SoundRegistry;
import deadbycube.util.EntityUtils;
import org.bukkit.Location;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;

public class StalkInteraction extends Interaction {

    private static final double MIN_DISTANCE = 0;
    private static final double MAX_DISTANCE = 40;
    private static final double FIELD_OF_VIEW = 70;

    private final PowerEvilWithin power;
    private int soundTick = 0;
    private int stalk = 0;

    public StalkInteraction(PowerEvilWithin power) {
        super("stalk");

        this.power = power;
    }

    @Override
    protected void onInteract() {
        KillerPlayer killer = power.getKiller();
        PlayerAudioManager audioManager = killer.getAudioManager();
        Player player = killer.getPlayer();

        int currentTick = DeadByCube.getCurrentTick();
        if (currentTick - soundTick < 30)
            audioManager.stopSound(SoundRegistry.POWER_EVIL_WITHIN_STALK_OFF);
        this.soundTick = currentTick;

        audioManager.playSound(SoundRegistry.POWER_EVIL_WITHIN_STALK_ON, SoundCategory.PLAYERS, player.getLocation(), .2f);
    }

    @Override
    protected void onUpdate(int stalkTick) {
        KillerPlayer killer = power.getKiller();
        Player killerPlayer = killer.getPlayer();
        for (SurvivorPlayer survivor : killer.getPlugin().getPlayerList().getSurvivors()) {
            Player survivorPlayer = survivor.getPlayer();

            Location survivorEyeLocation = survivorPlayer.getEyeLocation();
            Location killerEyeLocation = killerPlayer.getEyeLocation();

            if (EntityUtils.inFieldOfView(killerEyeLocation, survivorEyeLocation, FIELD_OF_VIEW, MIN_DISTANCE, MAX_DISTANCE) && killerPlayer.hasLineOfSight(survivorPlayer)) {
                killerPlayer.sendMessage("Stalking " + survivorPlayer.getDisplayName() + " (" + stalk + ")");
                power.onStalk(++stalk);
                return;
            }
        }
    }


    @Override
    protected void onStopInteract(int stalkTick) {
        KillerPlayer killer = power.getKiller();
        PlayerAudioManager audioManager = killer.getAudioManager();
        Player player = killer.getPlayer();

        int currentTick = DeadByCube.getCurrentTick();
        if (currentTick - soundTick < 30)
            audioManager.stopSound(SoundRegistry.POWER_EVIL_WITHIN_STALK_ON);
        this.soundTick = currentTick;

        audioManager.playSound(SoundRegistry.POWER_EVIL_WITHIN_STALK_OFF, SoundCategory.PLAYERS, player.getLocation(), .2f);
    }

    @Override
    public boolean isInteracting() {
        return super.isInteracting() && interactor.isSneaking();
    }
}
