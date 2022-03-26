package com.github.koryu25.soundplayer.sound;

import com.github.koryu25.soundplayer.SoundPlayer;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Audience {

    private final Player player;
    private float volume;
    private float pitch;
    private float different;
    private SoundInventory soundInventory = null;

    public Audience(Player player) {
        this(
                player,
                SoundPlayer.getMyConfig().getDefaultVolume(),
                SoundPlayer.getMyConfig().getDefaultPitch(),
                SoundPlayer.getMyConfig().getDefaultDifferent()
        );
    }
    public Audience(Player player, float volume, float pitch, float different) {
        this.player = player;
        this.volume = volume;
        this.pitch = pitch;
        this.different = different;
    }

    // SoundInventoryを開く(nullじゃなかったら)
    public void open() {
        if (soundInventory == null) return;
        player.openInventory(soundInventory.getInventory());
    }

    public void playSound(Sound sound, float volume, float pitch) {
        player.playSound(player.getLocation(), sound, volume, pitch);
    }

    public void reset() {
        volume = SoundPlayer.getMyConfig().getDefaultVolume();
        pitch = SoundPlayer.getMyConfig().getDefaultPitch();
        different = SoundPlayer.getMyConfig().getDefaultDifferent();
    }

    // VolumeとPitchの情報を記したItemStackを取得
    public ItemStack toItemStack() {
        ItemStack itemStack = new ItemStack(Material.REDSTONE_BLOCK);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName("information");
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.RED + "volume" + ChatColor.WHITE + ": " + String.format("%.3f", volume));
        lore.add(ChatColor.AQUA + "pitch" + ChatColor.WHITE + ": " + String.format("%.3f", pitch));
        lore.add(ChatColor.YELLOW + "different" + ChatColor.WHITE + ": " + String.format("%.3f", different));
        meta.setLore(lore);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    // different
    public void differentUp() {
        different *= 10;
    }
    public void differentDown() {
        different *= 0.1;
    }

    // Upper, Downer
    public void volumeUp() {
        volume += different;
    }
    public void volumeDown() {
        volume -= different;
    }
    public void pitchUp() {
        pitch += different;
    }
    public void pitchDown() {
        pitch -= different;
    }

    // Getter
    public Player getPlayer() {
        return player;
    }
    public float getVolume() {
        return volume;
    }
    public float getPitch() {
        return pitch;
    }
    public float getDifferent() {
        return different;
    }
    public SoundInventory getSoundInventory() {
        return soundInventory;
    }

    // Setter
    public void setSoundInventory(SoundInventory soundInventory) {
        this.soundInventory = soundInventory;
    }
}
