package com.github.koryu25.soundplayer.sound;

import com.github.koryu25.soundplayer.SoundPlayer;
import com.github.koryu25.soundplayer.yaml.lang.LangConfig;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Audience {

    @Getter
    private final Player player;

    @Getter
    private float volume; //音量

    @Getter
    private float pitch; //音の高さ

    @Getter
    private float different; //変数の変更差

    @Getter
    @Setter
    private SoundInv soundInv; //サウンドデータが入ったインベントリ

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

    /**
     * null判定後にsoundInventoryを開く
     */
    public void open() {
        if (soundInv == null) return;
        soundInv.getGui().show(player);
    }

    /**
     * プレイヤーの位置に音を流します
     * システム用として使います
     * @param sound 流す音のデータ
     * @param volume 流す音の大きさ
     * @param pitch 流す音の高さ
     */
    public void playSound(Sound sound, float volume, float pitch) {
        player.playSound(player.getLocation(), sound, volume, pitch);
    }

    /**
     * このAudienceに設定されている音の大きさ、音の高さ、変更差をリセットします
     */
    public void reset() {
        volume = SoundPlayer.getMyConfig().getDefaultVolume();
        pitch = SoundPlayer.getMyConfig().getDefaultPitch();
        different = SoundPlayer.getMyConfig().getDefaultDifferent();
    }

    /**
     * Audienceの情報をItemStackに変換します
     * @return Audienceの情報を記載したItemStack
     */
    public ItemStack toItemStack() {
        LangConfig lang = SoundPlayer.getLangConfig();
        ItemStack itemStack = new ItemStack(Material.REDSTONE_BLOCK);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName("Information");
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.RED + lang.getVolume() + ChatColor.WHITE + ": " + String.format("%.3f", volume));
        lore.add(ChatColor.AQUA + lang.getPitch() + ChatColor.WHITE + ": " + String.format("%.3f", pitch));
        lore.add(ChatColor.YELLOW + lang.getDifference() + ChatColor.WHITE + ": " + String.format("%.3f", different));
        meta.setLore(lore);
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    //このAudienceの変更差、音量、音の高さを調整するメソッド
    public void differentUp() {
        different *= 10;
    }
    public void differentDown() {
        different *= 0.1;
    }
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
}
