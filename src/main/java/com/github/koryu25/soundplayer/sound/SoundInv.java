package com.github.koryu25.soundplayer.sound;

import com.github.koryu25.soundplayer.SoundPlayer;
import com.github.koryu25.soundplayer.yaml.lang.LangConfig;
import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class SoundInv {

    public static final String TITLE = "[Sound Player]";

    private final Audience audience;
    private final List<SoundData> soundDataList;
    private int page;//ページ数

    @Getter
    @Setter
    private int currentPage;//現在のページ

    @Getter
    private final ChestGui gui;

    public SoundInv(Audience audience, List<SoundData> soundDataList, String suffix) {
        this.audience = audience;
        this.soundDataList = soundDataList;
        currentPage = 0;
        gui = new ChestGui(6, TITLE + suffix);

        // pageを算出
        page = soundDataList.size() / 45;
        if (soundDataList.size() % 45 > 0) page++;
        page--;

        //inventoryを初期化
        updateGui();
    }

    /**
     * maxSlotを更新
     */
    private int calMaxSlot() {
        int maxSlot;
        if (page == -1) maxSlot = -1;
        else if (currentPage == page) maxSlot = soundDataList.size() % 45;
        else maxSlot = 45;
        return maxSlot;
    }

    /**
     * Guiを初期化または更新
     */
    private void updateGui() {
        gui.getPanes().set(0, soundPane());
        gui.getPanes().set(1, operatingPane());
    }

    /**
     *
     * @return Soundデータが入ったOutlinePane
     */
    private OutlinePane soundPane() {
        OutlinePane pane = new OutlinePane(0, 0, 9, 5);

        pane.setOnClick(event -> event.setCancelled(true));

        for (int i = 0; i < calMaxSlot(); i++) {
            int index = currentPage * 45 + i;
            pane.addItem(new GuiItem(
                    soundDataList.get(index).getItemStack(),
                    event -> soundDataList.get(index).play(audience)
            ));
        }

        return pane;
    }

    /**
     *
     * @return 操作パネルのOutlinePane
     */
    private OutlinePane operatingPane() {
        OutlinePane pane = new OutlinePane(0, 5, 9, 1);

        pane.setOnClick(event -> event.setCancelled(true));

        pane.addItem(previousPageItem());
        pane.addItem(volumeDown());
        pane.addItem(volumeUp());
        pane.addItem(differentDown());
        pane.addItem(info());
        pane.addItem(differentUp());
        pane.addItem(pitchDown());
        pane.addItem(pitchUp());
        pane.addItem(nextPageItem());

        return pane;
    }

    // generate GuiItems
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
                    updateGui();
                    audience.playSound(Sound.ENTITY_ARROW_SHOOT, 1f, 0.8f);
                }
        );
    }
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
                    updateGui();
                    audience.playSound(Sound.ENTITY_ARROW_SHOOT, 1f, 0.8f);
                });
    }
    private GuiItem volumeDown() {
        LangConfig lang = SoundPlayer.getLangConfig();
        return new GuiItem(
                createItemStack(
                        Material.CHICKEN,
                        lang.getVolume() + lang.getDown() + " -" + String.format("%.03f", audience.getDifferent()),
                        ChatColor.RED + lang.getVolume() + ChatColor.WHITE + ": " + String.format("%.03f", audience.getVolume())
                ),
                event -> {
                    audience.volumeDown();
                    updateGui();
                    audience.playSound(Sound.UI_BUTTON_CLICK, 1, 0.8f);
                }
        );
    }
    private GuiItem volumeUp() {
        LangConfig lang = SoundPlayer.getLangConfig();
        return new GuiItem(
                createItemStack(
                        Material.COOKED_CHICKEN,
                        lang.getVolume() + lang.getUp() + " +" + String.format("%.03f", audience.getDifferent()),
                        ChatColor.RED + lang.getVolume() + ChatColor.WHITE + ": " + String.format("%.03f", audience.getVolume())
                ),
                event -> {
                    audience.volumeUp();
                    updateGui();
                    audience.playSound(Sound.UI_BUTTON_CLICK, 1, 0.8f);
                }
        );
    }
    private GuiItem pitchDown() {
        LangConfig lang = SoundPlayer.getLangConfig();
        return new GuiItem(
                createItemStack(
                        Material.LIGHT_BLUE_DYE,
                        lang.getPitch() + lang.getDown() + " -" + String.format("%.03f", audience.getDifferent()),
                        ChatColor.AQUA + lang.getPitch() + ChatColor.WHITE + ": " + String.format("%.03f", audience.getPitch())
                ),
                event -> {
                    audience.pitchDown();
                    updateGui();
                    audience.playSound(Sound.UI_BUTTON_CLICK, 1, 0.8f);
                }
        );
    }
    private GuiItem pitchUp() {
        LangConfig lang = SoundPlayer.getLangConfig();
        return new GuiItem(
                createItemStack(
                        Material.RED_DYE,
                        lang.getPitch() + lang.getUp() + " +" + String.format("%.03f", audience.getDifferent()),
                        ChatColor.AQUA + lang.getPitch() + ChatColor.WHITE + ": " + String.format("%.03f", audience.getPitch())
                ),
                event -> {
                    audience.pitchUp();
                    updateGui();
                    audience.playSound(Sound.UI_BUTTON_CLICK, 1, 0.8f);
                }
        );
    }
    private GuiItem differentDown() {
        LangConfig lang = SoundPlayer.getLangConfig();
        return new GuiItem(
                createItemStack(
                        Material.OAK_SLAB,
                        lang.getDifference() + " 0.1 " + lang.getMultiply(),
                        ChatColor.YELLOW + lang.getDifference() + ChatColor.WHITE + ": " + String.format("%.3f", audience.getDifferent())
                ),
                event -> {
                    audience.differentDown();
                    updateGui();
                    audience.playSound(Sound.UI_BUTTON_CLICK, 1, 0.8f);
                }
        );
    }
    private GuiItem differentUp() {
        LangConfig lang = SoundPlayer.getLangConfig();
        return new GuiItem(
                createItemStack(
                        Material.OAK_PLANKS,
                        lang.getDifference() + " 10 " + lang.getMultiply(),
                        ChatColor.YELLOW + lang.getDifference() + ChatColor.WHITE + ": " + String.format("%.3f", audience.getDifferent())
                ),
                event -> {
                    audience.differentUp();
                    updateGui();
                    audience.playSound(Sound.UI_BUTTON_CLICK, 1, 0.8f);
                }
        );
    }
    private GuiItem info() {
        return new GuiItem(audience.toItemStack());
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
