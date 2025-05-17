package ru.marduk.nedologin;

import net.fabricmc.api.DedicatedServerModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.message.v1.ServerMessageEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.server.MinecraftServer;
import ru.marduk.nedologin.network.NetworkLoader;
import ru.marduk.nedologin.server.ServerLoader;
import ru.marduk.nedologin.server.ServerSideEventHandler;

public class NedologinServer implements DedicatedServerModInitializer {
    public static MinecraftServer SERVER;

    @Override
    public void onInitializeServer() {
        NetworkLoader.initServer();

        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            SERVER = server;
        });

        ServerLifecycleEvents.SERVER_STARTING.register(ServerLoader::serverStarting);
        ServerLifecycleEvents.SERVER_STOPPED.register(ServerLoader::serverStopped);
        ServerPlayConnectionEvents.JOIN.register(ServerSideEventHandler::playerJoin);
        ServerPlayConnectionEvents.DISCONNECT.register(ServerSideEventHandler::playerLeave);
        ServerMessageEvents.ALLOW_COMMAND_MESSAGE.register(ServerSideEventHandler::onCommand);
    }
}
