package com.github.koryu25.soundplayer;

import com.github.koryu25.soundplayer.config.MyConfig;
import com.github.koryu25.soundplayer.config.lang.LangConfig;
import com.github.koryu25.soundplayer.sound.AllSoundDataList;
import com.github.koryu25.soundplayer.sound.Audience;
import dev.jorel.commandapi.CommandAPI;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class SoundPlayer extends JavaPlugin {

    private static MyConfig myConfig;
    private static LangConfig langConfig;

    private static List<Audience> audienceList;



    @Override
    public void onEnable() {
        // Config
        myConfig = new MyConfig(this);
        //langConfig
        langConfig = new LangConfig(this, "lang/" + myConfig.getLang().getPath());
        // SoundList
        AllSoundDataList.initialize();
        // Command
        CommandAPI.registerCommand(SoundPlayerCommand.class);
        // EventListener
        new EventProcessor(this);
        // AudienceList
        audienceList = new ArrayList<>();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    // Getter
    public static MyConfig getMyConfig() {
        return myConfig;
    }
    public static LangConfig getLangConfig() {
        return langConfig;
    }
    public static List<Audience> getAudienceList() {
        return audienceList;
    }

    // Audienceを検索
    public static Audience searchAudience(Player player) {
        for (Audience audience : audienceList) {
            if (audience.getPlayer().getUniqueId().toString().equals(player.getUniqueId().toString())) {
                return audience;
            }
        }
        return null;
    }
}
