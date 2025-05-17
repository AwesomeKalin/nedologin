package ru.marduk.nedologin.network;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import ru.marduk.nedologin.server.handler.PlayerLoginHandler;
import ru.marduk.nedologin.utils.SHA256;

import java.nio.charset.StandardCharsets;

public class MessageLogin {
    private final String pwd;

    public MessageLogin(String pwd) {
        this.pwd = SHA256.getSHA256(pwd);
    }

    public static void encode(MessageLogin packet, FriendlyByteBuf buf) {
        buf.writeInt(packet.pwd.length());
        buf.writeCharSequence(packet.pwd, StandardCharsets.UTF_8);
    }

    public static MessageLogin decode(FriendlyByteBuf buffer) {
        int len = buffer.readInt();
        return new MessageLogin(buffer.readCharSequence(len, StandardCharsets.UTF_8).toString());
    }

    public static void handle(MinecraftServer minecraftServer, ServerPlayer serverPlayer, ServerGamePacketListenerImpl serverGamePacketListener, FriendlyByteBuf friendlyByteBuf, PacketSender packetSender) {
        MessageLogin msg = MessageLogin.decode(friendlyByteBuf);

        if (serverPlayer != null) {
            PlayerLoginHandler.instance().login(serverPlayer.getGameProfile().getName(), msg.pwd);
        }
    }
}
