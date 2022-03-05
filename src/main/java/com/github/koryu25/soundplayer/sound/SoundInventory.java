package com.github.koryu25.soundplayer.sound;

import com.github.koryu25.soundplayer.SoundPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Material;
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
    private int currentPage;// 現在のページ
    private Inventory inventory;
    private int maxSlot;// 現在のページの最後尾slot

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
        previousPageMeta.setDisplayName("to previous page");
        List<String> pageLore = new ArrayList<>();
        pageLore.add(currentPage + "/" + page);
        previousPageMeta.setLore(pageLore);
        previousPage.setItemMeta(previousPageMeta);
        inventory.setItem(45, previousPage);
        ItemStack nextPage = new ItemStack(Material.ARROW);
        ItemMeta nextPageMeta = nextPage.getItemMeta();
        nextPageMeta.setDisplayName("to next page");
        nextPageMeta.setLore(pageLore);
        nextPage.setItemMeta(nextPageMeta);
        inventory.setItem(53, nextPage);
        // volume
        updateVolume();
         // pitch
         updatePitch();
         // information
         updateInformation();
    }

    // Volumeの情報、ItemStackを初期化
    public void updateVolume() {
        String volumeDifferent = String.format("%.03f", SoundPlayer.getMyConfig().getVolumeDifferent());
        ItemStack volumeUp = new ItemStack(Material.CHICKEN);
        ItemMeta volumeUpMeta = volumeUp.getItemMeta();
        volumeUpMeta.setDisplayName("volume down -" + volumeDifferent);
        List<String> volumeLore = new ArrayList<>();
        volumeLore.add("volume: " + String.format("%.03f", audience.getVolume()));
        volumeUpMeta.setLore(volumeLore);
        volumeUp.setItemMeta(volumeUpMeta);
        inventory.setItem(47, volumeUp);
        ItemStack volumeDown = new ItemStack(Material.COOKED_CHICKEN);
        ItemMeta volumeDownMeta = volumeDown.getItemMeta();
        volumeDownMeta.setDisplayName("volume up +" + volumeDifferent);
        volumeDownMeta.setLore(volumeLore);
        volumeDown.setItemMeta(volumeDownMeta);
        inventory.setItem(48, volumeDown);
    }
    // Pitchの情報、ItemStackを初期化
    public void updatePitch() {
        String pitchDifferent = String.format("%.03f", SoundPlayer.getMyConfig().getPitchDifferent());
        ItemStack pitchUp = new ItemStack(Material.LIGHT_BLUE_DYE);
        ItemMeta pitchUpMeta = pitchUp.getItemMeta();
        pitchUpMeta.setDisplayName("pitch down -" + pitchDifferent);
        List<String> pitchLore = new ArrayList<>();
        pitchLore.add("pitch: " + String.format("%.03f", audience.getPitch()));
        pitchUpMeta.setLore(pitchLore);
        pitchUp.setItemMeta(pitchUpMeta);
        inventory.setItem(50, pitchUp);
        ItemStack pitchDown = new ItemStack(Material.RED_DYE);
        ItemMeta pitchDownMeta = pitchDown.getItemMeta();
        pitchDownMeta.setDisplayName("pitch up +" + pitchDifferent);
        pitchDownMeta.setLore(pitchLore);
        pitchDown.setItemMeta(pitchDownMeta);
        inventory.setItem(51, pitchDown);
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
            return;
        }
        // volume down
        if (slot == 47) {
            audience.volumeDown();
            updateVolume();
            updateInformation();
            return;
        }
        // volume up
        if (slot == 48) {
            audience.volumeUp();
            updateVolume();
            updateInformation();
            return;
        }
        // pitch down
        if (slot == 50) {
            audience.pitchDown();
            updatePitch();
            updateInformation();
            return;
        }
        // pitch up
        if (slot == 51) {
            audience.pitchUp();
            updatePitch();
            updateInformation();
            return;
        }
    }

    // Getter, Setter
    public int getCurrentPage() {
        return currentPage;
    }
    public Inventory getInventory() {
        return inventory;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
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
