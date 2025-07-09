package com.example.downloadmusic;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    Button btnDownLoad;
    List<VideoItem> videoItemList = new ArrayList<>();
    VideoAdapter videoAdapter;
    ExecutorService executorService = Executors.newFixedThreadPool(5);
    Handler mainHandler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listView);
        btnDownLoad = findViewById(R.id.btndownload);
        loadJsonFromAssets();
        videoAdapter = new VideoAdapter(this, videoItemList);
        listView.setAdapter(videoAdapter);
        btnDownLoad.setOnClickListener(v -> {

            if (videoItemList != null && !videoItemList.isEmpty()) {
                Toast.makeText(this,"Đang tải...",Toast.LENGTH_SHORT).show();
                for (int i = 0; i < videoItemList.size(); i++) {
                    final int index = i;
                    try {
                        executorService.submit(() -> downloadVideo(index));
                    } catch (Exception e) {
                        runOnUiThread(() -> Toast.makeText(this, "Không thể bắt đầu tải", Toast.LENGTH_SHORT).show());
                    }
                }
            } else {
                Toast.makeText(this, "Không có video để tải", Toast.LENGTH_SHORT).show();
            }

        });
    }

    private void downloadVideo(int index) {
        VideoItem item = videoItemList.get(index);
        String url = item.getUrl();
        //item.setDownloaded(true);
        // Log.d("download", "Đang tải video: " + url);
        /*mainHandler.post(() -> {
            Toast.makeText(MainActivity.this, "Đang tải url: " + url, Toast.LENGTH_SHORT).show();
        });*/

        try{
            URL url1 = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) url1.openConnection();
            connection.connect();

            //Đường dẫn lưu video
            File file = new File(getExternalFilesDir(null), "video" + index + ".mp4");
            InputStream input = connection.getInputStream();
            FileOutputStream output = new FileOutputStream(file);

            byte[] buffer = new byte[4096];
            int len;
            while ((len = input.read(buffer)) != -1){
                output.write(buffer,0,len);
            }
            output.close();
            input.close();

            item.setDownloaded(true);
            mainHandler.post(()-> videoAdapter.notifyDataSetChanged());
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("DownloadError", "Lỗi tải: " + url, e);

            mainHandler.post(() -> {
                Toast.makeText(MainActivity.this, "Lỗi tải: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            });
        }
    }

    private void loadJsonFromAssets(){
        try {
            InputStream is = getAssets().open("video2.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            String json = new String(buffer, "UTF-8");
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("videos");
            for (int i=0; i<jsonArray.length(); i++){
                videoItemList.add(new VideoItem(jsonArray.getString(i)));
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}