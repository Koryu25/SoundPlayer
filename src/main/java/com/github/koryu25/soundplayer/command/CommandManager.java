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
        if (!(sender instanceof Player player)) return true;
        Audience audience = SoundPlayer.searchAudience(player);
        // sp
        if (args.length == 0) {
            audience.setSoundInventory(new SoundInventory(audience, AllSoundDataList.get(), ""));
            audience.open();
            return true;
        }
        if (args.length == 1) {
            // sp help
            if (args[0].equalsIgnoreCase("help")) {
                sendHelp(player);
                return true;
            }
            // sp reset
            if (args[0].equalsIgnoreCase("reset")) {
                audience.reset();
                player.sendMessage("ボリューム、ピッチ、変更差をリセットしました");
                return true;
            }
        }
        // sp search [string]
        if (args.length == 2) {
            if (!args[0].equalsIgnoreCase("search")) return true;
            String word = args[1];
            audience.setSoundInventory(new SoundInventory(audience, AllSoundDataList.search(word), word));
            audience.open();
            return true;
        }
        sendHelp(player);
        return true;
    }

    private void sendHelp(Player player) {
        player.sendMessage("=== SoundPlayer CommandHelp ===");
        player.sendMessage("/sp ですべてのサウンドを表示");
        player.sendMessage("/sp reset でボリューム、ピッチ、変更差をリセット");
        player.sendMessage("/sp search [string] で[string]が含まれるサウンドを表示");
        player.sendMessage("=============================");
    }
}
