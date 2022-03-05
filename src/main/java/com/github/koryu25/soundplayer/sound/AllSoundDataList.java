package com.github.koryu25.soundplayer.sound;

import org.bukkit.Sound;

import java.util.ArrayList;
import java.util.List;

public class AllSoundDataList {

    public static final int pageSize = 45;

    private static List<SoundData> soundDataList;

    // 初期化
    public static void initialize() {
        soundDataList = new ArrayList<>();
        for (int i = 0; i < Sound.values().length; i++) {
            soundDataList.add(new SoundData(Sound.values()[i], i));
        }
    }

    // 全データの取得
    public static List<SoundData> get() {
        return soundDataList;
    }

    // 検索で取得
    public static List<SoundData> search(String str) {
        List<SoundData> searchedList = new ArrayList<>();
        soundDataList.forEach(soundData -> {
            if (soundData.getName().contains(str.toUpperCase())) searchedList.add(soundData);
        });
        return searchedList;
    }

    // ページで取得
    public static List<SoundData> getPage(int i) {
        List<SoundData> list = new ArrayList<>();
        int startNum = i * pageSize;
        for (int index = startNum; index < startNum + pageSize; index++) {
            list.add(soundDataList.get(index));
        }
        return list;
    }

    public static int size() {
        return soundDataList.size();
    }
}
