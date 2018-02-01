package deadbycube.util;

import org.bukkit.Bukkit;

import java.lang.reflect.Field;

public class NMSUtils {

    private static final String VERSION = getVersion();
    private static final String NMS_PACKAGE = "net.minecraft.server." + VERSION + '.';

    private static String getVersion() {
        return Bukkit.getServer().getClass().getPackage().getName().substring("org.bukkit.craftbukkit.".length());
    }

    public static Object getField(Object nmsObject, String fieldName) {
        try {
            Field field = nmsObject.getClass().getField(fieldName);
            field.setAccessible(true);
            return field.get(nmsObject);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new IllegalStateException("Unable to get the field from " + nmsObject.getClass().getName());
        }
    }

    public static Object getStaticField(String nmsClassName, String fieldName) {
        try {
            Field field = getNMSClass(nmsClassName).getField(fieldName);
            field.setAccessible(true);
            return field.get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new IllegalStateException("Unable to get the field from " + nmsClassName + " (version: " + VERSION + ")");
        }
    }

    public static Class<?> getNMSClass(String nmsClassName) {
        try {
            return Class.forName(NMS_PACKAGE + nmsClassName);
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Unable to get the NMS class \"" + nmsClassName + "\" (version: " + VERSION + ")");
        }
    }

}
