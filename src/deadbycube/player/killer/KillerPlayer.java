package deadbycube.player.killer;

import deadbycube.DeadByCube;
import deadbycube.audio.PlayerAudioManager;
import deadbycube.audio.music.MusicManager;
import deadbycube.audio.music.MusicRegistry;
import deadbycube.player.killer.power.Power;
import deadbycube.player.killer.power.PowerRegistry;
import deadbycube.player.DeadByCubePlayer;
import deadbycube.player.PlayerType;
import deadbycube.player.survivor.heartbeat.HeartbeatEmitter;
import deadbycube.util.MagicalValue;
import deadbycube.util.Tickable;
import org.bukkit.Location;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;

public abstract class KillerPlayer extends DeadByCubePlayer implements HeartbeatEmitter {

    private static final int FOOD_LEVEL = 0;
    private static final double WALK_SPEED = 0.3;

    private final String name;
    private final Tickable tickable;

    private final MagicalValue terrorRadius = new MagicalValue(32d);
    private final MagicalValue breathVolume = new MagicalValue(0.1f);


    private Power power;

    protected KillerPlayer(DeadByCube plugin, Player player, String name, PowerRegistry power) {
        super(plugin, player);
        this.name = name;
        this.tickable = new Tickable(this::update);

        this.power = power.create(this);
    }

    void playBreathSound() {
        plugin.getAudioManager().playSound("killer." + name + ".breath", SoundCategory.VOICE, player.getLocation(), (float) getBreathVolume().getValue(), 1);
    }

    abstract void update();

    @Override
    public void init() {
        super.init();

        walkSpeed().setBaseValue(WALK_SPEED);
        player.setFoodLevel(FOOD_LEVEL);

        PlayerAudioManager audioManager = getAudioManager();
        MusicManager musicManager = audioManager.getMusicManager();
        musicManager.setMusics(MusicRegistry.KILLER_NORMAL);

        this.tickable.startTask();
        this.power.init(false);
    }

    @Override
    public final KillerActionHandler createActionHandler() {
        return new KillerActionHandler(this);
    }

    @Override
    public final PlayerType getType() {
        return PlayerType.KILLER;
    }

    @Override
    public Location getLocation() {
        return player.getLocation();
    }

    @Override
    public MagicalValue getTerrorRadius() {
        return terrorRadius;
    }

    public MagicalValue getBreathVolume() {
        return breathVolume;
    }

    public Power getPower() {
        return power;
    }

    public void setPower(PowerRegistry power) {
        boolean using = false;
        if (this.power != null) {
            using = this.power.isUsing();
            this.power.reset();
        }
        this.power = power.create(this);
        this.power.init(using);
    }

    public String getName() {
        return name;
    }

}
