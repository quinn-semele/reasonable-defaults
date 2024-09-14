package dev.compasses.reasonable_defaults.mixin;

import com.google.gson.*;
import com.mojang.datafixers.kinds.Const;
import dev.compasses.reasonable_defaults.Constants;
import dev.compasses.reasonable_defaults.ServerObject;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.ServerList;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.util.GsonHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
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
            JsonArray servers = GsonHelper.parseArray(reader);

            for (int i = 0; i < servers.size(); i++) {
                try {
                    ServerObject object = Constants.GSON.fromJson(servers.get(i), ServerObject.class);

                    ServerData data = new ServerData(
                            object.name(),
                            object.ip(),
                            ServerData.Type.OTHER
                    );

                    data.setResourcePackStatus(object.useServerPack());

                    serverList.add(data);
                } catch (JsonParseException error) {
                    Constants.LOG.warn("Skipping default server entry #{} because it is not valid.", i + 1);
                }
            }

        } catch (IOException error) {
            Constants.LOG.warn("Failed to add default servers to the server list...");
        }
    }
}
