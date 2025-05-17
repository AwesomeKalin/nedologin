package ru.marduk.nedologin.server;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import ru.marduk.nedologin.Nedologin;
import ru.marduk.nedologin.server.handler.PlayerLoginHandler;
import ru.marduk.nedologin.server.storage.NLStorage;
import ru.marduk.nedologin.NLConfig;
import ru.marduk.nedologin.NLConstants;

import java.io.IOException;

@SuppressWarnings("unused")
@Environment(EnvType.SERVER)
public final class ServerLoader {
    public static void serverStarting(MinecraftServer minecraftServer) {
        NLStorage.initialize(NLConfig.storageProvider, minecraftServer);

        PlayerLoginHandler.initLoginHandler(NLConfig.plugins.stream().map(ResourceLocation::new));
    }

    public static void serverStopped(MinecraftServer minecraftServer) {
        PlayerLoginHandler.instance().stop();

        Nedologin.logger.info("Saving all entries");
        if (NLStorage.instance() != null) {
            NLStorage.instance().storageProvider.save();
        }
    }
}
