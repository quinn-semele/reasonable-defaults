package dev.compasses.reasonable_defaults;

import net.minecraft.client.multiplayer.ServerData;

public record ServerObject(
        String name,
        String ip,
        ServerData.ServerPackStatus useServerPack
) {
}
