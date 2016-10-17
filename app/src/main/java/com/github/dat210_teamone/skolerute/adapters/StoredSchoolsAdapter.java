package com.github.dat210_teamone.skolerute.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.dat210_teamone.skolerute.Activities.MainActivity;
import com.github.dat210_teamone.skolerute.Fragments.StoredSchools;
import com.github.dat210_teamone.skolerute.R;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Alex on 289//16.
 */

public class StoredSchoolsAdapter extends ArrayAdapter<String> {

    private final Context context;
    private final String[] values;
    private final Date[] dates;

    public StoredSchoolsAdapter(Context context, String[] values, Date[] dates) {
        super(context, -1, values);
        this.context = context;
        this.values = values;
        this.dates = dates;
    }

    public String dateFormatter(Date date){

        int month = Integer.parseInt(new SimpleDateFormat("MM").format(date));
        String dayOfWeekShort = new SimpleDateFormat("EEE").format(date);
        String dateInMonth = new SimpleDateFormat("dd").format(date);
        String year = new SimpleDateFormat("yyyy").format(date);

        String months[] = {"Januar", "Februar", "Mars", "April", "Mai", "Juni", "Juli", "August", "September", "Oktober", "November", "Desember"};
        String dayOfWeekFinal = "Mandag";

        switch(dayOfWeekShort){
            case "Mon":
                dayOfWeekFinal = "Mandag";
                break;
            case "Tue":
                dayOfWeekFinal = "Tirsdag";
                break;
            case "Wed":
                dayOfWeekFinal = "Onsdag";
                break;
            case "Thu":
                dayOfWeekFinal = "Torsdag";
                break;
            case "Fri":
                dayOfWeekFinal = "Fredag";
                break;
            case "Sat":
                dayOfWeekFinal = "Lørdag";
                break;
            case "Sun":
                dayOfWeekFinal = "Søndag";
        }

        String displayDate = "Neste fridag: " + dayOfWeekFinal + " - " + dateInMonth + ". " + months[month-1] + " " + year;

        return displayDate;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.stored_schools_layout, parent, false);
        TextView schoolName = (TextView) rowView.findViewById(R.id.school_name);
        TextView nextDate = (TextView) rowView.findViewById(R.id.next_date);
        LinearLayout schoolNameContainer = (LinearLayout)rowView.findViewById(R.id.school_name_container);
        schoolName.setText(values[position]);

        String displayDate = dateFormatter(dates[position]);
        nextDate.setText(displayDate);

        schoolNameContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(context instanceof MainActivity){
                    ((MainActivity)context).setPosisjon(position);
                    ((MainActivity)context).goToCalendarList();
                }
            }
        });

        return rowView;
    }
}