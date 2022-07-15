package com.history.hatda;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.ObjectKey;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.UUID;

public class Adapter extends RecyclerView.Adapter<Adapter.Holder> {

    ArrayList<String> date;
    ArrayList<String> imgpath;
    ArrayList<String> memotext;
    ArrayList<String> sort,back,fontcolor,tag,weather,mind;
    static ArrayList<String> goday;
    Context mContext;
    String chosetime, count, chosedate;


    public Adapter(ArrayList<String> date, ArrayList<String> textpath, ArrayList<String> goday, ArrayList<String> imgpath,ArrayList<String> sort,ArrayList<String> back, Context mContext,ArrayList<String>fontcolor, ArrayList<String> weather, ArrayList<String> mind, ArrayList<String> tag) {
        this.date = date; // 날짜
        this.memotext = textpath; //텍스트
        this.goday = goday; // 이동날짜
        this.imgpath = imgpath;
        this.mContext = mContext;
        this.sort = sort;
        this.back = back;
        this.fontcolor = fontcolor;
        this.weather = weather;
        this.mind = mind;
        this.tag = tag;


    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.listview, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {


        viewSet(holder,position);

        setAni(holder,position);

        setFont(holder,mContext);


    }

    @Override
    public int getItemCount() {

        return date.size();

    }

    class Holder extends RecyclerView.ViewHolder {

        TextView time, textmemo, godays,itemweathertext , itemmindtext , itemtagtext,itemweathertitle , itemmindtitle , itemtagtitle;
        ImageView img;
        LinearLayout bodyview , itemweather , itemmind , itemtag;

        public void setimg(int i) throws IOException {
            if (imgpath != null) {
                Path path = Paths.get(imgpath.get(i));
                if (Files.size(path) != 0) {
                    img = itemView.findViewById(R.id.dayimg);
                    Glide.with(itemView).load(imgpath.get(i)).signature(new ObjectKey(UUID.randomUUID().toString())).into(img);
                    img.setVisibility(View.VISIBLE);
                } else {
                    Files.delete(path);
                }
            }
        }


        public Holder(@NonNull View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.dateText);
            textmemo = itemView.findViewById(R.id.textmemos);
            godays = itemView.findViewById(R.id.goday);
            bodyview = itemView.findViewById(R.id.backview);
            itemweather = itemView.findViewById(R.id.itemweather);
            itemmind = itemView.findViewById(R.id.itemmind);
            itemtag = itemView.findViewById(R.id.itemtag);
            itemweathertext = itemView.findViewById(R.id.itemweathertext);
            itemmindtext = itemView.findViewById(R.id.itemmindtext);
            itemtagtext = itemView.findViewById(R.id.itemtagtext);
            itemweathertitle = itemView.findViewById(R.id.itemweathertitle);
            itemmindtitle = itemView.findViewById(R.id.itemmindtitle);
            itemtagtitle = itemView.findViewById(R.id.itemtagtitle);





            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {



                    int pos = getAdapterPosition();
                    Intent intent = new Intent(mContext, readmemo.class);
                    intent.putExtra("day", goday.get(pos));
                    intent.putExtra("시간", chosetime);
                    intent.putExtra("카운트", count);
                    intent.putExtra("날짜", chosedate);
                    mContext.startActivity(intent);
                    ((Activity) mContext).overridePendingTransition(R.anim.fadein, R.anim.fadeout);
                    ((Activity) mContext).finish();
                }
            });
        }
    }


    public void setFont(Holder holder , Context mContexts){


        try {
            FileInputStream inputfile = mContexts.openFileInput("fontstyle.txt");
            DataInputStream dis = new DataInputStream(inputfile);

            int typenumber = dis.read();
            dis.close();


            if(typenumber-'0' == 0){
                Typeface typeface = mContexts.getResources().getFont(R.font.maruburiregular);


                holder.godays.setTypeface(typeface);
                holder.itemweathertext.setTypeface(typeface);
                holder.itemmindtext.setTypeface(typeface);
                holder.itemtagtext.setTypeface(typeface);
                holder.itemweathertitle.setTypeface(typeface);
                holder.itemmindtitle.setTypeface(typeface);
                holder.textmemo.setTypeface(typeface);
                holder.itemtagtitle.setTypeface(typeface);
                holder.godays.setTypeface(typeface);
                holder.time.setTypeface(typeface);


            }else if(typenumber-'0' == 1){
                Typeface typeface = mContexts.getResources().getFont(R.font.nanumguri);


                holder.godays.setTypeface(typeface);
                holder.itemweathertext.setTypeface(typeface);
                holder.itemmindtext.setTypeface(typeface);
                holder.itemtagtext.setTypeface(typeface);
                holder.itemweathertitle.setTypeface(typeface);
                holder.itemmindtitle.setTypeface(typeface);
                holder.textmemo.setTypeface(typeface);
                holder.itemtagtitle.setTypeface(typeface);
                holder.godays.setTypeface(typeface);
                holder.time.setTypeface(typeface);



                holder.godays.setTextSize(20);
                holder.itemweathertext.setTextSize(20);
                holder.itemmindtext.setTextSize(20);
                holder.itemtagtext.setTextSize(20);
                holder.itemweathertitle.setTextSize(20);
                holder.itemmindtitle.setTextSize(20);
                holder.textmemo.setTextSize(20);
                holder.itemtagtitle.setTextSize(20);
                holder.time.setTextSize(20);


            }else if(typenumber-'0' == 2){
                Typeface typeface = mContexts.getResources().getFont(R.font.nanumming);

                holder.godays.setTypeface(typeface);
                holder.itemweathertext.setTypeface(typeface);
                holder.itemmindtext.setTypeface(typeface);
                holder.itemtagtext.setTypeface(typeface);
                holder.itemweathertitle.setTypeface(typeface);
                holder.itemmindtitle.setTypeface(typeface);
                holder.textmemo.setTypeface(typeface);
                holder.itemtagtitle.setTypeface(typeface);
                holder.godays.setTypeface(typeface);
                holder.time.setTypeface(typeface);





                holder.godays.setTextSize(20);
                holder.itemweathertext.setTextSize(20);
                holder.itemmindtext.setTextSize(20);
                holder.itemtagtext.setTextSize(20);
                holder.itemweathertitle.setTextSize(20);
                holder.itemmindtitle.setTextSize(20);
                holder.textmemo.setTextSize(20);
                holder.itemtagtitle.setTextSize(20);
                holder.time.setTextSize(20);
            }else if(typenumber-'0' == 3){
                Typeface typeface = mContexts.getResources().getFont(R.font.nanumgom);

                holder.godays.setTypeface(typeface);
                holder.itemweathertext.setTypeface(typeface);
                holder.itemmindtext.setTypeface(typeface);
                holder.itemtagtext.setTypeface(typeface);
                holder.itemweathertitle.setTypeface(typeface);
                holder.itemmindtitle.setTypeface(typeface);
                holder.textmemo.setTypeface(typeface);
                holder.itemtagtitle.setTypeface(typeface);
                holder.godays.setTypeface(typeface);
                holder.time.setTypeface(typeface);




                holder.godays.setTextSize(20);
                holder.itemweathertext.setTextSize(20);
                holder.itemmindtext.setTextSize(20);
                holder.itemtagtext.setTextSize(20);
                holder.itemweathertitle.setTextSize(20);
                holder.itemmindtitle.setTextSize(20);
                holder.textmemo.setTextSize(20);
                holder.itemtagtitle.setTextSize(20);
                holder.time.setTextSize(20);
            }else if(typenumber-'0' == 4){
                Typeface typeface = mContexts.getResources().getFont(R.font.naumgori);

                holder.godays.setTypeface(typeface);
                holder.itemweathertext.setTypeface(typeface);
                holder.itemmindtext.setTypeface(typeface);
                holder.itemtagtext.setTypeface(typeface);
                holder.itemweathertitle.setTypeface(typeface);
                holder.itemmindtitle.setTypeface(typeface);
                holder.textmemo.setTypeface(typeface);
                holder.itemtagtitle.setTypeface(typeface);
                holder.godays.setTypeface(typeface);
                holder.time.setTypeface(typeface);



                holder.godays.setTextSize(20);
                holder.itemweathertext.setTextSize(20);
                holder.itemmindtext.setTextSize(20);
                holder.itemtagtext.setTextSize(20);
                holder.itemweathertitle.setTextSize(20);
                holder.itemmindtitle.setTextSize(20);
                holder.textmemo.setTextSize(20);
                holder.itemtagtitle.setTextSize(20);
                holder.time.setTextSize(20);
            }
        } catch (FileNotFoundException e) {

        } catch (IOException e) {

        }


    }


    public void viewSet(Holder holder , int positions){
        try {
            if (weather.get(positions).equals("")) {
                holder.itemweather.setVisibility(View.GONE);
            } else {
                holder.itemweather.setVisibility(View.VISIBLE);
                holder.itemweathertext.setText(weather.get(positions));
            }
            if (mind.get(positions).equals("")) {
                holder.itemmind.setVisibility(View.GONE);
            } else {
                holder.itemmind.setVisibility(View.VISIBLE);
                holder.itemmindtext.setText(mind.get(positions));
            }
            if (tag.get(positions).equals("")) {
                holder.itemtag.setVisibility(View.GONE);
            } else {
                holder.itemtag.setVisibility(View.VISIBLE);
                holder.itemtagtext.setText(tag.get(positions));
            }
        } catch (Exception e) {

        }
    }


    public void setAni(Holder holder , int positions){
        try {
            if (positions > 3) {
                holder.bodyview.setAnimation(AnimationUtils.loadAnimation(mContext, android.R.anim.fade_in));
            }

            try {
                holder.time.setText(date.get(positions));
                String memo = memotext.get(positions);
                holder.textmemo.setText(memo);
                holder.godays.setText(goday.get(positions));
                holder.setimg(positions);


            } catch (Exception e) {
            }

            if (sort.get(positions).equals("1") || sort.get(positions).equals("4")) {
                holder.textmemo.setGravity(Gravity.CENTER_HORIZONTAL);
            } else if (sort.get(positions).equals("2")) {
                holder.textmemo.setGravity(Gravity.LEFT);
            } else {
                holder.textmemo.setGravity(Gravity.RIGHT);
            }


            try {
                holder.bodyview.setBackgroundColor(Integer.parseInt(back.get(positions)));
                holder.textmemo.setTextColor(Integer.parseInt(fontcolor.get(positions)));
                holder.time.setTextColor(Integer.parseInt(fontcolor.get(positions)));



                holder.itemweathertitle.setTextColor(Integer.parseInt(fontcolor.get(positions)));
                holder.itemmindtitle.setTextColor(Integer.parseInt(fontcolor.get(positions)));
                holder.itemtagtitle.setTextColor(Integer.parseInt(fontcolor.get(positions)));
                holder.itemweathertext.setTextColor(Integer.parseInt(fontcolor.get(positions)));
                holder.itemmindtext.setTextColor(Integer.parseInt(fontcolor.get(positions)));
                holder.itemtagtext.setTextColor(Integer.parseInt(fontcolor.get(positions)));
            } catch (Exception e) {

            }
        } catch (Resources.NotFoundException e) {

        }
    }

}
