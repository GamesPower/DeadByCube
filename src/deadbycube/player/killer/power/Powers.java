package deadbycube.player.killer.power;

import deadbycube.player.killer.power.evilwithin.PowerEvilWithin1;
import deadbycube.player.killer.power.evilwithin.PowerEvilWithin2;
import deadbycube.player.killer.power.evilwithin.PowerEvilWithin3;

public enum Powers {

    BUBBAS_CHAINSAW(PowerBubbasChainsaw.class),
    EVIL_WITHIN_1(PowerEvilWithin1.class),
    EVIL_WITHIN_2(PowerEvilWithin2.class),
    EVIL_WITHIN_3(PowerEvilWithin3.class),
    BLACKENED_CATALYST(PowerBlackenedCatalyst.class),
    CARTERS_SPARK(PowerCartersSpark.class),
    HUNTING_HATCHETS(PowerHuntingHatchets.class),
    DREAM_MASTER(PowerDreamMaster.class),
    BELL(PowerBell.class),
    BREATH(PowerBreath.class),
    CHAINSAW(PowerChainsaw.class),
    TRAP(PowerTrap.class);

    private final Class<? extends Power> powerClass;

    Powers(Class<? extends Power> powerClass) {
        this.powerClass = powerClass;
    }

    public String getName() {
        return name().toLowerCase();
    }

    public Class<? extends Power> getPowerClass() {
        return powerClass;
    }
}
