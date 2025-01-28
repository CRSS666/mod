package cc.crss.mod.bridge;

import cc.crss.mod.CRSSMod;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.Text;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.activity.ActivityType;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.intent.Intent;
import org.javacord.api.entity.message.MessageBuilder;

import java.util.Optional;

public class Bridge {
    private final DiscordApi discordApi;
    public final TextChannel bridgeChannel;
    private final BridgeEvents bridgeEvents;
    public final MinecraftServer server;

    private final String token;

    public Bridge(String token, long channelId, MinecraftServer server) {
        this.token = token;
        this.server = server;

        DiscordApiBuilder discordApiBuilder = new DiscordApiBuilder();
        discordApiBuilder
                .setToken(token)
                .addIntents(Intent.GUILD_MESSAGES, Intent.MESSAGE_CONTENT);

        this.discordApi = discordApiBuilder.login().join();
        this.discordApi.updateActivity(ActivityType.LISTENING, "your messages");

        this.bridgeChannel = this.discordApi.getChannelById(channelId).orElseThrow().asTextChannel().orElseThrow();

        this.bridgeEvents = new BridgeEvents(this);
    }

    public void sendBasicMessageM2D(String message) {
        MessageBuilder msg = new MessageBuilder()
                .append(message);
        msg.send(CRSSMod.BRIDGE.bridgeChannel);
    }

    public void sendWebhookMessageM2D(String message, String username) {

    }

    public void sendBasicMessageD2M(String message) {
        Text embeddedText = Text.nbt("discord", false, Optional.empty(), null);

        this.server.getPlayerManager().broadcast(Text.literal(message), false);
        this.server.getPlayerManager().broadcast(embeddedText, false);
    }

    public DiscordApi getDiscordApi() {
        return this.discordApi;
    }
}
