package com.benmu.erospluginumeng.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Carry on 2017/10/16.
 */

public class ShareInfoBean implements Serializable{
    private String title;
    private String content;
    private String image;
    private String url;
    private String platform;
    private String path;
    private String shareType;
    //是否显示分享弹窗
    private boolean popUp;
    //分享的媒体类型  WEB  IMAGE VIDEO
    private String mediaType="WEB";

    private String userName;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public boolean isPopUp() {
        return popUp;
    }

    public void setPopUp(boolean popUp) {
        this.popUp = popUp;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getShareType() {
        return shareType;
    }

    public void setShareType(String shareType) {
        this.shareType = shareType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
