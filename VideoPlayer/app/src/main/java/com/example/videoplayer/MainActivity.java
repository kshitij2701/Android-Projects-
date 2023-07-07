package com.example.videoplayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.os.Bundle;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<File> fileList;
    File path = new File(System.getenv("EXTERNAL_STORAGE"));
    CustomAdapter customAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        askPermission();
    }

    private void askPermission() {
        Dexter.withContext(this).withPermission(Manifest.permission.READ_EXTERNAL_STORAGE).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                displayFiles();

            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                Toast.makeText(MainActivity.this, "Storage Permission is Required!!", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();

            }
        }).check();

    }

    private void displayFiles() {
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        fileList = new ArrayList<>();
        fileList.addAll(findVideos(path));
        customAdapter = new CustomAdapter(this,fileList);
        customAdapter.setHasStableIds(true);
        recyclerView.setAdapter(customAdapter);

    }

    private ArrayList<File> findVideos(File file)
    {
        ArrayList<File> myVideos = new ArrayList<>();
        File[] allFiles = file.listFiles();

        for (File singleFile : allFiles){
            if (singleFile.isDirectory() &&singleFile.isHidden()){
                myVideos.addAll(findVideos(singleFile));
            }
            else if (singleFile.getName().toLowerCase().endsWith(".mp4")){
                myVideos.add(singleFile);
            }
        }
        return myVideos;
    }


}