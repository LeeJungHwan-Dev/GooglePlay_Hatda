package com.history.hatda;

import android.content.Context;
import android.util.AttributeSet;

import android.view.LayoutInflater;

import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.ObjectKey;

import java.util.UUID;

public class SingerItemView extends LinearLayout {

    ImageView imageView;

    public SingerItemView(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.singer_item, this, true);

        imageView = findViewById(R.id.imageshowview);

    }

    public SingerItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        init(context);
    }

    public void setImage(String path) {
    try {
        Glide.with(this).load(path).signature(new ObjectKey(UUID.randomUUID().toString())).into(imageView);
    } catch (Exception e) {
    }
    }
    
}
