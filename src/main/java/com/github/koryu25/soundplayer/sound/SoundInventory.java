package com.github.koryu25.soundplayer.sound;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class SoundInventory {

    public static final String title = "[Sound Player]";

    private Audience audience;
    private List<SoundData> soundDataList;
    private int page;// ページ数
    private int maxSlot;// 現在のページの最後尾slot

    @Getter
    @Setter
    private int currentPage;// 現在のページ

    @Getter
    private Inventory inventory;


    public SoundInventory(Audience audience, List<SoundData> soundDataList, String suffix) {
        this.audience = audience;
        this.soundDataList = soundDataList;
        currentPage = 0;
        inventory = Bukkit.createInventory(null, 54, title + suffix);
        // pageを算出
        page = soundDataList.size() / 45;
        if (soundDataList.size() % 45 > 0) page++;
        page--;
        // 初期化
        initializeMaxSlot();
        initializeInventory();
    }

    // maxSlotを初期化
    private void initializeMaxSlot() {
        if (page == -1) maxSlot = -1;
        else if (currentPage == page) maxSlot = soundDataList.size() % 45;
        else maxSlot = 45;
    }

    // Inventoryを初期化
     private void initializeInventory() {
        // sound
         for (int index = 0; index < maxSlot; index++) {
             inventory.setItem(index, soundDataList.get(currentPage * 45 + index).getItemStack());
         }
        // page
        ItemStack previousPage = new ItemStack(Material.ARROW);
        ItemMeta previousPageMeta = previousPage.getItemMeta();
        previousPageMeta.setDisplayName("前のページへ");
        List<String> pageLore = new ArrayList<>();
        pageLore.add("§9" + currentPage + "/" + page);
        previousPageMeta.setLore(pageLore);
        previousPage.setItemMeta(previousPageMeta);
        inventory.setItem(45, previousPage);
        ItemStack nextPage = new ItemStack(Material.ARROW);
        ItemMeta nextPageMeta = nextPage.getItemMeta();
        nextPageMeta.setDisplayName("次のページへ");
        nextPageMeta.setLore(pageLore);
        nextPage.setItemMeta(nextPageMeta);
        inventory.setItem(53, nextPage);
        // volume
        updateVolume();
         // pitch
         updatePitch();
         // different
         updateDifferent();
         // information
         updateInformation();
    }

    // Volumeの情報、ItemStackを初期化
    public void updateVolume() {
        String different = String.format("%.03f", audience.getDifferent());
        ItemStack volumeDown = new ItemStack(Material.CHICKEN);
        ItemMeta volumeDownMeta = volumeDown.getItemMeta();
        volumeDownMeta.setDisplayName("ボリュームダウン -" + different);
        List<String> volumeLore = new ArrayList<>();
        volumeLore.add(ChatColor.RED + "ボリューム" + ChatColor.WHITE + ": " + String.format("%.03f", audience.getVolume()));
        volumeDownMeta.setLore(volumeLore);
        volumeDown.setItemMeta(volumeDownMeta);
        inventory.setItem(46, volumeDown);
        ItemStack volumeUp = new ItemStack(Material.COOKED_CHICKEN);
        ItemMeta volumeUpMeta = volumeUp.getItemMeta();
        volumeUpMeta.setDisplayName("ボリュームアップ +" + different);
        volumeUpMeta.setLore(volumeLore);
        volumeUp.setItemMeta(volumeUpMeta);
        inventory.setItem(47, volumeUp);
    }
    // Pitchの情報、ItemStackを初期化
    public void updatePitch() {
        String different = String.format("%.03f", audience.getDifferent());
        ItemStack pitchDown = new ItemStack(Material.LIGHT_BLUE_DYE);
        ItemMeta pitchDownMeta = pitchDown.getItemMeta();
        pitchDownMeta.setDisplayName("ピッチダウン -" + different);
        List<String> pitchLore = new ArrayList<>();
        pitchLore.add(ChatColor.AQUA + "ピッチ" + ChatColor.WHITE + ": " + String.format("%.03f", audience.getPitch()));
        pitchDownMeta.setLore(pitchLore);
        pitchDown.setItemMeta(pitchDownMeta);
        inventory.setItem(51, pitchDown);
        ItemStack pitchUp = new ItemStack(Material.RED_DYE);
        ItemMeta pitchUpMeta = pitchUp.getItemMeta();
        pitchUpMeta.setDisplayName("ピッチアップ +" + different);
        pitchUpMeta.setLore(pitchLore);
        pitchUp.setItemMeta(pitchUpMeta);
        inventory.setItem(52, pitchUp);
    }
    // Differentの情報、ItemStackを初期化
    public void updateDifferent() {
        String different = String.format("%.3f", audience.getDifferent());
        ItemStack differentDown = new ItemStack(Material.OAK_SLAB);
        ItemMeta differentDownMeta = differentDown.getItemMeta();
        differentDownMeta.setDisplayName("変更差0.1倍");
        List<String> differentLore = new ArrayList<>();
        differentLore.add(ChatColor.YELLOW + "変更差" + ChatColor.WHITE + ": " + different);
        differentDownMeta.setLore(differentLore);
        differentDown.setItemMeta(differentDownMeta);
        inventory.setItem(48, differentDown);
        ItemStack differentUp = new ItemStack(Material.OAK_PLANKS);
        ItemMeta differentUpMeta = differentUp.getItemMeta();
        differentUpMeta.setDisplayName("変更差10倍");
        differentUpMeta.setLore(differentLore);
        differentUp.setItemMeta(differentUpMeta);
        inventory.setItem(50, differentUp);
    }
    // VolumeとPitchの情報が記されたItemStackを初期化
    public void updateInformation() {
        inventory.setItem(49, audience.toItemStack());
    }

    // クリック時の処理
    public void onClick(int slot) {
        // サウンド再生
        if (slot < maxSlot) soundDataList.get(currentPage * 45 + slot).play(audience);
        // 前のページ
        if (slot == 45) {
            // ページが存在するか
            if (currentPage == 0) return;
            currentPage--;
            initializeMaxSlot();
            initializeInventory();
            audience.open();
            audience.playSound(Sound.ENTITY_ARROW_SHOOT, 1f, 0.8f);
            return;
        }
        // 次のページ
        if (slot == 53) {
            // ページが存在するか
            if (currentPage >= page) return;
            currentPage++;
            initializeMaxSlot();
            initializeInventory();
            audience.open();
            audience.playSound(Sound.ENTITY_ARROW_SHOOT, 1f, 0.8f);
            return;
        }
        // volume down
        if (slot == 46) {
            audience.volumeDown();
            updateVolume();
            updateInformation();
            audience.playSound(Sound.UI_BUTTON_CLICK, 1, 0.8f);
            return;
        }
        // volume up
        if (slot == 47) {
            audience.volumeUp();
            updateVolume();
            updateInformation();
            audience.playSound(Sound.UI_BUTTON_CLICK, 1, 1.3f);
            return;
        }
        // pitch down
        if (slot == 51) {
            audience.pitchDown();
            updatePitch();
            updateInformation();
            audience.playSound(Sound.UI_BUTTON_CLICK, 1, 0.8f);
            return;
        }
        // pitch up
        if (slot == 52) {
            audience.pitchUp();
            updatePitch();
            updateInformation();
            audience.playSound(Sound.UI_BUTTON_CLICK, 1, 1.3f);
            return;
        }
        // different down
        if (slot == 48) {
            audience.differentDown();
            updateDifferent();
            updateVolume();
            updatePitch();
            updateInformation();
            audience.playSound(Sound.UI_BUTTON_CLICK, 1, 0.8f);
            return;
        }
        // different up
        if (slot == 50) {
            audience.differentUp();
            updateDifferent();
            updateVolume();
            updatePitch();
            updateInformation();
            audience.playSound(Sound.UI_BUTTON_CLICK, 1, 1.3f);
            return;
        }
    }


    // Inventoryのtitleが合致するか確認するメソッド
    public static boolean match(String invTitle) {
        try {
            invTitle = invTitle.substring(0, title.length());
            return invTitle.equals(title);
        } catch (StringIndexOutOfBoundsException e) {
            return false;
        }
    }
}
