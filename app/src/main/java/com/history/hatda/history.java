package com.history.hatda;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class history extends AppCompatActivity {

    CoordinatorLayout history;
    RecyclerView item;
    Button leftbt1,rightbt2;
    FloatingActionButton goedit;
    TextView timetext;
    String times;
    int count = 0;
    ArrayList<String> date = new ArrayList<>();
    ArrayList<String> imgpath = new ArrayList<>();
    ArrayList<String> memotext = new ArrayList<>();
    ArrayList<String> goday = new ArrayList<>();
    ArrayList<String> goMonth = new ArrayList<>();
    ArrayList<String> sort = new ArrayList<>();
    ArrayList<String> back = new ArrayList<>();
    ArrayList<String> fontcolor = new ArrayList<>();

    ArrayList<String> itemweather = new ArrayList<>();
    ArrayList<String> itemmind = new ArrayList<>();
    ArrayList<String> itemtag = new ArrayList<>();

    String datem,filem,editfilem;
    String chosetime;
    ImageButton exit,setting,bt3;
    Adapter adapter = new Adapter(date,memotext,goday,imgpath,sort,back,this,fontcolor,itemweather,itemmind,itemtag,goMonth);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);


        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.flags &= ~WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(lp);


        item = findViewById(R.id.rec);
        leftbt1 = findViewById(R.id.back);
        rightbt2 = findViewById(R.id.go);
        goedit = findViewById(R.id.goedit);
        timetext = findViewById(R.id.date);
        exit = findViewById(R.id.mainexit);
        setting = findViewById(R.id.mainSetting);
        history = findViewById(R.id.history);
        leftbt1.setText("<--");
        rightbt2.setText("-->");
        bt3 = findViewById(R.id.album);



    try{
        FileInputStream inputfile = openFileInput("fontstyle.txt");
        DataInputStream dis = new DataInputStream(inputfile);

        int typenumber = dis.read();
        dis.close();


        if(typenumber-'0' == 0){
            Typeface typeface = getResources().getFont(R.font.maruburiregular);
            timetext.setTypeface(typeface);

            timetext.setTextSize(30);


        }else if(typenumber-'0' == 1){
            Typeface typeface = getResources().getFont(R.font.nanumguri);
            timetext.setTypeface(typeface);

            timetext.setTextSize(30);
        }else if(typenumber-'0' == 2){
            Typeface typeface = getResources().getFont(R.font.nanumming);
            timetext.setTypeface(typeface);

            timetext.setTextSize(30);
        }else if(typenumber-'0' == 3){
            Typeface typeface = getResources().getFont(R.font.nanumgom);
            timetext.setTypeface(typeface);

            timetext.setTextSize(30);
        }else if(typenumber-'0' == 4){
            Typeface typeface = getResources().getFont(R.font.naumgori);
            timetext.setTypeface(typeface);

            timetext.setTextSize(30);
        }
    } catch (FileNotFoundException e) {

    } catch (IOException e) {

    }










        bt3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(history.this,imgshow.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }
        });

        item.setItemViewCacheSize(32);
        item.setHasFixedSize(true);
        item.setNestedScrollingEnabled(false);


        SimpleDateFormat timeset = new SimpleDateFormat("yyyy.MM");
        Date timeDateset = new Date();
        timetext.setText(timeset.format(timeDateset));

        SimpleDateFormat time = new SimpleDateFormat("yyyyM");
        Date timeDate = new Date();
        times = time.format(timeDate);

        SimpleDateFormat time2 = new SimpleDateFormat("yyyyMM");
        Date timeDate2 = new Date();
        editfilem = time2.format(timeDate2);

        filem = times;
        // filem에다가 MM 형식으로 넣어줘야한다.


        adapter.chosetime = times;
        adapter.count = String.valueOf(count);
        Intent intent = getIntent();

        try {
            if(intent.getStringExtra("시간").equals(null)){
            }
            else{
                chosetime = intent.getStringExtra("시간");
                adapter.chosetime = chosetime;
                filem = chosetime;
                times = chosetime;
                count = Integer.parseInt(intent.getStringExtra("카운트"));
                timetext.setText(addmm(count));
                adapter.count = String.valueOf(count);
            }
        } catch (Exception e) {

        }


        goedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        Intent intent = new Intent(history.this,edit.class);
                        intent.putExtra("시간",filem);
                        intent.putExtra("카운트",String.valueOf(count));
                        startActivity(intent);
                        overridePendingTransition(R.anim.fadein,R.anim.fadeout);
                        finish();
            }
        });

        leftbt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date.clear();
                goday.clear();
                sort.clear();
                memotext.clear();
                back.clear();
                fontcolor.clear();
                itemmind.clear();
                itemweather.clear();
                itemtag.clear();



                count--;
                datem = addmm(count);
                timetext.setText(datem);
                filem = fileaddmm(count);

                readfile(filem,editfilem);
                imglist imglist = new imglist();
                imglist.start();
                adapter.notifyDataSetChanged();
                item.setAdapter(adapter);
            }
        });

        rightbt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    date.clear();
                    goday.clear();
                    sort.clear();
                    memotext.clear();
                    back.clear();
                    fontcolor.clear();
                    itemmind.clear();
                    itemweather.clear();
                    itemtag.clear();

                    count++;
                    datem = addmm(count);
                    timetext.setText(datem);
                    filem = fileaddmm(count);
                    readfile(filem,editfilem);
                    imglist imglist = new imglist();
                    imglist.start();
                    adapter.notifyDataSetChanged();
                    item.setAdapter(adapter);

            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exitDialog(v);
            }
        });

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(history.this,setting.class);
                startActivity(intent1);
                overridePendingTransition(R.anim.fadein,R.anim.fadeout);

            }
        });

        readfile(times,editfilem);
        imglist imglist = new imglist();
        imglist.start();



        Adapter adapter = new Adapter(date,memotext,goday,imgpath,sort,back,this,fontcolor,itemweather,itemmind,itemtag,goMonth);
        adapter.notifyDataSetChanged();
        item.setLayoutManager(new LinearLayoutManager(this));
        item.setAdapter(adapter);

    }

        public String addmm(int num){

            adapter.count = String.valueOf(num);
            SimpleDateFormat format = new SimpleDateFormat("yyyy.MM");
            Date time = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(time);
            cal.add(Calendar.MONTH,num);

            String date = format.format(cal.getTime());
            adapter.chosedate = date;
            return date;
        }

        public String fileaddmm(int num){
            SimpleDateFormat format = new SimpleDateFormat("yyyyM");
            Date time = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(time);
            cal.add(Calendar.MONTH,num);

            String date = format.format(cal.getTime());
            adapter.chosetime = date;
            return date;
        }

    public void exitDialog(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(R.string.exitdialog_title).setMessage(R.string.exit_coment);
        AlertDialog.Builder builder1 = builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finishAndRemoveTask();
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

    public void savefile(String filename,String date){

        try {
            FileOutputStream fo = openFileOutput(filename,MODE_PRIVATE);
            DataOutputStream dos = new DataOutputStream(fo);
            dos.flush();
            dos.close();
        } catch (FileNotFoundException e) {

        } catch (IOException e) {

        }
    }

    @Override
    public void onBackPressed() {
        exitDialog(history);
    }

    class imglist extends Thread {

        public void run() {
            imgpath.clear();
            for (int i = 1; i <= 31; i++) {

                String path = "/data/data/com.history.hatda/files/img" + filem + i + ".jpeg";
                File file = new File(path);
                if (file.exists() == true) {
                    imgpath.add("/data/data/com.history.hatda/files/img" + filem + i + ".jpeg");
                } else {
                }

            }
        }
    }

    public void readfile(String times1,String times2){

        String times = times1;
        String fileName,editName;

            for(int i = 1; i <= 31; i++) {
                fileName = times + i;
                editName = times2 + i;
                Path path = Paths.get(String.valueOf(getFilesDir()), "e" + fileName + ".txt");
                if (Files.exists(path, LinkOption.NOFOLLOW_LINKS)) {
                    Path path2 = Paths.get(String.valueOf(getFilesDir()), "img" + fileName + ".jpeg");
                    if (Files.exists(path2) != true) {
                        savefile("img" + fileName + ".jpeg", null);
                    }

                }
                try {
                    // 파일에서 읽은 데이터를 저장하기 위해서 만든 변수
                    StringBuffer data = new StringBuffer();
                    FileInputStream fs = openFileInput("d" + fileName + ".txt");//파일명
                    BufferedReader buffer = new BufferedReader
                            (new InputStreamReader(fs));
                    String str = buffer.readLine(); // 파일에서 한줄을 읽어옴
                    if (str != null) {
                        while (str != null) {
                            data.append(str);
                            str = buffer.readLine();
                        }
                        date.add(data.toString());
                        goday.add(fileName);
                        goMonth.add(editName);
                        buffer.close();
                    } else {
                    }
                } catch (Exception e) {

                }

                /////////// 정렬
                try {
                    // 파일에서 읽은 데이터를 저장하기 위해서 만든 변수
                    StringBuffer data = new StringBuffer();
                    FileInputStream fs = openFileInput("sort"+fileName+".txt");//파일명
                    BufferedReader buffer = new BufferedReader
                            (new InputStreamReader(fs));
                    String str = buffer.readLine(); // 파일에서 한줄을 읽어옴
                    if(str != null) {
                        while (str != null) {
                            data.append(str);
                            str = buffer.readLine();
                        }
                        sort.add(data.toString());
                        buffer.close();
                    }
                } catch (Exception e) {

                }




                ///////////메모

                try {
                    // 파일에서 읽은 데이터를 저장하기 위해서 만든 변수
                    StringBuffer data = new StringBuffer();
                    FileInputStream fs = openFileInput("e"+fileName+".txt");//파일명
                    BufferedReader buffer = new BufferedReader
                            (new InputStreamReader(fs));
                    String str = buffer.readLine(); // 파일에서 한줄을 읽어옴
                    if(str != null) {
                        while (str != null) {
                            data.append(str+"\n");
                            str = buffer.readLine();
                        }
                        memotext.add(data.toString());
                        buffer.close();
                    }
                } catch (Exception e) {

                }

                //////////// 뒷배경
                try {
                    // 파일에서 읽은 데이터를 저장하기 위해서 만든 변수
                    StringBuffer data = new StringBuffer();
                    FileInputStream fs = openFileInput("back"+fileName+".txt");//파일명
                    BufferedReader buffer = new BufferedReader
                            (new InputStreamReader(fs));
                    String str = buffer.readLine(); // 파일에서 한줄을 읽어옴
                    if(str != null) {
                        while (str != null) {
                            data.append(str);
                            str = buffer.readLine();
                        }
                        back.add(data.toString());
                        buffer.close();
                    }
                } catch (Exception e) {

                }

                /////////// 폰트컬러
                try {
                    // 파일에서 읽은 데이터를 저장하기 위해서 만든 변수
                    StringBuffer data = new StringBuffer();
                    FileInputStream fs = openFileInput("fontcolor"+fileName+".txt");//파일명
                    BufferedReader buffer = new BufferedReader
                            (new InputStreamReader(fs));
                    String str = buffer.readLine(); // 파일에서 한줄을 읽어옴
                    if(str != null) {
                        while (str != null) {
                            data.append(str);
                            str = buffer.readLine();
                        }
                        fontcolor.add(data.toString());
                        buffer.close();
                    }
                } catch (Exception e) {

                }


                ////// 날씨
                try {

                        StringBuffer data = new StringBuffer();
                        FileInputStream fs = openFileInput("w" + fileName + ".txt");//파일명
                        BufferedReader buffer = new BufferedReader
                                (new InputStreamReader(fs));
                        String str = buffer.readLine(); // 파일에서 한줄을 읽어옴
                        if (str != null) {
                            while (str != null) {
                                data.append(str);
                                str = buffer.readLine();
                            }
                            itemweather.add(data.toString());

                            buffer.close();
                        } else {
                            itemweather.add("");
                        }

                } catch (Exception e) {

                }

                ////// 생각
                try {

                        StringBuffer data = new StringBuffer();
                        FileInputStream fs = openFileInput("m"+fileName+".txt");//파일명
                        BufferedReader buffer = new BufferedReader
                                (new InputStreamReader(fs));
                        String str = buffer.readLine(); // 파일에서 한줄을 읽어옴
                        if(str != null) {
                            while (str != null) {
                                data.append(str);
                                str = buffer.readLine();
                            }

                            itemmind.add(data.toString());

                            buffer.close();
                        }
                        else{
                            itemmind.add("");
                        }
                } catch (Exception e) {

                }


                /////// 태그
                try {
                    // 파일에서 읽은 데이터를 저장하기 위해서 만든 변수
                    StringBuffer data = new StringBuffer();
                    FileInputStream fs = openFileInput("t"+fileName+".txt");//파일명
                    BufferedReader buffer = new BufferedReader
                            (new InputStreamReader(fs));
                    String str = buffer.readLine(); // 파일에서 한줄을 읽어옴
                    if(str != null) {
                        while (str != null) {
                            data.append(str);
                            str = buffer.readLine();
                        }

                            itemtag.add(data.toString());

                        buffer.close();
                    }
                    else{
                        itemtag.add("");
                    }
                } catch (Exception e) {

                }

            }

    }
}