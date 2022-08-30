package com.history.hatda;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.exifinterface.media.ExifInterface;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;


import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.ObjectKey;
import com.github.dhaval2404.colorpicker.MaterialColorPickerDialog;
import com.github.dhaval2404.colorpicker.listener.ColorListener;
import com.github.dhaval2404.colorpicker.model.ColorShape;
import com.github.dhaval2404.colorpicker.model.ColorSwatch;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;


import org.threeten.bp.LocalDate;
import org.threeten.bp.format.DateTimeFormatter;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.nio.file.Files;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class edit extends AppCompatActivity {

    MaterialCalendarView cl;
    SimpleDateFormat infotimeset = new SimpleDateFormat("yyyyM");
    Date timeDate = new Date();
    LinearLayout chose, edit, action, firstback, editbox;
    String pattern = "yyyyMMdd";
    String formatted = "";
    org.threeten.bp.format.DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
    String day, infoday;
    Bitmap imgBitmap = null;
    ImageView historypic;
    ImageButton backgroundch, alldel, imgset, save, sort, exit, setting;
    ArrayList<LocalDate> infotimes = new ArrayList<>();
    String dates, editdate, time, month, num;
    EditText mind, weather, tag, history;
    TextView dateedit, weathertitle, mindtitle, tagtitle;
    BasicFileAttributes attrs = null;
    ProgressDialog dialog, dialog2;
    int count = 0;
    int background = 1;
    int sortcount = 0;
    int pic = 0;
    int setimg = 0;
    int comple = 0;
    int donttalk = 0;
    int editcheck = 0;
    int size;
    int fontcolor = Color.BLACK;
    private InterstitialAd mInterstitialAd;
    int checkmonth,checkmonth2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        // 레이아웃을 다시 부모 크기에 맞춰준다.
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.flags &= ~WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(lp);

        cl = findViewById(R.id.calendarView1);
        chose = findViewById(R.id.chosedate);
        edit = findViewById(R.id.edithistory);
        history = findViewById(R.id.editText); // 일기 입력칸
        dateedit = findViewById(R.id.editDate); // 날짜 입력칸
        mind = findViewById(R.id.mindedit); // 기분 입력칸
        weather = findViewById(R.id.Weatheredit); // 날씨 입력칸
        tag = findViewById(R.id.tagedit); // 태그 입력칸
        backgroundch = findViewById(R.id.backgroundch);
        alldel = findViewById(R.id.clear);
        imgset = findViewById(R.id.Imgadd);
        save = findViewById(R.id.save);
        action = findViewById(R.id.actionbutton);
        historypic = findViewById(R.id.rehistorypic);
        sort = findViewById(R.id.sort);
        firstback = findViewById(R.id.firstback);
        exit = findViewById(R.id.reexit);
        editbox = findViewById(R.id.editbox);
        setting = findViewById(R.id.Setting);
        weathertitle = findViewById(R.id.Weather);
        mindtitle = findViewById(R.id.mind); // 마인드 라벨
        tagtitle = findViewById(R.id.Tag); // 태그 라벨
        cl.addDecorators(new SaturdayDecorator(), new SundayDecorator(), new OneDayDecorator(this));
        String infotime = infotimeset.format(timeDate);



        setFonts();


        readdot readdot = new readdot(infotime);

        Timer timer = new Timer();
        TimerTask TT = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (readdot.getcomple() != 0) {

                            size = infotimes.size();
                            for (int i = 0; i < size; i++) {
                                cl.addDecorator(new EventDecorator(infotimes.get(i)));
                            }
                            timer.cancel();
                        }
                    }
                });

            }
        };



        Timer timer2 = new Timer();
        TimerTask TT2 = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (checkmonth != checkmonth2) {
                            if (readdot.getcomple() != 0) {
                                size = infotimes.size();
                                for (int i = 0; i < size; i++) {
                                    cl.addDecorator(new EventDecorator(infotimes.get(i)));
                                }
                                checkmonth2 = checkmonth;
                            }
                        }
                    }
                });

            }
        };

        roadad roadad = new roadad();
        roadad.setPriority(10);
        roadad.start();

        cl.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {

                infotimes.clear();
                checkmonth = date.getMonth();
                readdot readdot = new readdot(date.getYear() + "" + date.getMonth());
                readdot.setPriority(10);
                readdot.start();


            }

        });

        cl.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                String day = String.valueOf(date.getYear() + "" + date.getMonth() + "" + date.getDay());
                dates = day; // 저장용 이름을 date에 저장한다.
                infoday = date.getYear() + "-" + date.getMonth() + "-" + date.getDay();

                File file = new File(getFilesDir(), "d" + dates + ".txt");
                if (file.exists()) {
                    Toast.makeText(edit.this, R.string.saveitfile, Toast.LENGTH_SHORT).show();
                } else {
                    timer2.cancel();
                    editdate = getString(R.string.Date_eng) + date.getYear() + " / " + date.getMonth() + " / " + date.getDay(); // 날짜 : 에 들어갈 날짜를 저장한다.
                    dateedit.setText(editdate); // 저장한 날짜를 날짜:에 입력한다.
                    chose.setVisibility(View.GONE); // 날짜 선택창을 감춘다.
                    edit.setVisibility(View.VISIBLE); // 작성창을 보여준다.
                    action.setVisibility(View.VISIBLE); // 액션버튼을 보여준다.
                }
            }
        });


        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exitDialog(v);
            }
        });


        editbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                history.requestFocus();
                imm.showSoftInput(history, 0);

                if (history.getText().toString().equals("")) {
                    sortcount = 0;
                } else {

                }
                if (sortcount == 0) {
                    sortcount++;
                    history.setHint("");

                    if (count == 0) {
                        count = 1;
                    }
                    if (count == 1 || count == 4) {
                        history.setGravity(Gravity.CENTER_HORIZONTAL);
                    } else if (count == 2) {
                        history.setGravity(Gravity.LEFT);
                    } else {
                        history.setGravity(Gravity.RIGHT);
                    }
                }

            }
        });
        //첫 내용 입력칸 클릭시 왼쪽 상단 정렬
        history.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (history.getText().toString().equals("")) {
                    sortcount = 0;
                } else {

                }
                if (sortcount == 0) {
                    sortcount++;
                    history.setHint("");

                    if (count == 0) {
                        count = 1;
                    }
                    if (count == 1 || count == 4) {
                        history.setGravity(Gravity.CENTER_HORIZONTAL);
                    } else if (count == 2) {
                        history.setGravity(Gravity.LEFT);
                    } else {
                        history.setGravity(Gravity.RIGHT);
                    }
                }
                return false;
            }
        });


        // 전체 삭제 버튼 클릭시 안내 멘트와 함께 내용 초기화
        alldel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlldelclickHandler(v);
            }
        });


        // 세이브 클릭시 칸이 전부 작성 됐는지 확인하고 안되어있으면 안내멘트 출력 되어있으면 저장.
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog2 = new ProgressDialog(edit.this);
                dialog2.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                dialog2.setCancelable(false);
                dialog2.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                dialog2.setMessage(getString(R.string.file_save_read));
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                save.setEnabled(false);
                mind.setEnabled(false);
                weather.setEnabled(false);
                tag.setEnabled(false);
                history.setEnabled(false);
                historypic.setEnabled(false);
                dialog2.show();



                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog2.dismiss();


                        dialog = new ProgressDialog(edit.this);
                        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        dialog.setCancelable(false);
                        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        dialog.setMessage(getString(R.string.saveing));
                        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                        save.setEnabled(false);
                        mind.setEnabled(false);
                        weather.setEnabled(false);
                        tag.setEnabled(false);
                        history.setEnabled(false);
                        historypic.setEnabled(false);

                        if (setimg == 1) {
                            if (comple == 1) {
                                if (history.getText().toString().equals("")) {
                                    nosaveDialog(v);
                                    donttalk = 1;
                                } else {
                                    dialog.show();
                                    savefile("d" + dates + ".txt", editdate);
                                    savefile("m" + dates + ".txt", mind.getText().toString());
                                    savefile("w" + dates + ".txt", weather.getText().toString());
                                    savefile("t" + dates + ".txt", tag.getText().toString());
                                    savefile("e" + dates + ".txt", history.getText().toString());
                                    savefile("back" + dates + ".txt", Integer.toString(background));
                                    savefile("fontcolor" + dates + ".txt", Integer.toString(fontcolor));

                                    if (count == 0) {
                                        count = 1;
                                        savefile("sort" + dates + ".txt", Integer.toString(count));
                                    } else {
                                        savefile("sort" + dates + ".txt", Integer.toString(count));
                                    }
                                    if (imgBitmap != null) {
                                        saveimg();
                                    } else {
                                    }

                                }
                            }
                        } else {
                            if (history.getText().toString().equals("")) {
                                nosaveDialog(v);
                                donttalk = 1;
                            } else {
                                dialog.show();
                                savefile("d" + dates + ".txt", editdate);
                                savefile("m" + dates + ".txt", mind.getText().toString());
                                savefile("w" + dates + ".txt", weather.getText().toString());
                                savefile("t" + dates + ".txt", tag.getText().toString());
                                savefile("e" + dates + ".txt", history.getText().toString());
                                savefile("back" + dates + ".txt", Integer.toString(background));
                                savefile("fontcolor" + dates + ".txt", Integer.toString(fontcolor));

                                if (count == 0) {
                                    count = 1;
                                    savefile("sort" + dates + ".txt", Integer.toString(count));
                                } else {
                                    savefile("sort" + dates + ".txt", Integer.toString(count));
                                }
                                if (imgBitmap != null) {
                                    saveimg();
                                } else {
                                }
                            }
                        }
                    }
                }, 1500);

                Handler handler1 = new Handler();
                handler1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialog.dismiss();
                        history.clearFocus();
                        mind.clearFocus();
                        weather.clearFocus();
                        tag.clearFocus();
                        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(edit.getWindowToken(), 0);

                        if (donttalk != 1) {
                            if (mInterstitialAd != null) {
                                Intent intent1 = new Intent(edit.this, history.class);
                                startActivity(intent1);
                                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                                finish();
                                mInterstitialAd.show(edit.this);
                            } else {
                                Intent intent1 = new Intent(edit.this, history.class);
                                startActivity(intent1);
                                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                                finish();
                            }
                            Toast.makeText(edit.this, R.string.save_comple, Toast.LENGTH_SHORT).show();

                        } else {
                            donttalk = 0;
                        }
                    }
                }, 3000);

            }

        });


        // 이미지 불러오기 버튼 클릭시 인텐트를 활용한 갤러리 호출
        imgset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setimg(v);
            }
        });


        // 글자 정렬을 바꿔준다.
        sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                count++;
                if (count == 1 || count == 4) {
                    history.setGravity(Gravity.CENTER_HORIZONTAL);
                    sort.setImageResource(R.drawable.ic_baseline_line_weight_24);
                    count = 1;
                } else if (count == 2) {
                    history.setGravity(Gravity.LEFT);
                    sort.setScaleX(1f);
                    sort.setImageResource(R.drawable.ic_baseline_sort_24);
                } else {
                    history.setGravity(Gravity.RIGHT);
                    sort.setScaleX(-1f);
                    sort.setImageResource(R.drawable.ic_baseline_sort_24);
                }
            }
        });


        backgroundch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorpicDialog(v);
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(edit.getWindowToken(), 0);
            }
        });


        historypic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delpicDialog(v);
            }
        });

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(edit.this, setting.class);
                startActivity(intent1);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
            }
        });


        try {

            Intent intent = getIntent();
            day = intent.getStringExtra("날짜");
            infoday = intent.getStringExtra("수정월");
            StringBuffer sb = new StringBuffer();
            sb.append(infoday);
            if (infoday.length() == 6) {
                sb.insert(4, "-");
                sb.insert(6, "-");
            } else {
                sb.insert(4, "-"); //4
                sb.insert(7, "-");
            }
            infoday = sb.toString();

            background = intent.getIntExtra("배경", 1);
            editcheck = intent.getIntExtra("수정", 0);
            fontcolor = intent.getIntExtra("폰트색상", Color.BLACK);

            try {
                if (intent.getStringExtra("시간").equals(null)) {
                } else {
                    time = intent.getStringExtra("시간");
                    num = intent.getStringExtra("카운트");
                }

            } catch (Exception e) {
            }

        } catch (Exception e) {

        }

        if (day != null) {
            dates = day;

            File file = new File("/data/data/com.history.hatda/files/img" + day + ".jpeg");
            if (file.exists() == true) {
                Glide.with(edit.this).load("/data/data/com.history.hatda/files/img" + day + ".jpeg").signature(new ObjectKey(UUID.randomUUID())).into(historypic);
                historypic.setVisibility(View.VISIBLE);
                pic = 1;
            } else {
                pic = 0;
            }

            enter enter = new enter();
            enter.start();
        } else {
        }

        readdot.setPriority(10);
        readdot.start();

        timer.schedule(TT, 0, 1000);
        timer2.schedule(TT2, 0, 1500);


    }


    public void saveimg() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.id.rehistorypic, options);

        File tempfile = new File(getFilesDir(), "img" + dates + ".jpeg");
        try {
            tempfile.createNewFile();
            FileOutputStream fileout = new FileOutputStream(tempfile);
            imgBitmap.compress(Bitmap.CompressFormat.JPEG, 60, fileout);
            fileout.close();
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
    }

    // 갤러리 인텐트에서 받아온 값을 넘겨 받아 비트맵 형식으로 변환하고 이미지뷰에 셋팅한다.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) { // 갤러리
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                Uri fileUri = data.getData();
                ContentResolver resolver = getContentResolver();
                try {
                    InputStream instream = resolver.openInputStream(fileUri); // 파일 읽어 오기
                    Bitmap imgBitmap = BitmapFactory.decodeStream(instream); // 받은 정보를 비트맵 으로 변환
                    rotate rotate = new rotate(fileUri, imgBitmap); // 받은 정보를 바탕으로 이미지 방향정보 추가를 위한 쓰레드 클래스
                    rotate.setPriority(Thread.MAX_PRIORITY); // 쓰레드 우선도 최상위로 설정 이 코드는 수정및 최적화가 가능하면 해볼 것
                    Glide.with(this).load(fileUri).signature(new ObjectKey(UUID.randomUUID().toString())).into(historypic);    // 선택한 이미지 이미지뷰에 셋 //glide 라이브러리 설치 필요 찾아볼 것 // 이미지를 세팅해줌
                    instream.close();   // 스트림 닫아주기
                    historypic.setVisibility(View.VISIBLE); //이미지 컴포넌트 보이게 설정
                    setimg = 1; // 이건 필요 없을거 같으니 옮겨서 지울 것
                    rotate.start(); // 쓰레드 시작
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), R.string.failroadfile, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    class rotate extends Thread {

        Uri uri;
        Bitmap bitmap;

        public rotate(Uri uri, Bitmap bitmap) {
            this.uri = uri;
            this.bitmap = bitmap;
        }

        @Override
        public void run() {
            try {
                rotateImage(uri, bitmap);
            } catch (IOException e) {

            }
        }
    }

    private void rotateImage(Uri uri, Bitmap bitmap) throws IOException { // 사진 방향이 돌아가기 때문에 사진에 방향 정보를 다시 넣어 줘야함
        InputStream in = getContentResolver().openInputStream(uri);
        ExifInterface exif = new ExifInterface(in);
        in.close();

        int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
        Matrix matrix = new Matrix();

        if (orientation == ExifInterface.ORIENTATION_ROTATE_90) {
            matrix.postRotate(90);
        } else if (orientation == ExifInterface.ORIENTATION_ROTATE_180) {
            matrix.postRotate(180);
        } else if (orientation == ExifInterface.ORIENTATION_ROTATE_270) {
            matrix.postRotate(270);
        }

        this.imgBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        comple = 1;
    }


    private long backKeyPressedTime = 0;

    //뒤로 가기 키를 누르면 입력을 종료 시킨다.
    @Override
    public void onBackPressed() {

        if (System.currentTimeMillis() > backKeyPressedTime + 500) {
            backKeyPressedTime = System.currentTimeMillis();
            history.clearFocus();
            mind.clearFocus();
            tag.clearFocus();
            weather.clearFocus();
            if (history.getText().toString().equals("")) {
                history.setGravity(Gravity.CENTER);
                history.setHint(R.string.back_hint);
            }
            return;
        }

        if (System.currentTimeMillis() <= backKeyPressedTime + 500) {

            gohistoryDialog(firstback);
        }
    }


    // 전체 삭제 버튼을 눌렀을 때 나오는 안내 멘트
    public void AlldelclickHandler(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(R.string.set_pas_button2_del).setMessage(R.string.alldelbuttoncoment);
        AlertDialog.Builder builder1 = builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            File file = new File("/data/data/com.history.hatda/files/img" + day + ".jpeg");

            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (editcheck != 1) {
                    if (file.exists()) {
                        file.delete();
                    }
                } else {
                }
                mind.setText("");
                weather.setText("");
                tag.setText("");
                history.setText("");
                history.setHint(R.string.back_hint);
                historypic.setImageBitmap(null);
                historypic.setVisibility(View.GONE);
                history.clearFocus();
                mind.clearFocus();
                weather.clearFocus();
                tag.clearFocus();
                weather.setHintTextColor(fontcolor);
                mind.setHintTextColor(fontcolor);
                tag.setHintTextColor(fontcolor);
                history.setHintTextColor(fontcolor);

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


    //빈 칸이 있을 경우 나오는 안내멘트
    public void nosaveDialog(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(R.string.setting_alldel_Warning).setMessage(R.string.back_hint);
        AlertDialog.Builder builder1 = builder.setPositiveButton(R.string.getsetpass_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mind.setEnabled(true);
                weather.setEnabled(true);
                tag.setEnabled(true);
                history.setEnabled(true);
                historypic.setEnabled(true);
                save.setEnabled(true);
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    public void gohistoryDialog(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(R.string.exitdialog_title).setMessage(R.string.golist);
        AlertDialog.Builder builder1 = builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(edit.this, history.class);
                intent.putExtra("시간", time);
                intent.putExtra("날짜", month);
                intent.putExtra("카운트", num);
                startActivity(intent);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                finish();
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

    public void delpicDialog(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(R.string.set_pas_button2_del).setMessage(R.string.delpic);
        AlertDialog.Builder builder1 = builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                File file = new File("/data/data/com.history.hatda/files/img" + day + ".jpeg");
                if (file.exists()) {
                    file.delete();
                    historypic.setVisibility(View.GONE);
                } else {
                    historypic.setVisibility(View.GONE);
                }
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

    public void colorpicDialog(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(R.string.Color).setMessage(R.string.Color_coment);
        AlertDialog.Builder builder1 = builder.setPositiveButton(R.string.background, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String colors[] = {"#F0E5CF", "#F7F6F2", "#C8C6C6", "#4B6587", "#22577A", "#38A3A5", "#57CC99", "#80ED99", "#00A19D", "#FFF8E5", "#FFB344", "#E05D5D", "#E2C2B9", "#BFD8B8", "#E7EAB5", "#F1F7E7", "#F9F3DF", "#FDFCE5"
                        , "#D7E9F7", "#F4D19B", "#6F69AC", "#95DAC1", "#FFEBA1", "#FD6F96", "#FFADAD", "#FFDAC7", "#FFEFBC", "#FFFEB7", "#316B83", "#6D8299", "#8CA1A5", "#D5BFBF", "#FBF4E9", "#B1E693", "#6ECB63", "#EC9CD3", "#000000", "#3B185F", "#394867", "#9BA4B4"
                        , "#D9E4DD", "#CDC9C3", "#555555", "#222831", "#00303F"
                };
                new MaterialColorPickerDialog
                        .Builder(edit.this)
                        .setTitle(R.string.set_background)
                        .setColorShape(ColorShape.SQAURE)
                        .setColorSwatch(ColorSwatch._300)
                        .setColors(colors)
                        .setColorListener(new ColorListener() {
                            @Override
                            public void onColorSelected(int i, @NonNull String s) {
                                firstback.setBackgroundColor(i);
                                background = i;
                            }
                        })
                        .show();
            }
        });

        builder.setNeutralButton(R.string.리셋버튼, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mind.setTextColor(Color.BLACK);
                weather.setTextColor(Color.BLACK);
                tag.setTextColor(Color.BLACK);
                history.setTextColor(Color.BLACK);
                dateedit.setTextColor(Color.BLACK);
                weathertitle.setTextColor(Color.BLACK);
                mindtitle.setTextColor(Color.BLACK);
                tagtitle.setTextColor(Color.BLACK);
                tag.setHintTextColor(Color.BLACK);
                mind.setHintTextColor(Color.BLACK);
                weather.setHintTextColor(Color.BLACK);
                history.setHintTextColor(Color.BLACK);
                firstback.setBackgroundResource(R.drawable.back1);
                fontcolor = Color.BLACK;
                background = 1;

            }
        });

        builder.setNegativeButton(R.string.Font_color, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String colors[] = {"#F0E5CF", "#F7F6F2", "#C8C6C6", "#4B6587", "#22577A", "#38A3A5", "#57CC99", "#80ED99", "#00A19D", "#FFF8E5", "#FFB344", "#E05D5D", "#E2C2B9", "#BFD8B8", "#E7EAB5", "#F1F7E7", "#F9F3DF", "#FDFCE5"
                        , "#D7E9F7", "#F4D19B", "#6F69AC", "#95DAC1", "#FFEBA1", "#FD6F96", "#FFADAD", "#FFDAC7", "#FFEFBC", "#FFFEB7", "#316B83", "#6D8299", "#8CA1A5", "#D5BFBF", "#FBF4E9", "#B1E693", "#6ECB63", "#EC9CD3", "#000000", "#3B185F", "#394867", "#9BA4B4"
                        , "#D9E4DD", "#CDC9C3", "#555555", "#222831", "#00303F"};
                new MaterialColorPickerDialog
                        .Builder(edit.this)
                        .setTitle(R.string.setFontColor)
                        .setColorShape(ColorShape.SQAURE)
                        .setColorSwatch(ColorSwatch._300)
                        .setColors(colors)
                        .setColorListener(new ColorListener() {
                            @Override
                            public void onColorSelected(int i, @NonNull String s) {
                                mind.setTextColor(i);
                                weather.setTextColor(i);
                                tag.setTextColor(i);
                                history.setTextColor(i);
                                dateedit.setTextColor(i);
                                weathertitle.setTextColor(i);
                                mindtitle.setTextColor(i);
                                tagtitle.setTextColor(i);
                                tag.setHintTextColor(i);
                                mind.setHintTextColor(i);
                                weather.setHintTextColor(i);
                                fontcolor = i;
                            }
                        })
                        .show();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    public void exitDialog(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(R.string.exitdialog_title).setMessage(R.string.golist);
        AlertDialog.Builder builder1 = builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(edit.this, history.class);

                intent.putExtra("시간", time);
                intent.putExtra("카운트", num);

                startActivity(intent);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                finish();
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

    // 내부 저장소에 txt 파일 저장
    public void savefile(String filename, String date) {

        try {
            FileOutputStream fo = openFileOutput(filename, MODE_PRIVATE);
            DataOutputStream dos = new DataOutputStream(fo);
            dos.write(date.getBytes());
            dos.flush();
            dos.close();


            File file123 = new File(getFilesDir(), "d" + dates + ".txt");

            try {
                String pattern = "yyyy-MM-dd";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                Date timeToSet = simpleDateFormat.parse(infoday);
                FileTime time = FileTime.fromMillis(timeToSet.getTime());

                Files.setAttribute(file123.toPath(), "lastModifiedTime", time);
                Files.setAttribute(file123.toPath(), "lastAccessTime", time);
                Files.setAttribute(file123.toPath(), "creationTime", time);
            } catch (IOException e) {

            } catch (ParseException e) {

            }

        } catch (IOException e) {

        }
    }

    public String readFile(String fileName) {

        try {
            // 파일에서 읽은 데이터를 저장하기 위해서 만든 변수
            StringBuffer data = new StringBuffer();
            FileInputStream fs = openFileInput(fileName);//파일명
            BufferedReader buffer = new BufferedReader
                    (new InputStreamReader(fs));
            String str = buffer.readLine(); // 파일에서 한줄을 읽어옴
            if (str != null) {
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

    public String readmemo(String fileName) {

        try {
            // 파일에서 읽은 데이터를 저장하기 위해서 만든 변수
            StringBuffer data = new StringBuffer();
            FileInputStream fs = openFileInput(fileName);//파일명
            BufferedReader buffer = new BufferedReader
                    (new InputStreamReader(fs));
            String str = buffer.readLine(); // 파일에서 한줄을 읽어옴
            if (str != null) {
                while (str != null) {
                    data.append(str + "\n");
                    str = buffer.readLine();
                }
                buffer.close();
                return data.toString();
            }
        } catch (Exception e) {

        }
        return null;
    }

    public void setimg(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(R.string.img).setMessage(R.string.getimgcoment);
        AlertDialog.Builder builder1 = builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 0);
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

    class enter extends Thread {


        public void run() {

            try {
                if (background == 1) {
                    firstback.setBackgroundResource(R.drawable.back1);
                } else if (background == 2) {
                    firstback.setBackgroundResource(R.drawable.back2);
                } else {
                    firstback.setBackgroundColor(background);
                }


                mind.setTextColor(fontcolor);
                weather.setTextColor(fontcolor);
                tag.setTextColor(fontcolor);
                history.setTextColor(fontcolor);
                dateedit.setTextColor(fontcolor);
                weathertitle.setTextColor(fontcolor);
                mindtitle.setTextColor(fontcolor);
                tagtitle.setTextColor(fontcolor);
                weather.setHintTextColor(fontcolor);
                mind.setHintTextColor(fontcolor);
                tag.setHintTextColor(fontcolor);
                history.setHintTextColor(fontcolor);

                dateedit.setText(readFile("d" + day + ".txt"));
                editdate = dateedit.getText().toString();
                mind.setText(readFile("m" + day + ".txt"));
                weather.setText(readFile("w" + day + ".txt"));
                tag.setText(readFile("t" + day + ".txt"));
                history.setText(readmemo("e" + day + ".txt"));
                count = Integer.parseInt(readFile("sort" + day + ".txt"));
                background = Integer.parseInt(readFile("back" + day + ".txt"));
                chose.setVisibility(View.GONE); // 날짜 선택창을 감춘다.

                if (count == 1 || count == 4) {
                    history.setGravity(Gravity.CENTER_HORIZONTAL);
                } else if (count == 2) {
                    history.setGravity(Gravity.LEFT);
                } else {
                    history.setGravity(Gravity.RIGHT);
                }
                edit.setVisibility(View.VISIBLE); // 작성창을 보여준다.
                action.setVisibility(View.VISIBLE); // 액션버튼을 보여준다.

            } catch (NumberFormatException e) {

            }
        }

    }

    class readdot extends Thread {

        String path1;
        int comple = 0;

        public readdot(String path) {
            this.path1 = path;
        }

        public int getcomple() {

            return comple;

        }

        @Override
        public void run() {
            for (int i = 1; i <= 31; i++) {

                Path path = Paths.get(String.valueOf(getFilesDir()), "d" + path1 + "" + i + ".txt");

                if (Files.exists(path, LinkOption.NOFOLLOW_LINKS)) {
                    try {
                        attrs = Files.readAttributes(path, BasicFileAttributes.class);
                        FileTime time = attrs.creationTime();
                        formatted = simpleDateFormat.format(new Date(time.toMillis()));
                        LocalDate date = LocalDate.parse(formatted, formatter);
                        infotimes.add(date);


                    } catch (IOException e) {

                    }
                } else {
                }
            }
            comple++;
        }
    }


    class roadad extends Thread{
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    AdRequest adRequest = new AdRequest.Builder().build();
                    InterstitialAd.load(edit.this, "ca-app-pub-9257002707604510/9590767297", adRequest, new InterstitialAdLoadCallback() {
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
            });

        }
    }


    public void setFonts(){
        try {
            FileInputStream inputfile = openFileInput("fontstyle.txt");
            DataInputStream dis = new DataInputStream(inputfile);

            int typenumber = dis.read();
            dis.close();


            if(typenumber-'0' == 0){
                Typeface typeface = getResources().getFont(R.font.maruburiregular);
                history.setTypeface(typeface);
                dateedit.setTypeface(typeface);
                mind.setTypeface(typeface);
                weather.setTypeface(typeface);
                tag.setTypeface(typeface);
                mindtitle.setTypeface(typeface);
                tagtitle.setTypeface(typeface);
                weathertitle.setTypeface(typeface);


            }else if(typenumber-'0' == 1){
                Typeface typeface = getResources().getFont(R.font.nanumguri);
                history.setTypeface(typeface);
                dateedit.setTypeface(typeface);
                mind.setTypeface(typeface);
                weather.setTypeface(typeface);
                tag.setTypeface(typeface);
                mindtitle.setTypeface(typeface);
                tagtitle.setTypeface(typeface);
                weathertitle.setTypeface(typeface);


                history.setTextSize(20);
                dateedit.setTextSize(20);
                mind.setTextSize(20);
                weather.setTextSize(20);
                tag.setTextSize(20);
                mindtitle.setTextSize(20);
                tagtitle.setTextSize(20);
                weathertitle.setTextSize(20);
            }else if(typenumber-'0' == 2){
                Typeface typeface = getResources().getFont(R.font.nanumming);
                history.setTypeface(typeface);
                dateedit.setTypeface(typeface);
                mind.setTypeface(typeface);
                weather.setTypeface(typeface);
                tag.setTypeface(typeface);
                mindtitle.setTypeface(typeface);
                tagtitle.setTypeface(typeface);
                weathertitle.setTypeface(typeface);


                history.setTextSize(20);
                dateedit.setTextSize(20);
                mind.setTextSize(20);
                weather.setTextSize(20);
                tag.setTextSize(20);
                mindtitle.setTextSize(20);
                tagtitle.setTextSize(20);
                weathertitle.setTextSize(20);
            }else if(typenumber-'0' == 3){
                Typeface typeface = getResources().getFont(R.font.nanumgom);
                history.setTypeface(typeface);
                dateedit.setTypeface(typeface);
                mind.setTypeface(typeface);
                weather.setTypeface(typeface);
                tag.setTypeface(typeface);
                mindtitle.setTypeface(typeface);
                tagtitle.setTypeface(typeface);
                weathertitle.setTypeface(typeface);


                history.setTextSize(20);
                dateedit.setTextSize(20);
                mind.setTextSize(20);
                weather.setTextSize(20);
                tag.setTextSize(20);
                mindtitle.setTextSize(20);
                tagtitle.setTextSize(20);
                weathertitle.setTextSize(20);
            }else if(typenumber-'0' == 4){
                Typeface typeface = getResources().getFont(R.font.naumgori);
                history.setTypeface(typeface);
                dateedit.setTypeface(typeface);
                mind.setTypeface(typeface);
                weather.setTypeface(typeface);
                tag.setTypeface(typeface);
                mindtitle.setTypeface(typeface);
                tagtitle.setTypeface(typeface);
                weathertitle.setTypeface(typeface);



                history.setTextSize(20);
                dateedit.setTextSize(20);
                mind.setTextSize(20);
                weather.setTextSize(20);
                tag.setTextSize(20);
                mindtitle.setTextSize(20);
                tagtitle.setTextSize(20);
                weathertitle.setTextSize(20);
            }
        } catch (FileNotFoundException e) {

        } catch (IOException e) {

        }
    }

}
