package com.history.hatda;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.ObjectKey;
import com.github.chrisbanes.photoview.PhotoView;

import java.io.File;
import java.util.UUID;

public class bigpicshow extends AppCompatActivity {

    String path;
    ImageButton delpic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bigpicshow);

        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.flags &= ~WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(lp);

        delpic = findViewById(R.id.delpic);

        Intent intent = getIntent();

        path = intent.getStringExtra("path");

        PhotoView photoView = (PhotoView) findViewById(R.id.photo_view);
        try {
            Glide.with(this).load(path).signature(new ObjectKey(UUID.randomUUID().toString())).into(photoView);
        } catch (Exception e) {

        }
        delpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delpicDialog(v);
            }
        });

    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public void delpicDialog(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle(R.string.set_pas_button2_del).setMessage(R.string.delpicnow);
        AlertDialog.Builder builder1 = builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                File file = new File(path);
                file.delete();
                Intent intent = new Intent(bigpicshow.this,history.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
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
}