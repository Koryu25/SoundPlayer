package com.github.koryu25.soundplayer.sound;

import lombok.Getter;
import org.bukkit.Sound;

import java.util.ArrayList;
import java.util.List;

public class AllSoundDataList {

    public static final int pageSize = 45;

    private static List<SoundData> soundDataList;

    /**
     * 全データの読み込み
     */
    public static void initialize() {
        soundDataList = new ArrayList<>();
        for (int i = 0; i < Sound.values().length; i++) {
            soundDataList.add(new SoundData(Sound.values()[i], i));
        }
    }

    /**
     * すべてのデータを取得
     * @return すべてのサウンドデータ
     */
    public static List<SoundData> get() {
        return soundDataList;
    }

    /**
     * 文字列からサウンドデータを検索
     * @param word 検索に使うワード
     * @return wordが含まれるサウンドデータのリスト
     */
    public static List<SoundData> search(String word) {
        List<SoundData> searchedList = new ArrayList<>();
        soundDataList.forEach(soundData -> {
            if (soundData.getName().contains(word.toUpperCase())) searchedList.add(soundData);
        });
        return searchedList;
    }
}
