package com.history.hatda;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.ObjectKey;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.UUID;

public class readmemo extends AppCompatActivity {

    TextView setday,setmind,setweather,settag,setmemo,Weather,mind,Tag;
    ImageButton del,edit,exit,setting;
    ImageView rehistorypic;
    LinearLayout refirstback;
    String day,time,date,num;
    private InterstitialAd mInterstitialAd;
    ProgressDialog dialog;

    int count = 0;
    int back = 1;
    int fontcolor = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.readmemo);


        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.flags &= ~WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(lp);


        setday = findViewById(R.id.reeditDate); // 날짜
        setmind = findViewById(R.id.remindedit); // 기분
        setweather = findViewById(R.id.reWeatheredit); // 날씨
        settag = findViewById(R.id.retagedit); // 태그
        setmemo = findViewById(R.id.reeditText); // 일기내용
        del = findViewById(R.id.del);
        edit = findViewById(R.id.reedit);
        rehistorypic = findViewById(R.id.rehistorypic);
        refirstback = findViewById(R.id.refirstback);
        exit = findViewById(R.id.reexit);
        setting = findViewById(R.id.Setting);
        Weather = findViewById(R.id.Weather);
        mind = findViewById(R.id.mind);
        Tag = findViewById(R.id.Tag);








        try {
            FileInputStream inputfile = openFileInput("fontstyle.txt");
            DataInputStream dis = new DataInputStream(inputfile);

            int typenumber = dis.read();
            dis.close();


            if(typenumber-'0' == 0){
                Typeface typeface = getResources().getFont(R.font.maruburiregular);
                setday.setTypeface(typeface);
                setmind.setTypeface(typeface);
                mind.setTypeface(typeface);
                setweather.setTypeface(typeface);
                settag.setTypeface(typeface);
                setmemo.setTypeface(typeface);
                Tag.setTypeface(typeface);
                Weather.setTypeface(typeface);




            }else if(typenumber-'0' == 1){
                Typeface typeface = getResources().getFont(R.font.nanumguri);
                setday.setTypeface(typeface);
                setmind.setTypeface(typeface);
                mind.setTypeface(typeface);
                setweather.setTypeface(typeface);
                settag.setTypeface(typeface);
                setmemo.setTypeface(typeface);
                Tag.setTypeface(typeface);
                Weather.setTypeface(typeface);


                setday.setTextSize(20);
                setmind.setTextSize(20);
                mind.setTextSize(20);
                setweather.setTextSize(20);
                settag.setTextSize(20);
                setmemo.setTextSize(20);
                Tag.setTextSize(20);
                Weather.setTextSize(20);
            }else if(typenumber-'0' == 2){
                Typeface typeface = getResources().getFont(R.font.nanumming);
                setday.setTypeface(typeface);
                setmind.setTypeface(typeface);
                mind.setTypeface(typeface);
                setweather.setTypeface(typeface);
                settag.setTypeface(typeface);
                setmemo.setTypeface(typeface);
                Tag.setTypeface(typeface);
                Weather.setTypeface(typeface);


                setday.setTextSize(20);
                setmind.setTextSize(20);
                mind.setTextSize(20);
                setweather.setTextSize(20);
                settag.setTextSize(20);
                setmemo.setTextSize(20);
                Tag.setTextSize(20);
                Weather.setTextSize(20);
            }else if(typenumber-'0' == 3){
                Typeface typeface = getResources().getFont(R.font.nanumgom);
                setday.setTypeface(typeface);
                setmind.setTypeface(typeface);
                mind.setTypeface(typeface);
                setweather.setTypeface(typeface);
                settag.setTypeface(typeface);
                setmemo.setTypeface(typeface);
                Tag.setTypeface(typeface);
                Weather.setTypeface(typeface);


                setday.setTextSize(20);
                setmind.setTextSize(20);
                mind.setTextSize(20);
                setweather.setTextSize(20);
                settag.setTextSize(20);
                setmemo.setTextSize(20);
                Tag.setTextSize(20);
                Weather.setTextSize(20);
            }else if(typenumber-'0' == 4){
                Typeface typeface = getResources().getFont(R.font.naumgori);
                setday.setTypeface(typeface);
                setmind.setTypeface(typeface);
                mind.setTypeface(typeface);
                setweather.setTypeface(typeface);
                settag.setTypeface(typeface);
                setmemo.setTypeface(typeface);
                Tag.setTypeface(typeface);
                Weather.setTypeface(typeface);



                setday.setTextSize(20);
                setmind.setTextSize(20);
                mind.setTextSize(20);
                setweather.setTextSize(20);
                settag.setTextSize(20);
                setmemo.setTextSize(20);
                Tag.setTextSize(20);
                Weather.setTextSize(20);
            }
        } catch (FileNotFoundException e) {

        } catch (IOException e) {

        }

        Intent intent = getIntent();
        day = intent.getStringExtra("day");
        time = intent.getStringExtra("시간");
        date = intent.getStringExtra("날짜");
        num = intent.getStringExtra("카운트");

        try {
            setday.setText(readFile("d"+day+".txt"));
            setmind.setText(readFile("m"+day+".txt"));
            setweather.setText(readFile("w"+day+".txt"));
            settag.setText(readFile("t"+day+".txt"));
            setmemo.setText(readmemo("e"+day+".txt"));
            count = Integer.parseInt(readFile("sort"+day+".txt"));
            back = Integer.parseInt(readFile("back"+day+".txt"));
            fontcolor = Integer.parseInt(readFile("fontcolor"+day+".txt"));
        } catch (Exception e) {

        }


        setmemo.setMovementMethod(new ScrollingMovementMethod());

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(readmemo.this,setting.class);
                startActivity(intent1);
                overridePendingTransition(R.anim.fadein,R.anim.fadeout);
            }
        });

        del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                godel(v,day);

            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goeditDialog(v);
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gohistoryDialog(v);
            }
        });


        if (count == 1 || count == 4) {
            setmemo.setGravity(Gravity.CENTER_HORIZONTAL);
        } else if (count == 2) {
            setmemo.setGravity(Gravity.LEFT);
        } else {
            setmemo.setGravity(Gravity.RIGHT);
        }

        if(back == 1){
            refirstback.setBackgroundResource(R.drawable.back1);
        }
        else if(back == 2){
            refirstback.setBackgroundResource(R.drawable.back2);
        }
        else{
            refirstback.setBackgroundColor(back);
        }

        try {
            setday.setTextColor(fontcolor);
            setmind.setTextColor(fontcolor);
            setweather.setTextColor(fontcolor);
            settag.setTextColor(fontcolor);
            setmemo.setTextColor(fontcolor);
            Weather.setTextColor(fontcolor);
            mind.setTextColor(fontcolor);
            Tag.setTextColor(fontcolor);
            setweather.setHintTextColor(fontcolor);
            settag.setHintTextColor(fontcolor);
            setmind.setHintTextColor(fontcolor);
        } catch (Exception e) {
            e.printStackTrace();
        }


        File file = new File("/data/data/com.history.hatda/files/img"+day+".jpeg");
        if(file.exists() == true){
            Glide.with(this).load("/data/data/com.history.hatda/files/img"+day+".jpeg").signature(new ObjectKey(UUID.randomUUID())).into(rehistorypic);
            rehistorypic.setVisibility(View.VISIBLE);
        }

        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(readmemo.this,"ca-app-pub-9257002707604510/9590767297", adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                // The mInterstitialAd reference will be null until
                // an ad is loaded.
                mInterstitialAd = interstitialAd;

            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                // Handle the error
                mInterstitialAd = null;
            }
        });
    }

    public String readFile(String fileName){

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

    public String readmemo(String fileName){

        try {
            // 파일에서 읽은 데이터를 저장하기 위해서 만든 변수
            StringBuffer data = new StringBuffer();
            FileInputStream fs = openFileInput(fileName);//파일명
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

    public void delfile(String filename){
        try {
            File file = new File(getFilesDir(),filename);
            if(file.exists()){
                file.delete();
            }
        } catch (Exception e) {

        }
    }

    @Override
    public void onBackPressed() {

        dialog = new ProgressDialog(readmemo.this);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        dialog.setCancelable(false);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage(getString(R.string.GO));
        dialog.show();



        if(mInterstitialAd != null){
            Handler handler = new Handler();

            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                        dialog.dismiss();
                        Intent intent = new Intent(readmemo.this,history.class);
                        intent.putExtra("시간",time);
                        intent.putExtra("날짜",date);
                        intent.putExtra("카운트",num);
                        startActivity(intent);
                        overridePendingTransition(R.anim.fadein,R.anim.fadeout);
                        finish();
                        mInterstitialAd.show(readmemo.this);

                }
            },500);
        }
        else{
            dialog.dismiss();
            Intent intent = new Intent(readmemo.this,history.class);
            intent.putExtra("시간",time);
            intent.putExtra("날짜",date);
            intent.putExtra("카운트",num);
            startActivity(intent);
            overridePendingTransition(R.anim.fadein,R.anim.fadeout);
            finish();
        }

    }

    public void gohistoryDialog(View view){


        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(R.string.Goedit).setMessage(R.string.golist);
        AlertDialog.Builder builder1 = builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {



                        Intent intent = new Intent(readmemo.this, history.class);
                        intent.putExtra("시간", time);
                        intent.putExtra("날짜", date);
                        intent.putExtra("카운트", num);
                        startActivity(intent);
                        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                        finish();
                    }
             });

        builder.setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void goeditDialog(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(R.string.Goedit).setMessage(R.string.edithistory);
        AlertDialog.Builder builder1 = builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent1 = new Intent(readmemo.this,edit.class);
                intent1.putExtra("날짜",day);
                intent1.putExtra("배경",back);
                intent1.putExtra("시간",time);
                intent1.putExtra("월일",date);
                intent1.putExtra("카운트",num);
                intent1.putExtra("수정",1);
                intent1.putExtra("폰트색상",fontcolor);
                startActivity(intent1);
                overridePendingTransition(R.anim.fadein,R.anim.fadeout);
                finish();
            }
        });

        builder.setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void godel(View view,String day){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(R.string.set_pas_button2_del).setMessage(R.string.delhistory);
        AlertDialog.Builder builder1 = builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                delfile("img" + day +".jpeg");
                delfile("d"+day+".txt");
                delfile("m"+day+".txt");
                delfile("w"+day+".txt");
                delfile("t"+day+".txt");
                delfile("e"+day+".txt");
                delfile("back"+day+".txt");
                delfile("sort"+day+".txt");
                delfile("fontcolor"+day+".txt");

                Intent intent = new Intent(readmemo.this, history.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                finish();
                Toast.makeText(readmemo.this,getString(R.string.delcomple),Toast.LENGTH_SHORT).show();

            }
        });

        builder.setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}