package com.history.hatda;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

public class SingerAdapter extends BaseAdapter {


    ArrayList<String> items = new ArrayList<>(); // 이미지 경로
    Context mc;

    public SingerAdapter(ArrayList<String> path, Context v) {
        this.items = path;
        this.mc = v;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        SingerItemView view = null;
        if (convertView == null) {
            view = new SingerItemView(mc);
        } else {
            view = (SingerItemView) convertView;
        }
        try {
            view.setImage(items.get(position));
        } catch (Exception e) {

        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mc, bigpicshow.class);
                intent.putExtra("path", items.get(position));
                mc.startActivity(intent);

            }
        });

        return view;

    }

}
