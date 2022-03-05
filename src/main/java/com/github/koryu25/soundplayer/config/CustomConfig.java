package com.github.koryu25.soundplayer.config;

import com.github.koryu25.soundplayer.SoundPlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;

public class CustomConfig {

    private final SoundPlayer main;
    private FileConfiguration config = null;
    private final File configFile;
    private final String fileName;

    public CustomConfig(SoundPlayer main, String fileName) {
        this.main = main;
        this.fileName = fileName;
        this.configFile = new File(main.getDataFolder(), fileName);
        saveDefaultConfig();
        reloadConfig();
    }

    public void saveDefaultConfig() {
        if (!configFile.exists()) {
            main.saveResource(fileName, false);
        }
    }

    public FileConfiguration getConfig() {
        if (config == null) {
            reloadConfig();
        }
        return config;
    }

    public void saveConfig() {
        if (config == null) return;
        try {
            getConfig().save(configFile);
        } catch (IOException e) {
            main.getLogger().log(Level.SEVERE, "Could not save config to " + configFile, e);
        }
    }

    public void reloadConfig() {
        config = YamlConfiguration.loadConfiguration(configFile);
        final InputStream defConfigStream = main.getResource(fileName);
        if (defConfigStream == null) return;
        config.setDefaults(YamlConfiguration.loadConfiguration(new InputStreamReader(defConfigStream, StandardCharsets.UTF_8)));
    }
}
