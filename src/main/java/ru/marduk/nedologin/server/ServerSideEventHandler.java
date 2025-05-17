package ru.marduk.nedologin.server;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.ChatType;
import net.minecraft.network.chat.PlayerChatMessage;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import ru.marduk.nedologin.NLConstants;
import ru.marduk.nedologin.Nedologin;
import ru.marduk.nedologin.server.handler.PlayerLoginHandler;
import ru.marduk.nedologin.NLConfig;

@Environment(EnvType.SERVER)
public class ServerSideEventHandler {

    public static void playerJoin(ServerGamePacketListenerImpl serverGamePacketListener, PacketSender packetSender, MinecraftServer minecraftServer) {
        PlayerLoginHandler.instance().playerJoin(serverGamePacketListener.getPlayer());
        ServerPlayNetworking.send(serverGamePacketListener.getPlayer(), new ResourceLocation(NLConstants.MODID, "request_login"), PacketByteBufs.create());
    }

    public static void playerLeave(ServerGamePacketListenerImpl serverGamePacketListener, MinecraftServer minecraftServer) {
        PlayerLoginHandler.instance().playerLeave(serverGamePacketListener.getPlayer());
    }

    public static boolean onCommand(PlayerChatMessage playerChatMessage, CommandSourceStack commandSourceStack, ChatType.Bound bound) {
        String command = playerChatMessage.toString();
        if (command.startsWith("/")) command = command.substring(1);

        Nedologin.logger.debug("Checking command '{}'", command);
        if (PlayerLoginHandler.instance().hasPlayerLoggedIn(commandSourceStack.getPlayer().toString())) {
            return true;
        }
        if (NLConfig.whiteListCommands.contains(command)) {
            return true;
        }
        Nedologin.logger.warn("Denied {} to execute command '{}' before login",
                commandSourceStack.getDisplayName().getString(), command);

        return false;
    }
}
