package deadbycube.interaction;

import deadbycube.player.DeadByCubePlayer;
import deadbycube.util.ActionBar;
import deadbycube.util.TextComponentUtil;
import deadbycube.util.Tickable;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.KeybindComponent;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.ArrayList;
import java.util.HashMap;

public class PlayerInteractionManager {

    private final ArrayList<Interaction> availableInteractionList = new ArrayList<>();
    private final DeadByCubePlayer deadByCubePlayer;
    private final Tickable tickable;

    private HashMap<InteractionActionBinding, Interaction> interactionMap = new HashMap<>();

    private Interaction interaction;
    private boolean interacting = false;

    private boolean updateDisplay = true;
    private byte lastDisplayUpdate = 0;

    public PlayerInteractionManager(DeadByCubePlayer deadByCubePlayer) {
        this.deadByCubePlayer = deadByCubePlayer;
        this.tickable = new Tickable(this::updateDisplay);
    }

    private void updateDisplay() {
        if (interacting && (interaction == null || !interaction.isInteracting(deadByCubePlayer))) {
            this.updateDisplay = true;
            this.interacting = false;
        }

        if (--lastDisplayUpdate == 0 || updateDisplay) {

            ActionBar actionBar;
            if (interacting)
                actionBar = new ActionBar();
            else {
                ArrayList<BaseComponent> baseComponentList = new ArrayList<>();
                int i = 0;
                for (Interaction interaction : interactionMap.values()) {

                    baseComponentList.add(TextComponentUtil.create("[", ChatColor.DARK_GRAY));
                    KeybindComponent keybindComponent = new KeybindComponent(interaction.getActionBinding().getKey());
                    keybindComponent.setColor(ChatColor.GRAY);
                    baseComponentList.add(keybindComponent);
                    baseComponentList.add(TextComponentUtil.create("] ", ChatColor.DARK_GRAY));
                    baseComponentList.add(TextComponentUtil.create(interaction.getName(), ChatColor.WHITE));

                    if (++i < interactionMap.size())
                        baseComponentList.add(new TextComponent("    "));
                }
                actionBar = new ActionBar(baseComponentList.toArray(new BaseComponent[baseComponentList.size()]));
            }
            actionBar.send(deadByCubePlayer.getPlayer());

            this.lastDisplayUpdate = 40;
            this.updateDisplay = false;
        }
    }

    public void init() {
        tickable.startTask();
    }

    public void reset() {
        if (interaction != null && interaction.isInteracting())
            interaction.stopInteract();
    }

    public void dispatch(InteractionActionBinding actionBinding) {
        if (interacting)
            return;

        Interaction interaction = interactionMap.get(actionBinding);
        if (interaction != null) {
            this.interaction = interaction;
            this.interaction.interact(deadByCubePlayer);
            this.interacting = true;
            this.updateDisplay = true;
        }
    }

    public void updateInteractions() {
        HashMap<InteractionActionBinding, Interaction> interactionMap = new HashMap<>();
        for (Interaction availableInteraction : availableInteractionList) {
            if (availableInteraction.canInteract(deadByCubePlayer))
                interactionMap.put(availableInteraction.getActionBinding(), availableInteraction);
        }

        if (!this.interactionMap.equals(interactionMap)) {
            this.interactionMap = interactionMap;
            this.updateDisplay = true;
        }
    }

    public void registerInteraction(Interaction interaction) {
        this.availableInteractionList.add(interaction);
    }

    public void unregisterInteraction(Interaction interaction) {
        this.availableInteractionList.remove(interaction);
    }

}
