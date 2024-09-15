package dev.compasses.reasonable_defaults.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.compasses.reasonable_defaults.Constants;
import net.minecraft.util.GsonHelper;

public record PackEntry(String location, PackPriority priority) {
    public static PackEntry fromJsonElement(JsonElement element, String memberName) { // "resource_packs[" + version + "][" + (index + 1) + "]"
        JsonObject object = GsonHelper.convertToJsonObject(element, memberName);

        String location = GsonHelper.getAsString(object, "location");

        PackPriority priority = PackPriority.BEFORE_MODS;
        if (object.has("priority")) {
            try {
                priority = PackPriority.fromString(GsonHelper.convertToString(object.get("priority"), "priority"));
            } catch (IllegalArgumentException error) {
                Constants.LOG.warn("Skipping priority for default resource pack \"{}\"; {}", location, error.getMessage());
            }
        }

        return new PackEntry(location, priority);
    }
}
