package deadbycube.registry;

import deadbycube.gui.Gui;
import deadbycube.gui.GuiSelectKiller;
import deadbycube.gui.GuiSelectRole;

import java.lang.reflect.InvocationTargetException;

public enum GuiRegistry {

    SELECT_ROLE(GuiSelectRole.class),
    SELECT_KILLER(GuiSelectKiller.class);

    private final Class<? extends Gui> guiClass;

    GuiRegistry(Class<? extends Gui> guiClass) {
        this.guiClass = guiClass;
    }

    public Gui create() {
        try {
            return guiClass.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            return null;
        }
    }

}
