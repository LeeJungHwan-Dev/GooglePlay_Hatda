package com.history.hatda;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.oss.licenses.OssLicensesMenuActivity;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Calendar;

public class setting extends AppCompatActivity {

    Button openli,fontli,reset,password,al,apply;
    ImageButton exit;
    Dialog openlicense;
    TextView time,title,after;
    Spinner fontsp;
    String min ="";
    SeekBar sizeseek;
    String stylenum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        


        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.flags &= ~WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(lp);

        openli = findViewById(R.id.openli);
        fontli = findViewById(R.id.fontli);
        reset = findViewById(R.id.reset);
        exit = findViewById(R.id.gohistory);
        password = findViewById(R.id.setpas);
        al = findViewById(R.id.setal);
        time = findViewById(R.id.time);
        fontsp = findViewById(R.id.fontitem);
        title = findViewById(R.id.aftertitle);
        after = findViewById(R.id.after);
        sizeseek = findViewById(R.id.sizeseekBar);
        apply = findViewById(R.id.apply);




    try {
        File file = new File("/data/data/com.history.hatda/files/time1.txt");
        File file1 = new File("/data/data/com.history.hatda/files/time2.txt");
        File file2 = new File("/data/data/com.history.hatda/files/time3.txt");

        if(file.exists()&&file1.exists()&&file2.exists()){
            if(Integer.parseInt(readfile("time3.txt"))<10){
                min += 0;
                min += readfile("time3.txt");

            }else{
                min += readfile("time3.txt");
            }
            time.setText(readfile("time1.txt")+" "+readfile("time2.txt")+" : "+min);
        }
        else{}
    } catch (Exception e) {

    }


        String[] item ={"마루부리","나눔손글씨 미니손글씨","나눔손글씨 예쁜 민경체","나눔손글씨 곰신체","나눔손글씨 흰수염고래"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        fontsp.setAdapter(adapter);


        fontsp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0) {
                    Typeface typeface = getResources().getFont(R.font.maruburiregular);
                    after.setTypeface(typeface);
                    stylenum = "0";
                }
                else if (position == 1){
                    Typeface typeface = getResources().getFont(R.font.nanumguri);
                    after.setTypeface(typeface);
                    stylenum = "1";
                }
                else if (position == 2){
                    Typeface typeface = getResources().getFont(R.font.nanumming);
                    after.setTypeface(typeface);
                    stylenum = "2";
                }
                else if (position == 3){
                    Typeface typeface = getResources().getFont(R.font.nanumgom);
                    after.setTypeface(typeface);
                    stylenum = "3";
                }
                else if (position == 4){
                    Typeface typeface = getResources().getFont(R.font.naumgori);
                    after.setTypeface(typeface);
                    stylenum = "4";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        sizeseek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress < 40) {
                    after.setTextSize(progress);

                }
                else{}
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });



        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savefile("fontstyle.txt",stylenum);
                restart();
            }
        });



        al.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                int hour= 22;
                int minute= 00;
                calendar.set(Calendar.HOUR_OF_DAY,hour);
                calendar.set(Calendar.MINUTE,minute);



                if (calendar.before(Calendar.getInstance())) {
                    calendar.add(Calendar.DATE, 1);
                }

                AlarmManager alarmManager=(AlarmManager)setting.this.getSystemService(Context.ALARM_SERVICE);
                if (alarmManager != null) {
                    Intent intent = new Intent(setting.this, receivetime.class);
                    PendingIntent alarmIntent = PendingIntent.getBroadcast(setting.this,1,intent,PendingIntent.FLAG_IMMUTABLE);


                    alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                            AlarmManager.INTERVAL_DAY, alarmIntent);
                }

                Intent intent = new Intent(setting.this,setAlarm.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fadein,R.anim.fadeout);
                finish();
            }
        });



        openlicense = new Dialog(setting.this);
        openlicense.setContentView(R.layout.license);

        openli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(setting.this, OssLicensesMenuActivity.class);
                startActivity(intent);
                OssLicensesMenuActivity.setActivityTitle(getString(R.string.opensorcelititle));
            }
        });

        fontli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showopenli();
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alldel(v);
                time.setText("PM 22 : 00");
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.fadein,R.anim.fadeout);
            }
        });

        password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPassword(v);
            }
        });

    }

    public void showopenli(){
        openlicense.show();


        Button closebutton = openlicense.findViewById(R.id.close);
        TextView license = openlicense.findViewById(R.id.license);
        String licensetxt = readli();
        license.setText(licensetxt);
        closebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openlicense.dismiss();
            }
        });
    }

    public String readli(){

        try {
            // 파일에서 읽은 데이터를 저장하기 위해서 만든 변수
            StringBuffer data = new StringBuffer();
            InputStream fs = getResources().openRawResource(R.raw.fontlicense);
            BufferedReader buffer = new BufferedReader
                    (new InputStreamReader(fs));
            String str = buffer.readLine(); // 파일에서 한줄을 읽어옴
            if(str != null) {
                while (str != null) {
                    data.append(str+"\n");
                    str = buffer.readLine();
                }
                buffer.close();
                return data.toString();
            }
        } catch (Exception e) {

        }
        return null;
    }

    public String readfile(String fileName){

        try {
            // 파일에서 읽은 데이터를 저장하기 위해서 만든 변수
            StringBuffer data = new StringBuffer();
            FileInputStream fs = openFileInput(fileName);//파일명
            BufferedReader buffer = new BufferedReader
                    (new InputStreamReader(fs));
            String str = buffer.readLine(); // 파일에서 한줄을 읽어옴
            if(str != null) {
                while (str != null) {
                    data.append(str);
                    str = buffer.readLine();
                }
                buffer.close();
                return data.toString();
            }
        } catch (Exception e) {

        }
        return null;
    }

    public void alldel(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(R.string.setting_alldel_Warning).setMessage(R.string.waring_coment);
        AlertDialog.Builder builder1 = builder.setPositiveButton(R.string.alldel_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                File file = new File("/data/data/com.history.hatda/files/");
                try {
                    while(file.exists()) {
                        File[] fileslist = file.listFiles();

                        for (int i = 0; i < fileslist.length; i++) {
                            fileslist[i].delete();
                        }
                        if (fileslist.length == 0 && file.isDirectory()) {
                            file.delete(); //대상폴더 삭제
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Toast.makeText(setting.this, R.string.reset_comple,Toast.LENGTH_SHORT).show();
                restart();
            }
        });

        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void setPassword(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(R.string.set_password_title).setMessage(R.string.set_pas_titlecoment);
        AlertDialog.Builder builder1 = builder.setPositiveButton(R.string.set_pas_button1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(setting.this,getsetpassword.class);
                intent.putExtra("설정","1");
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.fadein,R.anim.fadeout);
            }
        });

        builder.setNeutralButton(R.string.set_pas_button2_del, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                File file = new File("/data/data/com.history.hatda/files/password.txt");

                if(file.exists()){
                    file.delete();
                    Toast.makeText(setting.this, R.string.set_pas_del_coment,Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(setting.this, R.string.noget_pas,Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton(R.string.set_pas_cancle, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
        overridePendingTransition(R.anim.fadein,R.anim.fadeout);
    }

    public void restart(){
        Intent intent = getBaseContext().getPackageManager().
                getLaunchIntentForPackage(getBaseContext().getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        ActivityCompat.finishAffinity(this);
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
    protected void onDestroy() {


        openlicense.dismiss();


        super.onDestroy();

    }

}