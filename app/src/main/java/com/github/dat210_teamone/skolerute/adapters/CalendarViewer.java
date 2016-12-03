package com.github.dat210_teamone.skolerute.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.DragEvent;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.dat210_teamone.skolerute.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;

public class CalendarViewer extends LinearLayout {

    private static final String LOGTAG = "Calendar View";

    private static final int DAYS_COUNT = 48;

    private static final String DATE_FORMAT = "MMM yyyy";

    private String dateFormat;

    private Calendar currentDate = Calendar.getInstance();

    private EventHandler eventHandler = null;

    private LinearLayout header;
    private ImageView btnPrev;
    private ImageView btnNext;
    private TextView txtDate;
    private GridView grid;
    private HorizontalScrollView scroller;
    private HashSet<Date> events;
    private int viewWidth;
    private MotionEvent baseEvent = null;
    private Date maxDate;


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
        assignScrollHandler();

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
        Object o = findViewById(R.id.scroll_view);
        scroller = (HorizontalScrollView) o;
        grid = (GridView)findViewById(R.id.calendar_grid);
        viewWidth = scroller.getWidth();

    }

    private void assignClickHandlers() {

        btnNext.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                currentDate.add(Calendar.MONTH, 1);
                updateCalendar(events);
            }
        });

        btnPrev.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                currentDate.add(Calendar.MONTH, -1);
                updateCalendar(events);
            }
        });

        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                if (position %  8 != 0) {
                    int mover = position - (position / 8 + 1);
                    eventHandler.onDayPress((Date) adapterView.getItemAtPosition(mover));
                }
            }
        });
    }


    private void assignScrollHandler(){
        scroller.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                //Log.d("GUI",Integer.toString(event.getAction()));

                if (baseEvent == null) {
                    grid.setTranslationX(0);
                    baseEvent = MotionEvent.obtain(event);//event;
                    Log.d("BASEEVENT", "Setting Base event: "  + Float.toString(baseEvent.getX()) + ", " + Float.toString(baseEvent.getY()));
                }
                grid.setTranslationX(event.getX() - baseEvent.getX());
                Log.d("BASEEVENT", "Current: "  + Float.toString(event.getX()) + ", " + Float.toString(event.getY()));
                if (event.getAction() == 1 && baseEvent != null){
                    grid.setTranslationX(0);
                    Animation move = null;
                    int monthShift = 0;
                    if (baseEvent.getX() < event.getX()) {
                        move = AnimationUtils.loadAnimation(getContext(), R.anim.move);
                        monthShift = -1;
                    } else {
                        move = AnimationUtils.loadAnimation(getContext(), R.anim.moverev);
                        monthShift = 1;
                    }
                    grid.startAnimation(move);
                    currentDate.add(Calendar.MONTH, monthShift);
                    updateCalendar(events);
                    Log.d("BASEEVENT", "Unsetting baseEvent");
                    baseEvent = null;
                }

                return false;
            }
       });
    }

    public void updateCalendar(){
        updateCalendar(null);
    }

    public void updateCalendar(HashSet<Date> events) {
        if (maxDate != null && currentDate != null) {
            int curMonth = currentDate.get(Calendar.MONTH);
            int checkMonth = maxDate.getMonth();
            int curYear = currentDate.get(Calendar.YEAR);
            int checkYear = maxDate.getYear() + 1900;

            int curDate = curMonth + curYear * 12;
            int checkDate = checkMonth + checkYear * 12;

            if (curDate > checkDate)
                currentDate.add(Calendar.MONTH, -1);
        }
        this.events=events;
        ArrayList<Date> cells = new ArrayList<>();
        Calendar calendar = (Calendar)currentDate.clone();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int monthBeginningCell = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        if(monthBeginningCell == 0)
            monthBeginningCell += 7;
        calendar.add(Calendar.DAY_OF_MONTH, -monthBeginningCell + 1);

        while (cells.size() < DAYS_COUNT) {
            cells.add(calendar.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        grid.setAdapter(new CalendarAdapter(getContext(), cells, events));

        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        txtDate.setText(sdf.format(currentDate.getTime()));

        int month = currentDate.get(Calendar.MONTH);



    }

    public void setMaxDate(Date maxDate) {
        this.maxDate = maxDate;
    }

    private class CalendarAdapter extends ArrayAdapter<Date> {
        private HashSet<Date> eventDays;
        private LayoutInflater inflater;

        public CalendarAdapter(Context context, ArrayList<Date> days, HashSet<Date> eventDays) {
            super(context, R.layout.control_calendar_day, days);
            this.eventDays = eventDays;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public View getView(int position, View view, ViewGroup parent)
        {
            Date today = new Date();

            if (view == null) {
                view = inflater.inflate(R.layout.control_calendar_day, parent, false);
            }
            view.setBackgroundResource(0);
            String text = "";
            if (position % 8 == 0) {
                Date date = getItem(position);
                Calendar cal = Calendar.getInstance();
                cal.setMinimalDaysInFirstWeek(4);
                cal.set(date.getYear() + 1900, date.getMonth(), date.getDate());

                text = Integer.toString(cal.get(Calendar.WEEK_OF_YEAR));
                ((TextView) view).setTextColor(Color.LTGRAY);
            }
            else {
                int mover = position / 8 + 1;
                Date date = getItem(position - mover);
                int day = date.getDate();
                int month = date.getMonth();
                int year = date.getYear();
                if (position - day < 8 + mover && position - day >= 0) {
                    ((TextView) view).setTextColor(Color.BLACK);
                } else {
                    ((TextView) view).setTextColor(getResources().getColor(R.color.greyed_out));
                }

                if (eventDays != null) {
                    for (Date eventDate : eventDays) {
                        if (eventDate.getDate() == day &&
                                eventDate.getMonth() == month &&
                                eventDate.getYear() == year) {
                            //view.setBackgroundColor(R.color.colorSchoolClosedIcon);
                            view.setBackgroundColor(Color.argb(255, 200, 200, 255));
                            ((TextView) view).setTextColor(Color.RED);
                            break;
                        }
                    }
                }
                ((TextView)view).setTypeface(null, Typeface.NORMAL);


                if (day == today.getDate() && month == today.getMonth() && year == today.getYear()) {

                    ((TextView)view).setTypeface(null, Typeface.BOLD);
                    ((TextView)view).setTextColor(getResources().getColor(R.color.today));
                }
                text = Integer.toString(day);
            }

            ((TextView)view).setText(String.valueOf(text));

            return view;
        }
    }

    public void setEventHandler(EventHandler eventHandler)
    {
        this.eventHandler = eventHandler;
    }

    public interface EventHandler {
        void onDayPress(Date date);
    }
}