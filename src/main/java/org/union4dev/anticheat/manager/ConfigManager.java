package org.union4dev.anticheat.manager;

import github.scarsz.configuralize.DynamicConfig;
import github.scarsz.configuralize.Language;
import github.scarsz.configuralize.ParseException;
import org.union4dev.anticheat.AntiCheat;
import org.union4dev.anticheat.AntiCheatAPI;

import java.io.File;
import java.io.IOException;

public class ConfigManager {

    private final DynamicConfig config;

    public ConfigManager() {
        if (!AntiCheatAPI.getInstance().getPlugin().getDataFolder().exists()) {
            if (!AntiCheatAPI.getInstance().getPlugin().getDataFolder().mkdirs()) {
                AntiCheatAPI.getInstance().getPlugin().getLogger().warning("Failed to create data folder");
            }
        }
        config = new DynamicConfig();
        config.addSource(AntiCheat.class, "config", new File(AntiCheatAPI.getInstance().getPlugin().getDataFolder(), "config.yml"));
        config.addSource(AntiCheat.class, "detection", new File(AntiCheatAPI.getInstance().getPlugin().getDataFolder(), "detection.yml"));

        load();
    }

    private void load() {
        final String languageCode = System.getProperty("user.language").toUpperCase();

        if (!config.isLanguageAvailable(Language.valueOf(languageCode))) {
            AntiCheatAPI.getInstance().getPlugin().getLogger().warning(languageCode + " is not supported now, ENGLISH instead.");
            config.setLanguage(Language.EN);
        } else {
            config.setLanguage(Language.valueOf(languageCode));
        }

        try {
            config.saveAllDefaults(false);
            config.loadAll();
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public DynamicConfig getConfig() {
        return config;
    }
}
