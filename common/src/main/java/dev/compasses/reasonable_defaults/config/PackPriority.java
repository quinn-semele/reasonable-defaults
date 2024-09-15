package dev.compasses.reasonable_defaults.config;

import java.util.Locale;

public enum PackPriority {
    FIRST,
    BEFORE_MODS,
    BEFORE_VANILLA,
    LAST;

    public static PackPriority fromString(String priority) {
        return switch (priority.toLowerCase(Locale.ROOT)) {
            case "first" -> FIRST;
            case "before_mods" -> BEFORE_MODS;
            case "before_vanilla" -> BEFORE_VANILLA;
            case "last" -> LAST;
            default -> throw new IllegalArgumentException("priority must be one of [first, before_mods, before_vanilla, last]");
        };
    }

    public String serializedName() {
        return switch (this) {
            case FIRST -> "first";
            case BEFORE_MODS -> "before_mods";
            case BEFORE_VANILLA -> "before_vanilla";
            case LAST -> "last";
        };
    }
}
