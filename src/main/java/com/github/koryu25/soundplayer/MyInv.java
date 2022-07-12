package com.github.koryu25.soundplayer;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class MyInv {

    public MyInv() {
    }

    public void test(Player player) {
        ChestGui gui = new ChestGui(6, "MyInv");
        OutlinePane pane = new OutlinePane(0, 0, 9, 6);
        ItemStack item = new ItemStack(Material.BEDROCK);
        GuiItem guiItem = new GuiItem(item, event -> {
            event.setCancelled(true);
            event.getWhoClicked().sendMessage("岩盤「やぁ」");
        });
        pane.insertItem(guiItem, 3);
        gui.addPane(pane);
        gui.show(player);
    }
}
