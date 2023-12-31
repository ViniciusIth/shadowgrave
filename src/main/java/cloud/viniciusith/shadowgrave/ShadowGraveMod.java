package cloud.viniciusith.shadowgrave;

import net.fabricmc.api.ModInitializer;

import java.util.logging.Logger;

public class ShadowGraveMod implements ModInitializer {
    public static final String MOD_ID = "shadowgrave";
    public static final Logger LOGGER = Logger.getLogger(MOD_ID);


    @Override
    public void onInitialize() {
        LOGGER.info("Hello from " + MOD_ID);
    }
}
