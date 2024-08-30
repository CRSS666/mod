package cc.crss.mod;
import cc.crss.mod.api.ApiServer;
import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import cc.crss.mod.util.CommandRegister;

public class CRSSMod implements DedicatedServerModInitializer {
    public static final Logger LOGGER = LogManager.getLogger("crss");

	@Override
	public void onInitializeServer() {
		LOGGER.info("CRSS initialising.");

        ServerLifecycleEvents.SERVER_STARTED.register(ApiServer::createInstance);
        ServerLifecycleEvents.SERVER_STOPPING.register(server -> {
            ApiServer srv = ApiServer.getInstance();

            srv.stop();
        });

		CommandRegister.registerCommands();
	}
}