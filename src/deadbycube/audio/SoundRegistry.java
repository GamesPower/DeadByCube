package deadbycube.audio;

public enum SoundRegistry {

    KILLER_NURSE_BREATH("killer.nurse.breath"),
    KILLER_SHAPE_BREATH("killer.shape.breath"),
    KILLER_TRAPPER_BREATH("killer.trapper.breath"),
    KILLER_WRAITH_BREATH("killer.wraith.breath"),
    KILLER_WRAITH_WEAPON_ARM("killer.wraith.weapon.arm"),

    POWER_EVIL_WITHIN_LEVEL_1("power.evil_within.level.1"),
    POWER_EVIL_WITHIN_LEVEL_2("power.evil_within.level.2"),
    POWER_EVIL_WITHIN_LEVEL_3("power.evil_within.level.3"),
    POWER_EVIL_WITHIN_STALK_OFF("power.evil_within.stalk.off"),
    POWER_EVIL_WITHIN_STALK_ON("power.evil_within.stalk.on"),
    POWER_EVIL_WITHIN_STALK_WARNING("power.evil_within.stalk.warning"),

    POWER_INVISIBILITY_BELL_BELL_IRON("power.invisibility_bell.bell.iron"),
    POWER_INVISIBILITY_BELL_BELL_RING("power.invisibility_bell.bell.ring"),
    POWER_INVISIBILITY_BELL_BELL_ROCK_HIT("power.invisibility_bell.bell.rock.hit"),
    POWER_INVISIBILITY_BELL_BELL_SINGLE("power.invisibility_bell.bell.single"),
    POWER_INVISIBILITY_BELL_BELL_SLEIGH("power.invisibility_bell.bell.sleigh"),
    POWER_INVISIBILITY_BELL_BURN("power.invisibility_bell.burn"),
    POWER_INVISIBILITY_BELL_CRACKS("power.invisibility_bell.cracks"),
    POWER_INVISIBILITY_BELL_DISSOLVE("power.invisibility_bell.dissolve"),
    POWER_INVISIBILITY_BELL_INVISIBLE("power.invisibility_bell.invisible"),
    POWER_INVISIBILITY_BELL_VISIBLE("power.invisibility_bell.visible"),

    SURVIVOR_HEARTBEAT("survivor.heartbeat");

    private final String key;

    SoundRegistry(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

}
