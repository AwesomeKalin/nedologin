package ru.marduk.nedologin;

import eu.midnightdust.lib.config.MidnightConfig;
import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.marduk.nedologin.command.CommandLoader;

public final class Nedologin implements ModInitializer {
    public static Logger logger = LogManager.getLogger(NLConstants.MODID);

    @Override
    public void onInitialize() {
        MidnightConfig.init(NLConstants.MODID, NLConfig.class);
        CommandLoader.commonSetup();
    }
}
