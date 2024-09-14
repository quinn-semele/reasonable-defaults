package dev.compasses.reasonable_defaults.neoforge;

import dev.compasses.reasonable_defaults.screen.ModConfigOverview;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = "reasonable_defaults", dist = Dist.CLIENT)
public class ReasonableDefaults {
    public ReasonableDefaults(ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, (modContainer, screen) -> {
            return new ModConfigOverview(screen);
        });
    }
}
