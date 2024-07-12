package cc.crss.mod;

import net.fabricmc.api.ModInitializer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CRSSMod implements ModInitializer {

    public static final Logger LOGGER = LogManager.getLogger("crss");
	@Override
	public void onInitialize() {

		LOGGER.info("Hello World!");
	}
}