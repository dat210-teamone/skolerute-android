package com.github.dat210_teamone.skolerute.adapters;

import android.content.Context;
import android.icu.text.DateFormat;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.github.dat210_teamone.skolerute.Activities.MainActivity;
import com.github.dat210_teamone.skolerute.R;
import com.github.dat210_teamone.skolerute.data.OneUtils;
import com.github.dat210_teamone.skolerute.data.SchoolManager;
import com.github.dat210_teamone.skolerute.model.SchoolVacationDay;

import org.w3c.dom.Text;

import java.util.Date;

/**
 * Created by Alex on 2410//16.
 */

public class VacationDaysListAdapter extends ArrayAdapter {


    private final Context context;
    private SchoolVacationDay[] schoolVacationDays; // Not in use yet

    public VacationDaysListAdapter(Context context, SchoolVacationDay[] schoolVacationDays) {
        super(context, -1, schoolVacationDays);
        this.context = context;
        this.schoolVacationDays = schoolVacationDays;
    }

    /* public SearchSchoolsAdapter(Context context, String[] values) {
        super(context, -1, OneUtils.toArrayList(values));
        this.context = context;
        this.values = values;
        this.valuesToDisplay = values;
    } */


    public class VacationDayObject {

        private Boolean schoolOpen;
        private Boolean sfoOpen;
        private Date vacationDate;
        private String schoolName;

        VacationDayObject(SchoolVacationDay day) {
            this.schoolOpen = day.isStudentDay();
            this.sfoOpen = day.isSfoDay();
            this.vacationDate = day.getDate();
            this.schoolName = day.getName();
        }

        public String getName() {
            return schoolName;
        }

        public Date getDate() {
            return vacationDate;
        }

        public Boolean getSchoolOpen(){
            return schoolOpen;
        }

        public Boolean getSfoOpen(){
            return sfoOpen;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.vacation_day, parent, false);

        // Switch for actual selected schools
        VacationDaysListAdapter.VacationDayObject vacationDay = new VacationDaysListAdapter.VacationDayObject(schoolVacationDays[position]);

        TextView schoolName = (TextView) rowView.findViewById(R.id.vacation_day_school_name);
        schoolName.setText(vacationDay.getName());

        TextView vacationDate = (TextView) rowView.findViewById(R.id.vacation_day_date);
        String formattedDate = OneUtils.dateFormatter(vacationDay.getDate());
        vacationDate.setText(formattedDate);

        ImageView schoolClosedIcon = (ImageView) rowView.findViewById(R.id.school_closed_icon);
        ImageView sfoClosedIcon = (ImageView) rowView.findViewById(R.id.sfo_closed_icon);

        if(vacationDay.getSchoolOpen()){
            schoolClosedIcon.setAlpha(0.0f);
        }
        if(vacationDay.getSfoOpen()){
            sfoClosedIcon.setAlpha(0.0f);
        }
        return rowView;
    }



}