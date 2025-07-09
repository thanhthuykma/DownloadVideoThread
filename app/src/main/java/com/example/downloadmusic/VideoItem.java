package com.example.downloadmusic;

public class VideoItem {
    public String url;
    boolean downloaded;

    public VideoItem(String url) {
        this.url = url;
        this.downloaded = false;
    }

    public String getUrl() {
        return url;
    }
    public boolean isDownloaded()
    {
        return downloaded;
    }

    public void setDownloaded(boolean downloaded) {
        this.downloaded = downloaded;
    }
}
