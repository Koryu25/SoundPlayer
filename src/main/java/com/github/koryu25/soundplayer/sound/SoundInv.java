package com.github.koryu25.soundplayer.sound;

import com.github.koryu25.soundplayer.SoundPlayer;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class SoundInv {

    public static final String TITLE = "[Sound Player]";

    private Audience audience;
    private List<SoundData> soundDataList;
    private int page;//ページ数
    private int maxSlot;//現在のページの最後尾slot

    @Getter
    @Setter
    private int currentPage;//現在のページ

    public SoundInv(Audience audience, List<SoundData> soundDataList, String suffix) {
        this.audience = audience;
        this.soundDataList = soundDataList;
        currentPage = 0;

        //inventoryを生成
        ChestGui gui = new ChestGui(6, TITLE + suffix);
        gui.addPane(soundPane());
        gui.addPane(operationPane());

        // pageを算出
        page = soundDataList.size() / 45;
        if (soundDataList.size() % 45 > 0) page++;
        page--;
    }

    /**
     * インベントリ作成
     * クリック時の処理
     * 設定の変更
     * サウンドの再生
     */

    /**
     *
     * @return Soundデータが入ったOutlinePane
     */
    private OutlinePane soundPane() {
        OutlinePane pane = new OutlinePane(0, 0, 9, 5);

        for (int i = 0; i < 45; i++) {
            int index = currentPage * 45 + i;
            pane.addItem(new GuiItem(
                    soundDataList.get(index).getItemStack(),
                    event -> soundDataList.get(index).play(audience)));
        }

        return pane;
    }

    /**
     *
     * @return 操作パネルのOutlinePane
     */
    private OutlinePane operationPane() {
        OutlinePane pane = new OutlinePane(0, 5, 9, 1);

        pane.insertItem(previousPageItem(), 0);
        pane.insertItem(nextPageItem(), 8);

        return pane;
    }

    /**
     *
     * @return 前のページに戻るGuiItem
     */
    private GuiItem previousPageItem() {
        return new GuiItem(
                createItemStack(
                        Material.ARROW,
                        SoundPlayer.getLangConfig().getPreviousPage(),
                        "§9" + currentPage + "/" + page
                ),
                event ->{
                    if (currentPage == 0) return;
                    currentPage--;
                    //initializeMaxSlot();
                    //initializeInventory();
                    audience.open();
                    audience.playSound(Sound.ENTITY_ARROW_SHOOT, 1f, 0.8f);
                }
        );
    }

    /**
     *
     * @return 次のページに行くGuiItem
     */
    private GuiItem nextPageItem() {
        return new GuiItem(
                createItemStack(
                        Material.ARROW,
                        SoundPlayer.getLangConfig().getNextPage(),
                        "§9" + currentPage + "/" + page
                ),
                event ->{
                    if (currentPage >= page) return;
                    currentPage++;
                    //initializeMaxSlot();
                    //initializeInventory();
                    audience.open();
                    audience.playSound(Sound.ENTITY_ARROW_SHOOT, 1f, 0.8f);
                });
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
