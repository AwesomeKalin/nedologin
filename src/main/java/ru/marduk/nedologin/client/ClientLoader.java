package ru.marduk.nedologin.client;

import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import ru.marduk.nedologin.NLConstants;
import ru.marduk.nedologin.Nedologin;
import ru.marduk.nedologin.network.MessageLogin;

@Environment(EnvType.CLIENT)
public final class ClientLoader {

    public static void joinServer(ClientPacketListener clientPacketListener, PacketSender packetSender, Minecraft minecraft) {
        if (clientPacketListener.getConnection().isMemoryConnection()) return;
        Nedologin.logger.debug("Sending login packet to the server...");
        FriendlyByteBuf buf = PacketByteBufs.create();
        MessageLogin.encode(new MessageLogin(PasswordHolder.instance().password()), buf);
        ClientPlayNetworking.send(new ResourceLocation(NLConstants.MODID, "main"), buf);
    }

    public static void onClientRegisterCommand(CommandDispatcher<FabricClientCommandSource> dispatcher, CommandBuildContext commandBuildContext) {
        ChangePasswordCommand.register(dispatcher);
    }
}
