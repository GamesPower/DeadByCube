package deadbycube.interaction;

import net.md_5.bungee.api.chat.Keybinds;

public enum InteractionActionBinding {

    SNEAK(Keybinds.SNEAK),
    ATTACK(Keybinds.ATTACK),
    USE(Keybinds.USE),
    SWAP_HANDS(Keybinds.SWAP_HANDS),
    JUMP(Keybinds.JUMP),
    DROP(Keybinds.DROP);

    private final String key;

    InteractionActionBinding(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

}
