package dev.compasses.reasonable_defaults;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Constants {
	public static final String MOD_NAME = "Reasonable Defaults";
	public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);
	public static final Gson GSON = new GsonBuilder()
			.disableHtmlEscaping()
			.setPrettyPrinting()
			.create();
}