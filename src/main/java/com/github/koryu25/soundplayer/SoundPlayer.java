package com.github.koryu25.soundplayer;

import com.github.koryu25.soundplayer.command.CommandManager;
import com.github.koryu25.soundplayer.config.MyConfig;
import com.github.koryu25.soundplayer.config.lang.Lang;
import com.github.koryu25.soundplayer.config.lang.LangConfig;
import com.github.koryu25.soundplayer.sound.AllSoundDataList;
import com.github.koryu25.soundplayer.sound.Audience;
import com.github.koryu25.soundplayer.sound.SoundInventory;
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
        new CommandManager(this);
        // EventListener
        new EventProcessor(this);
        // AudienceList
        audienceList = new ArrayList<>();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
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

    // PlayerがJoinした時の処理
    public static void join(Player player) {
        Audience audience = searchAudience(player);
        if (audience != null) return;
        audienceList.add(new Audience(player));
    }
    // PlayerがQuitした時の処理
    public static void quit(Player player) {
        Audience audience = searchAudience(player);
        if (audience == null) return;
        audienceList.remove(audience);
    }
    // InventoryをClickした時の処理
    public static boolean onClick(String title, Player player, int slot) {
        if (!SoundInventory.match(title)) return false;
        Audience audience = searchAudience(player);
        if (audience != null) {
            audience.getSoundInventory().onClick(slot);
        }
        return true;
    }
    // InventoryがCloseされた時の処理
    public static void onInventoryClose(Player player) {
        Audience audience = searchAudience(player);
        if (audience == null) return;
        audience.setSoundInventory(null);
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
}
