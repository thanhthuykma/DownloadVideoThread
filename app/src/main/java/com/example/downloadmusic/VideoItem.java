package com.example.downloadmusic;

public class VideoItem {
    public String url;
    boolean downloaded;
    private int progress = 0;
    private boolean isDownloading = false;

    public VideoItem(String url) {
        this.url = url;
        this.downloaded = false;
    }

    public String getUrl() {
        return url;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }
    public boolean isDownloading(){
        return isDownloading;
    }
    public void setDownloading(boolean downloading){
        isDownloading = downloading;
    }

    public boolean isDownloaded()
    {
        return downloaded;
    }

    public void setDownloaded(boolean downloaded) {
        this.downloaded = downloaded;
    }
}
