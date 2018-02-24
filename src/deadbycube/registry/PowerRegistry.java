package deadbycube.registry;

import deadbycube.player.DeadByCubePlayer;
import deadbycube.player.PlayerType;
import deadbycube.player.killer.KillerPlayer;
import deadbycube.player.killer.power.*;
import deadbycube.player.killer.power.beartrap.PowerBearTrap;
import deadbycube.player.killer.power.cartersspark.PowerCartersSpark;
import deadbycube.player.killer.power.dreammaster.PowerDreamMaster;
import deadbycube.player.killer.power.evilwithin.PowerEvilWithin1;
import deadbycube.player.killer.power.evilwithin.PowerEvilWithin2;
import deadbycube.player.killer.power.evilwithin.PowerEvilWithin3;
import deadbycube.player.killer.power.huntinghatchets.PowerHuntingHatchets;
import deadbycube.player.killer.power.invisibilitybell.PowerInvisibilityBell;
import deadbycube.player.killer.power.spencerslastbreath.PowerSpencersLastBreath;

import java.lang.reflect.InvocationTargetException;

public enum PowerRegistry {

    BEAR_TRAP(PowerBearTrap.class),
    INVISIBILITY_BELL(PowerInvisibilityBell.class),
    CHAINSAW(PowerChainsaw.class),
    SPENCERS_LAST_BREATH(PowerSpencersLastBreath.class),
    HUNTING_HATCHETS(PowerHuntingHatchets.class),
    EVIL_WITHIN_1(PowerEvilWithin1.class),
    EVIL_WITHIN_2(PowerEvilWithin2.class),
    EVIL_WITHIN_3(PowerEvilWithin3.class),
    BLACKENED_CATALYST(PowerBlackenedCatalyst.class),
    BUBBAS_CHAINSAW(PowerBubbasChainsaw.class),
    CARTERS_SPARK(PowerCartersSpark.class),
    DREAM_MASTER(PowerDreamMaster.class);

    private final Class<? extends Power> powerClass;

    PowerRegistry(Class<? extends Power> powerClass) {
        this.powerClass = powerClass;
    }

    public static int getID(Power power) {
        for (PowerRegistry powerRegistry : PowerRegistry.values()) {
            if (powerRegistry.powerClass.equals(power.getClass()))
                return powerRegistry.ordinal();
        }
        return -1;
    }

    public String getName() {
        return name().toLowerCase();
    }

    public Power create(KillerPlayer killer) {
        try {
            return powerClass.getConstructor(KillerPlayer.class).newInstance(killer);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new IllegalStateException("Unable to create an instance of " + powerClass.getSimpleName());
        }
    }

    public boolean hasPower(DeadByCubePlayer deadByCubePlayer) {
        if (deadByCubePlayer.getType() != PlayerType.KILLER)
            return false;
        KillerPlayer killer = (KillerPlayer) deadByCubePlayer;
        Power power = killer.getPower();
        return powerClass.equals(power.getClass());
    }

}
