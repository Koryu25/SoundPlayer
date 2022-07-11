package com.github.koryu25.soundplayer.yaml.lang;

import com.github.koryu25.soundplayer.SoundPlayer;
import com.github.koryu25.soundplayer.yaml.CustomConfig;

import java.util.List;

public class LangConfig extends CustomConfig {

    public LangConfig(SoundPlayer main, String fileName) {
        super(main, fileName);
    }

    //getter
    public String getVolume() {
        return getConfig().getString("volume");
    }
    public String getPitch() {
        return getConfig().getString("pitch");
    }
    public String getDifference() {
        return getConfig().getString("difference");
    }
    public String getMultiply() {
        return getConfig().getString("multiply");
    }
    public String getDown() {
        return getConfig().getString("down");
    }
    public String getUp() {
        return getConfig().getString("up");
    }
    public String getPreviousPage() {
        return getConfig().getString("previous_page");
    }
    public String getNextPage() {
        return getConfig().getString("next_page");
    }

    public String getResetMessage() {
        return getConfig().getString("reset_message");
    }

    public List<String> getHelpMessages() {
        return getConfig().getStringList("help_messages");
    }

}
