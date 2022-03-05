package com.github.koryu25.soundplayer;

import com.github.koryu25.soundplayer.sound.Audience;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class EventProcessor implements Listener {

    public EventProcessor(SoundPlayer main) {
        main.getServer().getPluginManager().registerEvents(this, main);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        SoundPlayer.join(event.getPlayer());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        SoundPlayer.quit(event.getPlayer());
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getClickedInventory() == null) return;
        if (event.getClickedInventory().getType() == InventoryType.PLAYER) return;
        boolean b = SoundPlayer.onClick(event.getView().getTitle(), (Player) event.getWhoClicked(), event.getSlot());
        event.setCancelled(b);
    }
}
