package dev.compasses.reasonable_defaults.config;

import com.google.gson.JsonObject;
import dev.compasses.reasonable_defaults.Constants;
import net.minecraft.util.GsonHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public record UserConfig(
        @Nullable String serverListVersion,
        @Nullable String resourcePackVersion
) {
    private static UserConfig fromJsonObject(JsonObject object) {
        String resourcePackVersion = GsonHelper.getAsString(object, "resource_packs_version");

        String serverListVersion = null;
        if (object.has("servers_version")) {
            serverListVersion = GsonHelper.getAsString(object, "servers_version");
        }

        return new UserConfig(serverListVersion, resourcePackVersion);
    }

    @NotNull
    public static UserConfig load(Path gameDirectory) {
        Path configPath = gameDirectory.resolve("local/reasonable_defaults.json");

        if (Files.notExists(configPath)) {
            return new UserConfig(null, null);
        }

        try (var reader = Files.newBufferedReader(configPath, StandardCharsets.UTF_8)) {
            JsonObject config = Constants.GSON.fromJson(reader, JsonObject.class);

            return fromJsonObject(config);
        } catch (IOException error) {
            Constants.LOG.error("Failed to read user config file...", error);

            return new UserConfig(null, null);
        }
    }

    public UserConfig withResourcePackVersion(String version) {
        return new UserConfig(version, serverListVersion);
    }

    public UserConfig withServerListVersion(String version) {
        return new UserConfig(resourcePackVersion, version);
    }

    public void save(Path gameDirectory) {
        Path configPath = gameDirectory.resolve("local/reasonable_defaults.json");

        try (var writer = Constants.GSON.newJsonWriter(Files.newBufferedWriter(configPath, StandardCharsets.UTF_8))) {
            writer.beginObject();

            writer.name("resource_packs_version").value(resourcePackVersion); // Should be non-null by now.

            if (serverListVersion != null) {
                writer.name("servers_version").value(serverListVersion);
            }

            writer.endObject();
        } catch (IOException error) {
            Constants.LOG.error("Failed to write user config file...", error);
        }
    }
}
