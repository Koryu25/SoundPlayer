package com.github.koryu25.soundplayer;

import com.github.koryu25.soundplayer.sound.Audience;
import com.github.koryu25.soundplayer.sound.SoundInventory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class EventListener implements Listener {

    // リスナの登録
    public EventListener(SoundPlayer main) {
        main.getServer().getPluginManager().registerEvents(this, main);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        Audience audience = SoundPlayer.searchAudience(player);
        if (audience != null) return;
        SoundPlayer.getAudienceList().add(new Audience(player));
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Audience audience = SoundPlayer.searchAudience(event.getPlayer());
        if (audience == null) return;
        SoundPlayer.getAudienceList().remove(audience);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getClickedInventory() == null) return;
        if (event.getClickedInventory().getType() == InventoryType.PLAYER) return;
        boolean cancel = false;
        if (SoundInventory.match(event.getView().getTitle())) {
            Audience audience = SoundPlayer.searchAudience((Player) event.getWhoClicked());
            if (audience != null) {
                audience.getSoundInventory().onClick(event.getSlot());
            }
            cancel = true;
        }
        event.setCancelled(cancel);
    }
}
