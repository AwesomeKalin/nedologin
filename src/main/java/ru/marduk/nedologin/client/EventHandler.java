package ru.marduk.nedologin.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.client.gui.components.Button;

@SuppressWarnings("unused")
@Environment(EnvType.CLIENT)
public final class EventHandler {

    public static void onGuiOpen(Minecraft minecraft, Screen screen, int i, int i1) {
        if (!(screen instanceof SetPasswordScreen) && !PasswordHolder.instance().initialized()) {
            Screen prev = screen;
            minecraft.setScreen(new SetPasswordScreen(prev));
        }
    }

    public static void onGuiInit(Minecraft minecraft, Screen screen, int i, int i1) {
        Screen gui = screen;

        if (gui instanceof TitleScreen) {
            Button buttonSetPassword;

            buttonSetPassword = new Button(gui.width / 2 - 124, gui.height / 4 + 48, 20, 20, Component.literal("P"), btn -> Minecraft.getInstance().setScreen(new SetPasswordScreen(gui)));

            /*buttonSetPassword = Button(Component.literal("P"),
                    btn -> Minecraft.getInstance().setScreen(new SetPasswordScreen(gui)))
                    .bounds(5, 5, 20, 20).build();*/

            gui.addRenderableWidget(buttonSetPassword);
        }
    }
}