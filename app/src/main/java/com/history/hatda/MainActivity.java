package com.history.hatda;


import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.Toast;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;


public class MainActivity extends AppCompatActivity {

    File file,file2;
    String time;

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
        file2 = new File("/data/data/com.history.hatda/files/timeset.txt");

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



        if(!file2.exists()) {
            int hour = 22;
            int minute = 0;

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());

            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, minute);

            time = "PM";

            savefile("time1.txt", time);
            savefile("time2.txt", String.valueOf(hour));
            savefile("time3.txt", String.valueOf(minute));
            savefile("timeset.txt", "1658062850923");

            if (calendar.before(Calendar.getInstance())) {
                calendar.add(Calendar.DATE, 1);
            }

            AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
            if (alarmManager != null) {
                Intent intent = new Intent(this, receivetime.class);
                PendingIntent alarmIntent = PendingIntent.getBroadcast(this, 1, intent, PendingIntent.FLAG_IMMUTABLE);

                alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                        AlarmManager.INTERVAL_DAY, alarmIntent);

            }
        }





    }


    public void savefile(String filename,String date){

        try {
            FileOutputStream fo = openFileOutput(filename,MODE_PRIVATE);
            DataOutputStream dos = new DataOutputStream(fo);
            dos.write(date.getBytes());
            dos.flush();
            dos.close();
        } catch (FileNotFoundException e) {

        } catch (IOException e) {

        }
    }


}