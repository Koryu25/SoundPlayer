package com.github.koryu25.soundplayer.sound;

import com.github.koryu25.soundplayer.SoundPlayer;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Audience {

    private final Player player;
    private float volume;
    private float pitch;
    private SoundInventory soundInventory = null;

    public Audience(Player player) {
        this(player, SoundPlayer.getMyConfig().getDefaultVolume(), SoundPlayer.getMyConfig().getDefaultPitch());
    }
    public Audience(Player player, float volume, float pitch) {
        this.player = player;
        this.volume = volume;
        this.pitch = pitch;
    }

    // SoundInventoryを開く(nullじゃなかったら)
    public void open() {
        if (soundInventory == null) return;
        player.openInventory(soundInventory.getInventory());
    }

    // VolumeとPitchの情報を記したItemStackを取得
    public ItemStack toItemStack() {
        ItemStack itemStack = new ItemStack(Material.REDSTONE_BLOCK);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName("information");
        List<String> lore = new ArrayList<>();
        lore.add("volume: " + String.format("%.3f", volume));
        lore.add("pitch: " + String.format("%.3f", pitch));
        meta.setLore(lore);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    // Upper, Downer
    public void volumeUp() {
        volume += SoundPlayer.getMyConfig().getVolumeDifferent();
    }
    public void volumeDown() {
        volume -= SoundPlayer.getMyConfig().getVolumeDifferent();
    }
    public void pitchUp() {
        pitch += SoundPlayer.getMyConfig().getPitchDifferent();
    }
    public void pitchDown() {
        pitch -= SoundPlayer.getMyConfig().getPitchDifferent();
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
    public SoundInventory getSoundInventory() {
        return soundInventory;
    }

    // Setter
    public void setVolume(float volume) {
        this.volume = volume;
    }
    public void setPitch(float pitch) {
        this.pitch = pitch;
    }
    public void setSoundInventory(SoundInventory soundInventory) {
        this.soundInventory = soundInventory;
    }
}
