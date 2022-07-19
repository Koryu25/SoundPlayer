package com.github.koryu25.soundplayer;

import com.github.koryu25.soundplayer.yaml.MyConfig;
import com.github.koryu25.soundplayer.yaml.lang.LangConfig;
import com.github.koryu25.soundplayer.sound.AllSoundDataList;
import com.github.koryu25.soundplayer.sound.Audience;
import dev.jorel.commandapi.CommandAPI;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class SoundPlayer extends JavaPlugin {

    private static MyConfig myConfig; //メインコンフィグ
    private static LangConfig langConfig; //言語ファイル

    private static List<Audience> audienceList; //プレイヤーリスト

    @Override
    public void onEnable() {
        myConfig = new MyConfig(this);

        langConfig = new LangConfig(this, "lang/" + myConfig.getLang().getPath());

        AllSoundDataList.initialize(); //サウンドデータの読み込み

        CommandAPI.registerCommand(SRCommand.class);

        new EventListener(this);

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

    /**
     * Audienceを検索
     * @param player プレイヤー
     * @return 登録済みのAudienceまたはnull
     */
    public static Audience searchAudience(Player player) {
        for (Audience audience : audienceList) {
            if (audience.getPlayer().getUniqueId().toString().equals(player.getUniqueId().toString())) {
                return audience;
            }
        }
        return null;
    }
}
