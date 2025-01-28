package cc.crss.mod;

import cc.crss.mod.api.ApiServer;
import cc.crss.mod.bridge.Bridge;
import dev.spiritstudios.specter.api.serialization.text.TextContentRegistry;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import cc.crss.mod.util.CommandRegister;

public class CRSSMod implements DedicatedServerModInitializer {
    public static final Logger LOGGER = LogManager.getLogger("crss");
    public static Bridge BRIDGE;

    @Override
    public void onInitializeServer() {
        LOGGER.info("CRSS initialising.");

        ServerLifecycleEvents.SERVER_STARTING.register(server -> {
            BRIDGE = new Bridge("adminpassword1234", 12345L, server);
        });

        ServerLifecycleEvents.SERVER_STARTED.register(ApiServer::createInstance);
        ServerLifecycleEvents.SERVER_STOPPING.register(server -> {
            ApiServer srv = ApiServer.getInstance();

            srv.stop();
        });

        CommandRegister.registerCommands();;
    }
}