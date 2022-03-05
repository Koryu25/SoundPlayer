package com.github.koryu25.soundplayer.command;

import com.github.koryu25.soundplayer.SoundPlayer;
import com.github.koryu25.soundplayer.sound.AllSoundDataList;
import com.github.koryu25.soundplayer.sound.Audience;
import com.github.koryu25.soundplayer.sound.SoundInventory;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandManager implements CommandExecutor {

    // コマンドの登録
    public CommandManager(SoundPlayer main) {
        main.getCommand("sp").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player player = (Player) sender;
        Audience audience = SoundPlayer.searchAudience(player);
        // sp
        if (args.length == 0) {
            audience.setSoundInventory(new SoundInventory(audience, AllSoundDataList.get(), ""));
            audience.open();
            return true;
        }
        // sp [string]
        if (args.length == 1) {
            String word = args[0];
            audience.setSoundInventory(new SoundInventory(audience, AllSoundDataList.search(word), word));
            audience.open();
            return true;
        }
        player.sendMessage("/sp -> show all");
        player.sendMessage("/sp [character] -> search by character");
        return true;
    }
}
