package com.github.dat210_teamone.skolerute.adapters;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.github.dat210_teamone.skolerute.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;

public class CalendarViewer extends LinearLayout {

    private static final String LOGTAG = "Calendar View";

    private static final int DAYS_COUNT = 42;

    private static final String DATE_FORMAT = "MMM yyyy";

    private String dateFormat;

    private Calendar currentDate = Calendar.getInstance();

    private EventHandler eventHandler = null;

    private LinearLayout header;
    private ImageView btnPrev;
    private ImageView btnNext;
    private TextView txtDate;
    private GridView grid;
    private ScrollView scroller;

    public CalendarViewer(Context context) {
        super(context);
    }

    public CalendarViewer(Context context, AttributeSet attrs) {
        super(context, attrs);
        initControl(context, attrs);
    }

    public CalendarViewer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initControl(context, attrs);
    }


    private void initControl(Context context, AttributeSet attrs) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.control_calendar, this);

        loadDateFormat(attrs);
        assignUiElements();
        assignClickHandlers();
    //    assignScrollHandler();

        updateCalendar();
    }

    private void loadDateFormat(AttributeSet attrs) {
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.CalendarView);

        try {
            dateFormat = ta.getString(R.styleable.CalendarView_dateFormat);
            if (dateFormat == null)
                dateFormat = DATE_FORMAT;
        }
        finally {
            ta.recycle();
        }
    }

    private void assignUiElements() {
        header = (LinearLayout)findViewById(R.id.calendar_header);
        btnPrev = (ImageView)findViewById(R.id.calendar_prev_button);
        btnNext = (ImageView)findViewById(R.id.calendar_next_button);
        txtDate = (TextView)findViewById(R.id.calendar_date_display);
        grid = (GridView)findViewById(R.id.calendar_grid);
        scroller = (ScrollView)findViewById(R.id.scroller);
    }

    private void assignClickHandlers() {

        btnNext.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v)
            {
                currentDate.add(Calendar.MONTH, 1);
                updateCalendar();
            }
        });

        btnPrev.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                currentDate.add(Calendar.MONTH, -1);
                updateCalendar();
            }
        });

        grid.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> view, View cell, int position, long id) {
                // handle long-press
                if (eventHandler == null)
                    return false;

                eventHandler.onDayLongPress((Date)view.getItemAtPosition(position));
                return true;
            }
        });
    }
/*
    private void assignScrollHandler(){
        scroller.setOn(new OnScrollChangeListener() {
           @Override
           public void onScrollChange(View view, int i, int i1, int i2, int i3) {

           }
       });
    }
*/
    public void updateCalendar(){
        updateCalendar(null, null);
    }

    public void updateCalendar(HashSet<Date> events, HashSet<Date> free_days) {
        ArrayList<Date> cells = new ArrayList<>();
        Calendar calendar = (Calendar)currentDate.clone();

        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int monthBeginningCell = calendar.get(Calendar.DAY_OF_WEEK) - 1;

        calendar.add(Calendar.DAY_OF_MONTH, -monthBeginningCell);

        while (cells.size() < DAYS_COUNT) {
            cells.add(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        grid.setAdapter(new CalendarAdapter(getContext(), cells, events, free_days));

        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        txtDate.setText(sdf.format(currentDate.getTime()));

        int month = currentDate.get(Calendar.MONTH);

    }


    private class CalendarAdapter extends ArrayAdapter<Date>
    {
        private HashSet<Date> eventDays;
        private HashSet<Date> free_Days;
        private LayoutInflater inflater;

        public CalendarAdapter(Context context, ArrayList<Date> days, HashSet<Date> eventDays, HashSet<Date> free_days) {
            super(context, R.layout.control_calendar_day, days);
            this.eventDays = eventDays;
            this.free_Days = free_days;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public View getView(int position, View view, ViewGroup parent)
        {
            Date date = getItem(position);
            int day = date.getDate();
            int month = date.getMonth();
            int year = date.getYear();

            Date today = new Date();

            if (view == null) {
                view = inflater.inflate(R.layout.control_calendar_day, parent, false);
            }
            view.setBackgroundResource(0);
            if (eventDays != null) {
                for (Date eventDate : eventDays) {
                    if (eventDate.getDate() == day &&
                            eventDate.getMonth() == month &&
                            eventDate.getYear() == year) {

                        view.setBackgroundResource(R.mipmap.ic_exclamation_point_emoticon);
                        break;
                    }
                }
                for (Date free_days : free_Days) {
                    if (free_days.getDate() == day &&
                            free_days.getMonth() == month &&
                            free_days.getYear() == year) {

                        view.setBackgroundResource(R.mipmap.ic_exclamation_point_emoticon);
                        break;
                    }
                }
            }


            ((TextView)view).setTypeface(null, Typeface.NORMAL);
            ((TextView)view).setTextColor(Color.BLACK);

            if (month != today.getMonth() || year != today.getYear()) {
                ((TextView)view).setTextColor(getResources().getColor(R.color.greyed_out));
            }
            else if (day == today.getDate()) {

                ((TextView)view).setTypeface(null, Typeface.BOLD);
                ((TextView)view).setTextColor(getResources().getColor(R.color.today));
            }

            ((TextView)view).setText(String.valueOf(date.getDate()));

            return view;
        }
    }

    public void setEventHandler(EventHandler eventHandler)
    {
        this.eventHandler = eventHandler;
    }

    public interface EventHandler
    {
        void onDayLongPress(Date date);
    }
}