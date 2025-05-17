package ru.marduk.nedologin;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import ru.marduk.nedologin.client.ClientLoader;
import ru.marduk.nedologin.client.EventHandler;
import ru.marduk.nedologin.network.NetworkLoader;

public class NedologinClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientPlayConnectionEvents.JOIN.register(ClientLoader::joinServer);
        ClientCommandRegistrationCallback.EVENT.register(ClientLoader::onClientRegisterCommand);
        ScreenEvents.BEFORE_INIT.register(EventHandler::onGuiOpen);
        ScreenEvents.AFTER_INIT.register(EventHandler::onGuiInit);

        NetworkLoader.initClient();
    }
}
