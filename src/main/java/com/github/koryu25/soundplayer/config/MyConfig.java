package com.github.koryu25.soundplayer.config;

import com.github.koryu25.soundplayer.SoundPlayer;

public class MyConfig extends CustomConfig {

    public MyConfig(SoundPlayer main) {
        super(main, "config.yml");
    }

    // Getter
    public float getDefaultVolume() {
        return (float) getConfig().getDouble("default.volume");
    }
    public float getDefaultPitch() {
        return (float) getConfig().getDouble("default.pitch");
    }
    public float getVolumeDifferent() {
        return (float) getConfig().getDouble("different.volume");
    }
    public float getPitchDifferent() {
        return (float) getConfig().getDouble("different.pitch");
    }
}
