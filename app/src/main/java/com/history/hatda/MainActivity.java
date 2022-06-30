package com.history.hatda;


import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Calendar;


public class MainActivity extends AppCompatActivity {

    File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.flags &= ~WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(lp);



        file = new File("/data/data/com.history.hatda/files/password.txt");

        if(file.exists()){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(MainActivity.this,getsetpassword.class);
                    intent.putExtra("비밀번호","2");
                    startActivity(intent);
                    finish();
                    overridePendingTransition(R.anim.fadein,R.anim.fadeout);
                }
            },2000);
        }
        else{
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent2 = new Intent(MainActivity.this,history.class);
                    startActivity(intent2);
                    finish();
                    overridePendingTransition(R.anim.fadein,R.anim.fadeout);
                }
            },2000);
        }

    }
}