package com.history.hatda;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.style.ForegroundColorSpan;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;

import org.threeten.bp.DayOfWeek;

/**
 * Highlight Saturdays and Sundays with a background
 */
public class SundayDecorator implements DayViewDecorator {

    private final Drawable highlightDrawable;
    private static final int color = Color.parseColor("#228BC34A");

    public SundayDecorator() {
        highlightDrawable = new ColorDrawable(color);
    }

    @Override public boolean shouldDecorate(final CalendarDay day) {
        final DayOfWeek weekDay = day.getDate().getDayOfWeek();
        return weekDay == DayOfWeek.SUNDAY;
    }

    @Override public void decorate(final DayViewFacade view) {
        view.addSpan(new ForegroundColorSpan(Color.RED));
    }
}