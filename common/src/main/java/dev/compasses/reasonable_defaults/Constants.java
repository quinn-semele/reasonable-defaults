package dev.compasses.reasonable_defaults;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import net.minecraft.client.multiplayer.ServerData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class Constants {
	public static final String MOD_NAME = "Reasonable Defaults";
	public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);
	public static final Gson GSON = new GsonBuilder()
			.disableHtmlEscaping()
			.setPrettyPrinting()
			.registerTypeAdapter(ServerObject.class, new TypeAdapter<ServerObject>() {
				@Override
				public void write(JsonWriter out, ServerObject value) throws IOException {
					out.beginObject()
					.name("name").value(value.name())
					.name("ip").value(value.ip())
					.name("use_server_packs").value(serverPackStatusToString(value.useServerPack()))
					.endObject();
				}

				@Override
				public ServerObject read(JsonReader in) throws IOException {
					String name = null;
					String ip = null;
					ServerData.ServerPackStatus status = ServerData.ServerPackStatus.PROMPT;

					in.beginObject();

					while (in.peek() != JsonToken.END_OBJECT) {
						String key = in.nextName();
                        switch (key) {
                            case "name" -> name = in.nextString();
                            case "ip" -> ip = in.nextString();
                            case "use_server_packs" -> status = stringToServerPackStatus(in.nextString());
                            default -> Constants.LOG.info("Skipping invalid entry \"{}\" in server entry.", key);
                        }
					}

					in.endObject();

					if (name != null && ip != null) {
						return new ServerObject(name, ip, status);
					} else if (name == null) {
						throw new JsonParseException("Invalid server entry, missing name.");
					} else {
						throw new JsonParseException("Invalid server entry, missing ip.");
					}
				}
			})
			.create();

	private static String serverPackStatusToString(ServerData.ServerPackStatus status) {
		return switch (status) {
			case PROMPT -> "prompt";
			case ENABLED -> "yes";
			case DISABLED -> "no";
		};
	}

	private static ServerData.ServerPackStatus stringToServerPackStatus(String status) {
		return switch (status) {
			case "prompt" -> ServerData.ServerPackStatus.PROMPT;
			case "yes" -> ServerData.ServerPackStatus.ENABLED;
			case "no" -> ServerData.ServerPackStatus.DISABLED;
			default -> throw new IllegalArgumentException("Invalid server pack status passed: " + status);
		};
	}
}