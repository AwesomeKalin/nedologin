package ru.marduk.nedologin.network;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import ru.marduk.nedologin.NLConfig;
import ru.marduk.nedologin.Nedologin;
import ru.marduk.nedologin.server.storage.NLStorage;
import ru.marduk.nedologin.utils.SHA256;

import java.nio.charset.StandardCharsets;

public class MessageChangePassword {
    private final String original, to;

    public MessageChangePassword(String original, String to) {
        this.original = SHA256.getSHA256(original);
        this.to = SHA256.getSHA256(to);
    }

    public static void encode(MessageChangePassword msg, FriendlyByteBuf buf) {
        buf.writeInt(msg.original.length());
        buf.writeCharSequence(msg.original, StandardCharsets.UTF_8);
        buf.writeInt(msg.to.length());
        buf.writeCharSequence(msg.to, StandardCharsets.UTF_8);
    }

    public static MessageChangePassword decode(FriendlyByteBuf buf) {
        String original = buf.readCharSequence(buf.readInt(), StandardCharsets.UTF_8).toString();
        String to = buf.readCharSequence(buf.readInt(), StandardCharsets.UTF_8).toString();
        return new MessageChangePassword(original, to);
    }

    public static void handle(MinecraftServer minecraftServer, ServerPlayer serverPlayer, ServerGamePacketListenerImpl serverGamePacketListener, FriendlyByteBuf friendlyByteBuf, PacketSender packetSender) {
        MessageChangePassword msg = MessageChangePassword.decode(friendlyByteBuf);
        String username = serverPlayer.getGameProfile().getName();

        if (!NLConfig.enableChangePassword) {
            serverPlayer.displayClientMessage(Component.translatable("nedologin.info.password_change_disabled"), false);

            return;
        }

        if (NLStorage.instance().storageProvider.checkPassword(username, msg.original)) {
            NLStorage.instance().storageProvider.changePassword(username, msg.to);
            serverPlayer.displayClientMessage(
                    Component.translatable("nedologin.info.password_change_successful"),
                    false
            );
        } else {
            // Should never happen though
            serverPlayer.displayClientMessage(
                    Component.translatable("nedologin.info.password_change_fail"),
                    false
            );
            Nedologin.logger.warn("Player {} tried to change password with a wrong password.", username);
        }
    }
}
