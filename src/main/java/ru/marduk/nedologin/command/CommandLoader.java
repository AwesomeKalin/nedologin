package ru.marduk.nedologin.command;

import net.fabricmc.fabric.api.command.v2.ArgumentTypeRegistry;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.synchronization.SingletonArgumentInfo;
import net.minecraft.resources.ResourceLocation;
import ru.marduk.nedologin.NLConstants;
import ru.marduk.nedologin.command.arguments.ArgumentTypeEntryName;
import ru.marduk.nedologin.command.arguments.ArgumentTypeHandlerPlugin;

public final class CommandLoader {

    public static void commonSetup() {
        ArgumentTypeRegistry.registerArgumentType(
                new ResourceLocation(NLConstants.MODID, "entry_name"),
                ArgumentTypeEntryName.class,
                SingletonArgumentInfo.contextFree(ArgumentTypeEntryName::new)
        );

        ArgumentTypeRegistry.registerArgumentType(
                new ResourceLocation(NLConstants.MODID, "plugin"),
                ArgumentTypeHandlerPlugin.class,
                SingletonArgumentInfo.contextFree(ArgumentTypeHandlerPlugin::new)
        );

        CommandRegistrationCallback.EVENT.register((dispatcher, registry, environment) -> {
            NLCommand.register(dispatcher);
        });
    }
}
