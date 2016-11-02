package com.github.dat210_teamone.skolerute.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.dat210_teamone.skolerute.R;
import com.github.dat210_teamone.skolerute.data.SchoolManager;

/**
 * Created by Alex on 289//16.
 */

public class AddSchoolsAdapter extends ArrayAdapter<String> {

    private final Context context;
    private final String[] values;
    private String[] valuesToDisplay;

    public AddSchoolsAdapter(Context context, String[] values) {
        super(context, -1, values);
        this.context = context;
        this.values = values;
        this.valuesToDisplay = values;
    }

    public class AddSchoolObject {

        private Boolean alreadyStored;

        AddSchoolObject (Boolean alreadyStored) {
            this.alreadyStored = alreadyStored;
        }

        public Boolean getAlreadyStored() {
            return alreadyStored;
        }

        public void setAlreadyStored(Boolean alreadyStored) {
            this.alreadyStored = alreadyStored;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        //if shoudln't be displayed, return null_item
        if(!shouldSchoolNameBeDisplayed(values[position])){
            return inflater.inflate(R.layout.null_item, null);
        }

        View rowView = inflater.inflate(R.layout.add_schools_layout, parent, false);

        AddSchoolObject addSchoolObject = new AddSchoolObject(SchoolManager.getDefault().checkName(values[position]));

        TextView schoolName = (TextView) rowView.findViewById(R.id.school_name);
        schoolName.setText(values[position]);
        Button addSchool = (Button) rowView.findViewById(R.id.add_button);
        LinearLayout schoolNameContainer = (LinearLayout) rowView.findViewById(R.id.school_add);

        if (addSchoolObject.getAlreadyStored()) {
            addSchool.setText("Fjern");
            addSchool.setBackgroundResource(R.color.colorClickableSecondary);
        }

        addSchool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!addSchoolObject.getAlreadyStored()) {
                    SchoolManager.getDefault().addDefault(values[position]);
                    addSchoolObject.setAlreadyStored(true);
                    addSchool.setText("Fjern");
                    addSchool.setBackgroundResource(R.color.colorClickableSecondary);
                } else {
                    SchoolManager.getDefault().removeDefault(values[position]);
                    addSchoolObject.setAlreadyStored(false);
                    addSchool.setText("Lagre");
                    addSchool.setBackgroundResource(R.color.colorClickable);
                }
            }
        });

        schoolNameContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!addSchoolObject.getAlreadyStored()) {
                    SchoolManager.getDefault().addDefault(values[position]);
                    addSchoolObject.setAlreadyStored(true);
                    addSchool.setText("Fjern");
                    addSchool.setBackgroundResource(R.color.colorClickableSecondary);
                } else {
                    SchoolManager.getDefault().removeDefault(values[position]);
                    addSchoolObject.setAlreadyStored(false);
                    addSchool.setText("Lagre");
                    addSchool.setBackgroundResource(R.color.colorClickable);
                }
            }
        });

        return rowView;
    }

    //set school to be displayed and update view
    public void setSchoolsToView(String[] schoolsToView){
        valuesToDisplay = schoolsToView;
        this.notifyDataSetChanged();
    }

    //used when checking if school should be displayed
    private boolean shouldSchoolNameBeDisplayed(String schoolName){
        for(int i=0; i<valuesToDisplay.length; i++) {
            if (schoolName == valuesToDisplay[i]) {
                return true;
            }
        }
        return false;
    }
}