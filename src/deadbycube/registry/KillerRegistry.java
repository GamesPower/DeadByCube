package deadbycube.registry;

import deadbycube.DeadByCube;
import deadbycube.player.killer.*;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;

public enum KillerRegistry {

    DOCTOR(KillerDoctor.class),
    NURSE(KillerNurse.class),
    SHAPE(KillerShape.class),
    TRAPPER(KillerTrapper.class),
    WRAITH(KillerWraith.class);

    private final Class<? extends KillerPlayer> killerClass;

    KillerRegistry(Class<? extends KillerPlayer> killerClass) {
        this.killerClass = killerClass;
    }

    public KillerPlayer create(DeadByCube plugin, Player player) {
        try {
            return killerClass.getConstructor(DeadByCube.class, Player.class).newInstance(plugin, player);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new IllegalStateException("Unable to create an instance of " + killerClass.getSimpleName());
        }
    }

}
