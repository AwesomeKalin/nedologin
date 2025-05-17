package ru.marduk.nedologin.client;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import ru.marduk.nedologin.NLConstants;
import ru.marduk.nedologin.network.MessageChangePassword;

import static net.minecraft.commands.Commands.*;

@Environment(EnvType.CLIENT)
public final class ChangePasswordCommand {
    public static <S> void register(CommandDispatcher<S> dispatcher) {
        var theCommand =
                literal("nedologin")
                        .then(literal("change_password")
                                .then(argument("passwd", StringArgumentType.string())
                                        .executes(ChangePasswordCommand::changePassword)));
        dispatcher.register((LiteralArgumentBuilder<S>) theCommand);
    }

    private static int changePassword(CommandContext<CommandSourceStack> context) {
        final var to = StringArgumentType.getString(context, "passwd");
        var msg = new MessageChangePassword(PasswordHolder.instance().password(), to);
        PasswordHolder.instance().setPendingPassword(to);
        FriendlyByteBuf buf = PacketByteBufs.create();
        MessageChangePassword.encode(msg, buf);
        ClientPlayNetworking.send(new ResourceLocation(NLConstants.MODID, "change_password"), buf);
        return Command.SINGLE_SUCCESS;
    }
}
