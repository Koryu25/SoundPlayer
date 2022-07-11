package com.github.koryu25.soundplayer.sound;

import lombok.Data;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

@Data
public class SoundData {

    private final Sound sound;
    private final int number;
    private final ItemStack itemStack;

    public SoundData(Sound sound, int number) {
        this.sound = sound;
        this.number = number;
        itemStack = new ItemStack(Material.JUKEBOX);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(number + ":" + sound.name().toLowerCase());
        itemStack.setItemMeta(meta);
    }

    /**
     * 音を流すメソッド
     * @param audience このaudienceの位置で音を流します
     */
    public void play(Audience audience) {
        audience.getPlayer().playSound(
                audience.getPlayer().getLocation(),
                sound,
                audience.getVolume(),
                audience.getPitch()
        );
    }

    /**
     * サウンドデータの名前を取得
     * @return サウンドデータの名前
     */
    public String getName() {
        return sound.name();
    }
}
