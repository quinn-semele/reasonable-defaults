package dev.compasses.reasonable_defaults.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.options.OptionsSubScreen;
import net.minecraft.network.chat.Component;

public class ModConfigOverview extends OptionsSubScreen {
    public ModConfigOverview(Screen returnToScreen) {
        super(returnToScreen, Minecraft.getInstance().options, Component.translatable("reasonable_defaults.screen.overview"));
    }

    @Override
    protected void addOptions() {
        list.addSmall(
                Button.builder(
                        Component.translatable("reasonable_defaults.button.servers"),
                        (button) -> minecraft.setScreen(new DefaultServersScreen(this))
                ).build(),

                Button.builder(
                        Component.translatable("reasonable_defaults.button.keybinds"),
                        (button) -> minecraft.setScreen(null)
                ).build()
        );

        list.addSmall(
                Button.builder(
                        Component.translatable("reasonable_defaults.button.resource_packs"),
                        (button) -> minecraft.setScreen(null)
                ).build(),

                null
        );
    }
}
