package com.github.koryu25.soundplayer;

import com.github.koryu25.soundplayer.sound.Audience;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class EventListener implements Listener {

    public EventListener(SoundPlayer main) {
        main.getServer().getPluginManager().registerEvents(this, main);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        //ログインしたプレイヤーをAudienceとして登録
        Player player = event.getPlayer();
        Audience audience = SoundPlayer.searchAudience(player);
        if (audience != null) return;
        SoundPlayer.getAudienceList().add(new Audience(player));
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        //ログアウトしたプレイヤーをAudienceリストから削除
        Audience audience = SoundPlayer.searchAudience(event.getPlayer());
        if (audience == null) return;
        SoundPlayer.getAudienceList().remove(audience);
    }
}
