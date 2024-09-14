package dev.compasses.reasonable_defaults.screen;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.multiplayer.ServerSelectionList;
import net.minecraft.network.chat.Component;

public class DefaultServersScreen extends Screen {
    private final Screen returnToScreen;
    private boolean initialized = false;
    private DefaultServerSelectionList serverSelectionList;

    public DefaultServersScreen(Screen returnToScreen) {
        super(Component.translatable("reasonable_defaults.screen.servers"));

        this.returnToScreen = returnToScreen;
    }

    @Override
    protected void init() {
        if (initialized) {
            this.serverSelectionList.setRectangle(width, height - 64 - 32, 0, 32);
        } else {
            initialized = true;

            this.serverSelectionList = new DefaultServerSelectionList(minecraft, width, height - 64 - 32, 32, 36);
        }
    }
}
