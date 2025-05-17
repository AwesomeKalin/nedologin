package ru.marduk.nedologin.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.resources.ResourceLocation;
import ru.marduk.nedologin.NLConstants;

public class NetworkLoader {
    private static final String PROTOCOL_VERSION = "1.1";

    public static void initServer() {
        ServerPlayNetworking.registerGlobalReceiver(new ResourceLocation(NLConstants.MODID, "change_password"), MessageChangePassword::handle);
        ServerPlayNetworking.registerGlobalReceiver(new ResourceLocation(NLConstants.MODID, "login"), MessageLogin::handle);
    }

    public static void initClient() {
        ClientPlayNetworking.registerGlobalReceiver(new ResourceLocation(NLConstants.MODID, "request_login"), MessageRequestLogin::handle);
    }
}
