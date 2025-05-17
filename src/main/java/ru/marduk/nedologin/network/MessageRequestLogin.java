package ru.marduk.nedologin.network;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import ru.marduk.nedologin.NLConstants;
import ru.marduk.nedologin.client.PasswordHolder;

@SuppressWarnings({"InstantiationOfUtilityClass", "unused"})
public class MessageRequestLogin {

    public MessageRequestLogin() {
    }

    public static void encode(MessageRequestLogin msg, FriendlyByteBuf buffer) {
        // NO-OP
    }

    public static MessageRequestLogin decode(FriendlyByteBuf buffer) {
        return new MessageRequestLogin();
    }

    public static void handle(Minecraft minecraft, ClientPacketListener clientPacketListener, FriendlyByteBuf friendlyByteBuf, PacketSender packetSender) {
        FriendlyByteBuf buf = PacketByteBufs.create();
        MessageLogin.encode(new MessageLogin(PasswordHolder.instance().password()), buf);

        ClientPlayNetworking.send(new ResourceLocation(NLConstants.MODID, "login"), buf);
    }
}
