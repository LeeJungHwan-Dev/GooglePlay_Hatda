package com.history.hatda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.GridView;
import android.widget.ImageButton;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class imgshow extends AppCompatActivity {

    SingerAdapter adapter;
    GridView gridView;
    ArrayList<String> path = new ArrayList<>();
    ImageButton historyexit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imgshow);


        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.flags &= ~WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(lp);

        gridView = findViewById(R.id.imgshow);
        historyexit = findViewById(R.id.historyexit);

        historyexit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(imgshow.this,history.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                finish();
            }
        });


    try {


        File dir = new File(String.valueOf(getFilesDir()));
        File list[] = dir.listFiles();
        Path sizepath;
        if (list.length != 0) {
            int number = list.length;
            for (int i = 0; i < number; i++) {
                String checkpath = String.valueOf(list[i]);
                sizepath = Paths.get(checkpath);
                if(Files.size(sizepath) != 0) {
                    String typecheck = checkpath.substring(checkpath.lastIndexOf(".") + 1);

                    if (typecheck.equals("jpeg")) {
                        path.add(checkpath);
                    }
                }
            }
        }


        adapter = new SingerAdapter(path, this);

        gridView.setAdapter(adapter);
    } catch (Exception e) {

    }

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(imgshow.this,history.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
        finish();
    }
}