package dev.compasses.reasonable_defaults.mixin;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.compasses.reasonable_defaults.Constants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.ServerList;
import net.minecraft.util.GsonHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Mixin(ServerList.class)
public class DefaultServersMixin {
    @Final
    @Shadow
    private Minecraft minecraft;

    @Shadow @Final private List<ServerData> serverList;

    @Inject(
            method = "load()V",
            at = @At(value = "RETURN", ordinal = 0)
    )
    private void reasonableDefaults$load(CallbackInfo ci) {
        Path customServersFile = minecraft.gameDirectory.toPath().resolve("config/reasonable_defaults/servers.json");

        if (!Files.exists(customServersFile)) {
            // Make default config?
            return;
        }

        try (Reader reader = Files.newBufferedReader(customServersFile)) {
            JsonObject servers = GsonHelper.parse(reader, true);

            for (var entry : servers.entrySet()) {
                String name = entry.getKey();
                JsonElement ip = entry.getValue();

                if (ip.isJsonPrimitive()) {
                    serverList.add(new ServerData(name, ip.getAsString(), ServerData.Type.OTHER));
                } else {
                    Constants.LOG.warn("Skipping \"{}\" entry due to having an invalid ip, must be a string.", name);
                }
            }
        } catch (IOException error) {
            Constants.LOG.warn("Failed to add default servers to the server list...");
        }
    }
}
