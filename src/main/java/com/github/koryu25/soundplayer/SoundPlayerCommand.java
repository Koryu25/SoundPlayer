package com.github.koryu25.soundplayer;

import com.github.koryu25.soundplayer.sound.AllSoundDataList;
import com.github.koryu25.soundplayer.sound.Audience;
import com.github.koryu25.soundplayer.sound.SoundInventory;
import dev.jorel.commandapi.annotations.Command;
import dev.jorel.commandapi.annotations.Default;
import dev.jorel.commandapi.annotations.Permission;
import dev.jorel.commandapi.annotations.Subcommand;
import dev.jorel.commandapi.annotations.arguments.AStringArgument;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Command("sr")
public class SoundPlayerCommand {

    @Default
    public static void sp(CommandSender sender) {
        SoundPlayer.getLangConfig().getHelpMessages().forEach(sender::sendMessage);
    }

    @Subcommand("all")
    @Permission("sr.all")
    public static void all(Player player) {
        Audience audience = SoundPlayer.searchAudience(player);
        audience.setSoundInventory(new SoundInventory(audience, AllSoundDataList.get(), ""));
        audience.open();
    }

    @Subcommand("reset")
    @Permission("sr.reset")
    public static void reset(Player player) {
        Audience audience = SoundPlayer.searchAudience(player);
        audience.reset();
        player.sendMessage(SoundPlayer.getLangConfig().getResetMessage());
    }

    @Subcommand("search")
    @Permission("sr.search")
    public static void search(
            Player player,
            @AStringArgument String keyWord) {
        Audience audience = SoundPlayer.searchAudience(player);
        audience.setSoundInventory(new SoundInventory(audience, AllSoundDataList.search(keyWord), keyWord));
        audience.open();
    }
}
