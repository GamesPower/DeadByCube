package deadbycube.interaction;

import deadbycube.player.DeadByCubePlayer;
import deadbycube.util.ActionBar;
import deadbycube.util.Tickable;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.KeybindComponent;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.ArrayList;
import java.util.HashMap;

public class PlayerInteractionManager {

    private final DeadByCubePlayer deadByCubePlayer;
    private final ArrayList<Interaction> availableInteractionList = new ArrayList<>();

    private HashMap<InteractionActionBinding, Interaction> interactionMap = new HashMap<>();
    private Interaction currentInteraction;
    private boolean updated = true;
    private int lastUpdate = 0;

    public PlayerInteractionManager(DeadByCubePlayer deadByCubePlayer) {
        this.deadByCubePlayer = deadByCubePlayer;
        Tickable tickable = new Tickable(this::updateDisplay);
        tickable.startTask();
    }

    public void dispatch(InteractionActionBinding actionBinding) {
        if (currentInteraction != null)
            return;

        Interaction interaction = interactionMap.get(actionBinding);
        if (interaction != null) {
            System.out.print("Start interacting: ");
            System.out.println("interaction = " + interaction);
            this.currentInteraction = interaction;
            this.currentInteraction.startInteract(deadByCubePlayer);
        }
    }

    public void update() {
        HashMap<InteractionActionBinding, Interaction> interactionMap = new HashMap<>();
        for (Interaction availableInteraction : availableInteractionList) {
            if (availableInteraction.canInteract(deadByCubePlayer))
                interactionMap.put(availableInteraction.getActionBinding(), availableInteraction);
        }

        this.updated = !this.interactionMap.equals(interactionMap);
        this.interactionMap = interactionMap;
    }

    private void updateDisplay() {
        if (lastUpdate-- == 0 || updated) {
            createDisplay().send(deadByCubePlayer.getPlayer());
            this.lastUpdate = 40;
            this.updated = false;
        }
    }

    private ActionBar createDisplay() {
        ArrayList<BaseComponent> baseComponentList = new ArrayList<>();

        int i = 0;
        for (Interaction interaction : interactionMap.values()) {
            KeybindComponent keybindComponent = new KeybindComponent(interaction.getActionBinding().getKey());
            keybindComponent.setColor(ChatColor.GRAY);
            TextComponent spaceComponent = new TextComponent(" ");
            TextComponent actionComponent = new TextComponent(interaction.getName());
            actionComponent.setColor(ChatColor.WHITE);

            baseComponentList.add(keybindComponent);
            baseComponentList.add(spaceComponent);
            baseComponentList.add(actionComponent);

            if (++i < interactionMap.size())
                baseComponentList.add(new TextComponent("    "));
        }
        return new ActionBar(baseComponentList.toArray(new BaseComponent[baseComponentList.size()]));
    }

    public void registerInteraction(Interaction interaction) {
        this.availableInteractionList.add(interaction);
    }

}
