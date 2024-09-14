package dev.compasses.reasonable_defaults.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.network.chat.Component;

public class DefaultServerSelectionList extends ObjectSelectionList<DefaultServerSelectionList.ServerEntry> {
    public DefaultServerSelectionList(Minecraft minecraft, int width, int height, int y, int entryHeight) {
        super(minecraft, width, height, y, entryHeight);
    }

    public static class ServerEntry extends ObjectSelectionList.Entry<ServerEntry> {

        @Override
        public Component getNarration() {
            return null;
        }

        @Override // todo: name these params
        public void render(GuiGraphics graphics, int i, int i1, int i2, int i3, int i4, int i5, int i6, boolean b, float v) {

        }
    }
}
