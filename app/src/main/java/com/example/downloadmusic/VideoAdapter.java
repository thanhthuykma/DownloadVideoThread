package com.example.downloadmusic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class VideoAdapter extends ArrayAdapter<VideoItem> {
    public VideoAdapter(Context context, List<VideoItem> videos){
        super(context,0,videos);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        VideoItem videoItem = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_video, parent, false);
        }
            TextView url = convertView.findViewById(R.id.tvUrl);
        ProgressBar progressBar = convertView.findViewById(R.id.progressbar);
        TextView txtPer = convertView.findViewById(R.id.txtper);

            url.setText(videoItem.getUrl());
        if(videoItem.isDownloading()){
            progressBar.setVisibility(View.VISIBLE);
            txtPer.setVisibility(View.VISIBLE);
            progressBar.setProgress(videoItem.getProgress());
            txtPer.setText(videoItem.getProgress() + "%");
        }else {
            progressBar.setVisibility(View.GONE);
            txtPer.setVisibility(View.GONE);
        }
            return convertView;
        }
}
