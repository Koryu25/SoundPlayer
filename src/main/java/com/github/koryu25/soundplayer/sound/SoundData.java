package com.github.koryu25.soundplayer.sound;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SoundData {

    private final Sound sound;
    private final int number;
    private final ItemStack itemStack;

    public SoundData(Sound sound, int number) {
        this.sound = sound;
        this.number = number;
        itemStack = new ItemStack(getMaterial());
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(number + ":" + sound.name().toLowerCase());
        itemStack.setItemMeta(meta);
    }

    // Materialを検索するメソッド
    private Material getMaterial() {
        return Material.JUKEBOX;
    }

    // 再生
    public void play(Audience audience) {
        audience.getPlayer().playSound(
                audience.getPlayer().getLocation(),
                sound,
                audience.getVolume(),
                audience.getPitch()
        );
    }

    // Getter, Setter
    public String getName() {
        return sound.name();
    }
    public Sound getSound() {
        return sound;
    }
    public int getNumber() {
        return number;
    }
    public ItemStack getItemStack() {
        return itemStack;
    }

    /*
    public void setSound(Sound sound) {
        this.sound = sound;
    }
    public void setNumber(int number) {
        this.number = number;
    }
    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }
     */
}
