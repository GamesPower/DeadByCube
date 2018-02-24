package deadbycube.interaction;

import deadbycube.player.DeadByCubePlayer;
import deadbycube.util.ActionBar;
import deadbycube.util.TextComponentUtil;
import deadbycube.util.TickLoop;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.KeybindComponent;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InteractionManager {

    private static final int ACTION_BAR_SYNC_TICK = 40;

    private final HashMap<InteractionActionBinding, InteractionRegistry> interactionRegistryMap = new HashMap<>();
    private final DeadByCubePlayer deadByCubePlayer;
    private final TickLoop tickLoop;

    private HashMap<InteractionActionBinding, Interaction> interactionMap = new HashMap<>();

    private Interaction interaction;
    private boolean interacting = false;

    private boolean shouldUpdateInteractions = false;
    private byte lastDisplayUpdate = 0;

    public InteractionManager(DeadByCubePlayer deadByCubePlayer) {
        this.deadByCubePlayer = deadByCubePlayer;
        this.tickLoop = new TickLoop(this::update);
    }

    private void update() {
        this.updateCurrentInteractionStatus();

        if (--lastDisplayUpdate == 0)
            this.updateDisplay();

        if (shouldUpdateInteractions)
            this.updateInteractions();
    }

    private void updateDisplay() {
        ActionBar actionBar;
        if (interacting)
            actionBar = new ActionBar();
        else {
            ArrayList<BaseComponent> baseComponentList = new ArrayList<>();
            int i = 0;
            for (Map.Entry<InteractionActionBinding, Interaction> entry : interactionMap.entrySet()) {

                baseComponentList.add(TextComponentUtil.create("[", ChatColor.DARK_GRAY));
                KeybindComponent keybindComponent = new KeybindComponent(entry.getKey().getKey());
                keybindComponent.setColor(ChatColor.GRAY);
                baseComponentList.add(keybindComponent);
                baseComponentList.add(TextComponentUtil.create("] ", ChatColor.DARK_GRAY));
                baseComponentList.add(TextComponentUtil.create(entry.getValue().getName(), ChatColor.WHITE));

                if (++i < interactionMap.size())
                    baseComponentList.add(new TextComponent("    "));
            }
            actionBar = new ActionBar(baseComponentList.toArray(new BaseComponent[baseComponentList.size()]));
        }
        actionBar.send(deadByCubePlayer.getPlayer());

        this.lastDisplayUpdate = ACTION_BAR_SYNC_TICK;
    }

    private void updateCurrentInteractionStatus() {
        if (interacting && (interaction == null || !interaction.isInteracting())) {
            this.interacting = false;
            this.updateInteractions();
            this.updateDisplay();
        }
    }

    public void updateInteractions() {
        HashMap<InteractionActionBinding, Interaction> interactionMap = new HashMap<>();
        interactionRegistryMap.forEach((actionBinding, interactionRegistry) -> {
            Interaction interaction = interactionRegistry.getMainInteraction(deadByCubePlayer);
            if (interaction != null)
                interactionMap.put(actionBinding, interaction);

        });

        if (!this.interactionMap.equals(interactionMap)) {
            this.interactionMap = interactionMap;
            this.updateDisplay();
        }

        this.shouldUpdateInteractions = false;
    }

    public void init() {
        tickLoop.startTask();
    }

    public void reset() {
        if (interaction != null && interaction.isInteracting())
            interaction.stopInteract();
    }

    public void dispatch(InteractionActionBinding actionBinding) {
        Interaction interaction = interactionMap.get(actionBinding);
        if (interaction != null)
            this.interact(interaction);
    }

    public void interact(Interaction interaction) {
        this.updateCurrentInteractionStatus();

        if (!interacting) {
            this.interaction = interaction;
            this.interaction.interact(deadByCubePlayer);
            this.interacting = true;
            this.updateDisplay();
        }
    }

    public void registerInteraction(InteractionActionBinding actionBinding, Interaction interaction) {
        InteractionRegistry interactionRegistry = interactionRegistryMap.computeIfAbsent(actionBinding, actionBinding1 -> new InteractionRegistry());
        if (!interactionRegistry.isRegistered(interaction)) {
            interactionRegistry.register(interaction);
            this.shouldUpdateInteractions = true;
        }
    }

    public void unregisterInteraction(InteractionActionBinding actionBinding, Interaction interaction) {
        InteractionRegistry interactionRegistry = interactionRegistryMap.computeIfAbsent(actionBinding, actionBinding1 -> new InteractionRegistry());
        interactionRegistry.unregister(interaction);
        this.shouldUpdateInteractions = true;
    }

}
