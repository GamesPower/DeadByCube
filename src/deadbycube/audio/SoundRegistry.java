package deadbycube.audio;

public enum SoundRegistry {

    KILLER_DOCTOR_BREATH_TEETH("killer.doctor.breath.teeth"),
    KILLER_DOCTOR_LAUGH("killer.doctor.laugh"),

    KILLER_WRAITH_WEAPON_ARM("killer.wraith.weapon.arm"),

    POWER_CARTERS_SPARK_EXPLOSION("power.carters_spark.explosion"),

    POWER_CARTERS_SPARK_ATTACK("power.carters_spark.attack"),
    POWER_CARTERS_SPARK_ATTACK_DIRT("power.carters_spark.attack.dirt"),
    POWER_CARTERS_SPARK_ATTACK_ELECTRIC("power.carters_spark.attack.electric"),
    POWER_CARTERS_SPARK_ATTACK_GROUND("power.carters_spark.attack.ground"),
    POWER_CARTERS_SPARK_ATTACK_READY("power.carters_spark.attack.ready"),
    POWER_CARTERS_SPARK_ATTACK_BASS("power.carters_spark.attack.bass"),

    POWER_CARTERS_SPARK_CHARGE("power.carters_spark.charge"),
    POWER_CARTERS_SPARK_CHARGE_BASS("power.carters_spark.charge.bass"),
    POWER_CARTERS_SPARK_CHARGE_ELECTRIC("power.carters_spark.charge.electric"),
    POWER_CARTERS_SPARK_CHARGE_HIGH("power.carters_spark.charge.high"),

    POWER_EVIL_WITHIN_LEVEL_1("power.evil_within.level_1"),
    POWER_EVIL_WITHIN_LEVEL_2("power.evil_within.level_2"),
    POWER_EVIL_WITHIN_LEVEL_3("power.evil_within.level_3"),
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
