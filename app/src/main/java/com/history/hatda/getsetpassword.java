package com.history.hatda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class getsetpassword extends AppCompatActivity {

    TextView coment;
    EditText password,password2;
    Button check,check2;
    String pass;
    String num;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getsetpassword);

        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.flags &= ~WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(lp);



        coment = findViewById(R.id.coment);
        password = findViewById(R.id.editpassword);
        password2 = findViewById(R.id.editpassword2);
        check = findViewById(R.id.check);
        check2 = findViewById(R.id.check2);

        Intent intent = getIntent();
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        if(intent.getStringExtra("설정")!= null){
            num = intent.getStringExtra("설정");
        }
        else{
            num = intent.getStringExtra("비밀번호");
        }


        try {
            if(num.equals("1")){
                coment.setText(R.string.plase_enter_your_pass);
                password.setVisibility(View.VISIBLE);
                check.setVisibility(View.VISIBLE);
                count++;
            }
            else{
                pass = readpass();
                password2.setVisibility(View.VISIBLE);
                check2.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
        }

        password.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == 66) {
                    password.clearFocus();
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                }
                else{}
                return false;
            }
        });

        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {
                    if (password.getText().length() < 4) {
                        coment.setText(R.string.check_pass);
                        Animation shake1 = AnimationUtils.loadAnimation(getsetpassword.this, R.anim.shake);
                        password.startAnimation(shake1);

                    } else {
                        if (num.equals("1")) {
                            pass = password.getText().toString();
                            count++;
                            coment.setText(R.string.re_set_pass);
                            password.setText("");
                            num = "2";
                        } else {
                            if (count == 1) {
                                if (pass.equals(password.getText().toString())) {
                                    savefile("password.txt", pass);
                                    coment.setText(R.string.comple_pass);
                                    password.setText("");
                                    password.setEnabled(false);
                                    Intent intent1 = new Intent(getsetpassword.this, setting.class);
                                    startActivity(intent1);
                                    overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                                    finish();
                                    Toast.makeText(getsetpassword.this, R.string.comple_pass_2, Toast.LENGTH_SHORT).show();
                                } else {
                                    Animation shake = AnimationUtils.loadAnimation(getsetpassword.this, R.anim.shake);
                                    coment.setText(R.string.no_comple_pass);
                                    password.startAnimation(shake);
                                    password.setText("");
                                    count = 0;
                                }
                            } else {
                                if (pass != null) {
                                    if (pass.equals(password.getText().toString())) {
                                        savefile("password.txt", pass);
                                        coment.setText(R.string.comple_pass);
                                        password.setText("");
                                        password.setEnabled(false);
                                        Intent intent1 = new Intent(getsetpassword.this, setting.class);
                                        startActivity(intent1);
                                        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                                        finish();
                                        Toast.makeText(getsetpassword.this, R.string.comple_pass_2, Toast.LENGTH_SHORT).show();
                                    } else {
                                        Animation shake = AnimationUtils.loadAnimation(getsetpassword.this, R.anim.shake);
                                        coment.setText(R.string.no_comple_pass);
                                        password.startAnimation(shake);
                                        password.setText("");
                                        count = 0;
                                    }
                                } else {
                                    pass = password.getText().toString();
                                    count++;
                                    coment.setText(R.string.re_pass);
                                    password.setText("");
                                }
                            }
                        }
                    }
                }
            }
        });



        password2.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode == 66){
                    if(password2.getText().toString().equals(pass)){
                        coment.setText(R.string.ok_pass);
                        Intent intent2 = new Intent(getsetpassword.this,history.class);
                        startActivity(intent2);
                        overridePendingTransition(R.anim.fadein,R.anim.fadeout);
                        finish();
                    }
                    else{
                        Animation shake = AnimationUtils.loadAnimation(getsetpassword.this,R.anim.shake);
                        coment.setText(R.string.ifnopass_del);
                        password2.startAnimation(shake);
                    }
                }

                return false;
            }
        });

        check2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(password2.getText().toString().equals(pass)){
                    coment.setText(R.string.ok_pass);
                    Intent intent2 = new Intent(getsetpassword.this,history.class);
                    startActivity(intent2);
                    overridePendingTransition(R.anim.fadein,R.anim.fadeout);
                    finish();
                }
                else{
                    Animation shake = AnimationUtils.loadAnimation(getsetpassword.this,R.anim.shake);
                    coment.setText(R.string.ifnopass_del);
                    password2.startAnimation(shake);
                }
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
    public String readpass(){

        try {
            // 파일에서 읽은 데이터를 저장하기 위해서 만든 변수
            StringBuffer data = new StringBuffer();
            FileInputStream fs = openFileInput("password.txt");//파일명
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
}