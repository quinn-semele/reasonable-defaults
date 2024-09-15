package dev.compasses.reasonable_defaults.config;

import com.google.gson.JsonObject;

import java.util.List;
import java.util.Map;

public record PackConfig(
        String packVersion,
        Map<String, String> servers,
        Map<String, List<PackEntry>> resourcePacks
) {
    public static PackConfig fromJsonObject(JsonObject object) {
        // todo: finish
        return null;
    }
}
