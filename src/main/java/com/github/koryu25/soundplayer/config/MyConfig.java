package com.github.koryu25.soundplayer.config;

import com.github.koryu25.soundplayer.SoundPlayer;
import com.github.koryu25.soundplayer.config.lang.Lang;

public class MyConfig extends CustomConfig {

    public MyConfig(SoundPlayer main) {
        super(main, "config.yml");
    }

    // Getter
    public float getDefaultVolume() {
        return (float) getConfig().getDouble("volume", 1d);
    }
    public float getDefaultPitch() {
        return (float) getConfig().getDouble("pitch", 1d);
    }
    public float getDefaultDifferent() {
        return (float) getConfig().getDouble("different", 0.1d);
    }
    public Lang getLang() {
        String lang = getConfig().getString("lang", "ja");

        switch(lang) {
            case "en":
                return Lang.ENGLISH;
            default:
                return Lang.JAPANESE;
        }
    }
}
