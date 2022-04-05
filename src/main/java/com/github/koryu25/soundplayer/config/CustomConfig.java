package com.github.koryu25.soundplayer.config;

import com.github.koryu25.soundplayer.SoundPlayer;
import org.apache.commons.io.FileUtils;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;

public class CustomConfig {

    private final SoundPlayer main;
    private FileConfiguration config;
    private final File configFile;
    private final String fileName;

    public CustomConfig(SoundPlayer main, String fileName) {
        this.main = main;
        this.fileName = fileName;
        this.configFile = new File(main.getDataFolder(), fileName);

        config = new YamlConfiguration();

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
        try {
            this.config.load(configFile);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }

        InputStream defaultConfigStream = this.main.getResource(fileName);
        if (defaultConfigStream != null)
        {
            File file = new File(main.getDataFolder(), fileName);
            try {
                FileUtils.copyInputStreamToFile(defaultConfigStream,file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(file);
            this.config.setDefaults(defaultConfig);
        }
    }
}
