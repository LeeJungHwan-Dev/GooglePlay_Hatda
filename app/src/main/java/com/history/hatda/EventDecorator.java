package com.history.hatda;



import android.graphics.Color;
import android.util.Log;


import org.threeten.bp.LocalDate;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

public class EventDecorator implements DayViewDecorator {

    LocalDate date;

    public EventDecorator(LocalDate date) {
        this.date = date;

    }


    @Override
    public boolean shouldDecorate(CalendarDay day) {

         return date.toString().equals(day.getDate().toString());

    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new DotSpan(6, Color.rgb(0,216,255)));
    }
}