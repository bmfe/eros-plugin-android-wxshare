package com.benmu.erospluginumeng.utils;

/**
 * Created by Carry on 2018/3/5.
 * 分享媒体类型
 */

public enum ShareMediaType {
    TEXT("Text", "文本类型"),
    IMAGE("Image", "图片类型"),
    TEXTIMAGE("TextImage", "图文类型"),
    WEBPAGE("Webpage", "网页"),
    MUSIC("Music", "音乐类型"),
    VIDEO("Video", "视频类型"),
    MINIPROGRAM("MiniProgram", "小程序");

    private String key;
    private String value;

    ShareMediaType(String key, String value) {
        this.key = key;
        this.value = value;
    }


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
