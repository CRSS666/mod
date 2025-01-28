package cc.crss.mod.bridge;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.message.v1.ServerMessageEvents;

import java.util.Objects;

public class BridgeEvents {
    private final Bridge bridge;

    public BridgeEvents(Bridge bridge) {
        this.bridge = bridge;

        registerMinecraftEvents();
        registerDiscordEvents();
    }

    private void registerMinecraftEvents() {
        ServerLifecycleEvents.SERVER_STARTED.register(e -> this.bridge.sendBasicMessageM2D("**Server started**"));
        ServerLifecycleEvents.SERVER_STOPPING.register(e -> this.bridge.sendBasicMessageM2D("**Server stopping**"));
        ServerLifecycleEvents.SERVER_STOPPED.register(e -> this.bridge.sendBasicMessageM2D("**Server stopped**"));

        ServerMessageEvents.GAME_MESSAGE.register((server, message, overlay) -> {
            if (!Objects.equals("messageBuffer", message.getString())) {
                this.bridge.sendBasicMessageM2D("**%s**".formatted(message.getString()));
            }
        });
        ServerMessageEvents.CHAT_MESSAGE.register((message, user, parameters) -> this.bridge.sendBasicMessageM2D("%s: %s".formatted(user.getName().getLiteralString(), message.getContent().getLiteralString())));
    }

    private void registerDiscordEvents() {
        this.bridge.getDiscordApi().addMessageCreateListener(event -> {
            if (event.getChannel() == this.bridge.bridgeChannel && !event.getMessageAuthor().isBotUser()) {
                this.bridge.sendBasicMessageD2M("<@%s> %s".formatted(event.getMessageAuthor().getName(), event.getMessageContent()));
            }
        });
    }
}
