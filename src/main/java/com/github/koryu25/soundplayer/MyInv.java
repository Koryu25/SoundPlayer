package com.github.koryu25.soundplayer;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class MyInv {

    public void test(Player player) {
        ChestGui gui = new ChestGui(6, "MyInv");

        StaticPane pane = new StaticPane(0, 0, 9, 6);

        ItemStack item = new ItemStack(Material.BEDROCK);
        GuiItem guiItem = new GuiItem(item, event -> {
            event.setCancelled(true);
            event.getWhoClicked().sendMessage("岩盤「やぁ」");
        });

        pane.addItem(guiItem, 1, 1);

        gui.addPane(pane);

        gui.show(player);
    }
}
