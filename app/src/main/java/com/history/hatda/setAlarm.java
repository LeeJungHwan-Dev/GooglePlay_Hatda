package com.history.hatda;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;

import android.content.Context;
import android.content.Intent;

import android.os.Build;
import android.os.Bundle;

import android.view.WindowManager;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;


import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;


public class setAlarm extends AppCompatActivity {

    private Button save;
    private TimePicker timePicker;
    String time;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_alarm);

        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.flags &= ~WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(lp);

        timePicker= findViewById(R.id.timePicker);
        save= findViewById(R.id.button);

        save.setOnClickListener(v->{

            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            int hour=timePicker.getHour();
            int minute=timePicker.getMinute();
            calendar.set(Calendar.HOUR_OF_DAY,hour);
            calendar.set(Calendar.MINUTE,minute);

            int ampm = calendar.get(Calendar.AM_PM);

            if(ampm == Calendar.AM){
                time = "AM";
            }
            else{
                time = "PM";
            }

            savefile("time1.txt",time);
            savefile("time2.txt",String.valueOf(hour));
            savefile("time3.txt",String.valueOf(minute));
            savefile("timeset.txt",String.valueOf(calendar.getTimeInMillis()));

            if (calendar.before(Calendar.getInstance())) {
                calendar.add(Calendar.DATE, 1);
            }

            AlarmManager alarmManager=(AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
            if (alarmManager != null) {
                Intent intent = new Intent(this, receivetime.class);
                PendingIntent alarmIntent = PendingIntent.getBroadcast(this, 1, intent, PendingIntent.FLAG_IMMUTABLE) ;

                alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                        AlarmManager.INTERVAL_DAY, alarmIntent);

                Toast.makeText(setAlarm.this, R.string.comple_alrm_set_coment,Toast.LENGTH_LONG).show();

                Intent intent1 = new Intent(setAlarm.this,setting.class);
                startActivity(intent1);
                overridePendingTransition(R.anim.fadein,R.anim.fadeout);
                finish();
            }
        });

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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(setAlarm.this,setting.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fadein,R.anim.fadeout);
        finish();
    }
}