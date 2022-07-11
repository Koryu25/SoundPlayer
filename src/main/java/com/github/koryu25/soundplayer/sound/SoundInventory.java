package com.github.koryu25.soundplayer.sound;

import com.github.koryu25.soundplayer.SoundPlayer;
import com.github.koryu25.soundplayer.yaml.lang.LangConfig;
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
import java.util.Arrays;
import java.util.List;

public class SoundInventory {

    public static final String TITLE = "[Sound Player]";

    private Audience audience;
    private List<SoundData> soundDataList;
    private int page;//ページ数
    private int maxSlot;//現在のページの最後尾slot

    @Getter
    @Setter
    private int currentPage;//現在のページ

    @Getter
    private Inventory inventory;


    public SoundInventory(Audience audience, List<SoundData> soundDataList, String suffix) {
        this.audience = audience;
        this.soundDataList = soundDataList;
        currentPage = 0;
        inventory = Bukkit.createInventory(null, 54, TITLE + suffix);

        //pageを算出
        page = soundDataList.size() / 45;
        if (soundDataList.size() % 45 > 0) page++;
        page--;

        //maxSlotとInventoryを初期化
        initializeMaxSlot();
        initializeInventory();
    }

    //maxSlotを初期化
    private void initializeMaxSlot() {
        if (page == -1) maxSlot = -1;
        else if (currentPage == page) maxSlot = soundDataList.size() % 45;
        else maxSlot = 45;
    }

    //Inventoryを初期化
     private void initializeInventory() {
         LangConfig lang = SoundPlayer.getLangConfig();

         for (int index = 0; index < maxSlot; index++) { //サウンドデータを可能な限り追加する
             inventory.setItem(index, soundDataList.get(currentPage * 45 + index).getItemStack());
         }

         //ページ操作のアイテムを生成
         String pageLore = "§9" + currentPage + "/" + page;
         ItemStack previousPage = createItemStack(
                 Material.ARROW,
                 lang.getPreviousPage(),
                 pageLore
         );
         inventory.setItem(45, previousPage);
         ItemStack nextPage = createItemStack(
                 Material.ARROW,
                 lang.getNextPage(),
                 pageLore
         );
         inventory.setItem(53, nextPage);

         // 音量操作のアイテムを生成
         updateVolume();

         // 音の高さを操作するアイテムを生成
         updatePitch();

         // 変更差を操作するアイテムを生成
         updateDifferent();

         // 現在の情報が見れるアイテムの生成
         updateInformation();
    }

    /**
     * Volumeの情報、ItemStackを初期化
     */
    public void updateVolume() {
        LangConfig lang = SoundPlayer.getLangConfig();

        String different = String.format("%.03f", audience.getDifferent());
        ItemStack volumeDown = new ItemStack(Material.CHICKEN);
        ItemMeta volumeDownMeta = volumeDown.getItemMeta();
        volumeDownMeta.setDisplayName(lang.getVolume() + lang.getDown() + " -" + different);
        List<String> volumeLore = new ArrayList<>();
        volumeLore.add(ChatColor.RED + lang.getVolume() + ChatColor.WHITE + ": " + String.format("%.03f", audience.getVolume()));
        volumeDownMeta.setLore(volumeLore);
        volumeDown.setItemMeta(volumeDownMeta);
        inventory.setItem(46, volumeDown);
        ItemStack volumeUp = new ItemStack(Material.COOKED_CHICKEN);
        ItemMeta volumeUpMeta = volumeUp.getItemMeta();
        volumeUpMeta.setDisplayName(lang.getVolume() + lang.getUp() + " +" + different);
        volumeUpMeta.setLore(volumeLore);
        volumeUp.setItemMeta(volumeUpMeta);
        inventory.setItem(47, volumeUp);
    }

    /**
     * Pitchの情報、ItemStackを初期化
     */
    public void updatePitch() {
        LangConfig lang = SoundPlayer.getLangConfig();

        String different = String.format("%.03f", audience.getDifferent());
        ItemStack pitchDown = new ItemStack(Material.LIGHT_BLUE_DYE);
        ItemMeta pitchDownMeta = pitchDown.getItemMeta();
        pitchDownMeta.setDisplayName(lang.getPitch() + lang.getDown() + " -" + different);
        List<String> pitchLore = new ArrayList<>();
        pitchLore.add(ChatColor.AQUA + lang.getPitch() + ChatColor.WHITE + ": " + String.format("%.03f", audience.getPitch()));
        pitchDownMeta.setLore(pitchLore);
        pitchDown.setItemMeta(pitchDownMeta);
        inventory.setItem(51, pitchDown);
        ItemStack pitchUp = new ItemStack(Material.RED_DYE);
        ItemMeta pitchUpMeta = pitchUp.getItemMeta();
        pitchUpMeta.setDisplayName(lang.getPitch() + lang.getUp() + " +" + different);
        pitchUpMeta.setLore(pitchLore);
        pitchUp.setItemMeta(pitchUpMeta);
        inventory.setItem(52, pitchUp);
    }

    /**
     * Differentの情報、ItemStackを初期化
     */
    public void updateDifferent() {
        LangConfig lang = SoundPlayer.getLangConfig();

        String different = String.format("%.3f", audience.getDifferent());
        ItemStack differentDown = new ItemStack(Material.OAK_SLAB);
        ItemMeta differentDownMeta = differentDown.getItemMeta();
        differentDownMeta.setDisplayName(lang.getDifference() + " 0.1 " + lang.getMultiply());
        List<String> differentLore = new ArrayList<>();
        differentLore.add(ChatColor.YELLOW + lang.getDifference() + ChatColor.WHITE + ": " + different);
        differentDownMeta.setLore(differentLore);
        differentDown.setItemMeta(differentDownMeta);
        inventory.setItem(48, differentDown);
        ItemStack differentUp = new ItemStack(Material.OAK_PLANKS);
        ItemMeta differentUpMeta = differentUp.getItemMeta();
        differentUpMeta.setDisplayName(lang.getDifference() + " 10 " + lang.getMultiply());
        differentUpMeta.setLore(differentLore);
        differentUp.setItemMeta(differentUpMeta);
        inventory.setItem(50, differentUp);
    }

    /**
     * VolumeとPitchの情報が記されたItemStackを初期化
     */
    public void updateInformation() {
        inventory.setItem(49, audience.toItemStack());
    }

    /**
     * クリック時の処理
     * @param slot クリックしたスロット
     */
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

    /**
     * SoundPlayerのインベントリであるか確認します
     * @param invTitle インベントリのタイトル
     * @return SoundPlayerのインベントリであればtrue
     */
    public static boolean match(String invTitle) {
        try {
            invTitle = invTitle.substring(0, TITLE.length());
            return invTitle.equals(TITLE);
        } catch (StringIndexOutOfBoundsException e) {
            return false;
        }
    }

    /**
     * 簡易的にItemStackを生成します
     * @param material ItemStackのマテリアル
     * @param name ItemStackの表示名
     * @param lore ItemStackのlore
     * @return 生成されたItemStack
     */
    public static ItemStack createItemStack(Material material, String name, String... lore) {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(name);
        itemMeta.setLore(Arrays.asList(lore));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}
