package com.example.downloadmusic;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class DownloadReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(intent.getAction())){
        Toast.makeText(context,"Tải video hoàn tất",Toast.LENGTH_SHORT).show();
    }
    }
}
