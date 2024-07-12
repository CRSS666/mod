package cc.crss.mod;

import net.fabricmc.api.DedicatedServerModInitializer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CRSSMod implements DedicatedServerModInitializer {

    public static final Logger LOGGER = LogManager.getLogger("crss");
	@Override
	public void onInitializeServer() {

		LOGGER.info("Hello World!");
	}
}