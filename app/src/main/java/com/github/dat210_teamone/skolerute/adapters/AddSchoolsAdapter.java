package com.github.dat210_teamone.skolerute.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.dat210_teamone.skolerute.Activities.MainActivity;
import com.github.dat210_teamone.skolerute.Fragments.AddSchools;
import com.github.dat210_teamone.skolerute.R;
import com.github.dat210_teamone.skolerute.data.OneUtils;
import com.github.dat210_teamone.skolerute.data.SchoolManager;

/**
 * Created by Alex on 289//16.
 */

public class AddSchoolsAdapter extends ArrayAdapter<String> {

    private final Context context;
    private String[] values;
    private String[] valuesToDisplay;
    public AddSchools parentFragment;

    public AddSchoolsAdapter(Context context, String[] values, AddSchools fragment) {
        super(context, -1, OneUtils.toArrayList(values));
        this.context = context;
        this.values = values;
        this.valuesToDisplay = values;
        this.parentFragment = fragment;
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
        //if should not be displayed, return null_item
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
            addSchool.setText(R.string.remove_school);
            addSchool.setBackgroundResource(R.color.colorClickableSecondary);
        }

        //Set onClickListener for both containing layout and button
        setupAddListener(schoolNameContainer, addSchool, addSchoolObject, position);
        setupAddListener(addSchool, addSchool, addSchoolObject, position);

        return rowView;
    }


    public void setupAddListener(View object, Button addSchool, AddSchoolObject addSchoolObject, int position) {
        object.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!addSchoolObject.getAlreadyStored()) {
                    SchoolManager.getDefault().addDefault(values[position]);
                    addSchoolObject.setAlreadyStored(true);
                    addSchool.setText(R.string.remove_school);
                    addSchool.setBackgroundColor(context.getResources().getColor(R.color.colorClickableSecondary));
                } else {
                    SchoolManager.getDefault().removeDefault(values[position]);
                    addSchoolObject.setAlreadyStored(false);
                    addSchool.setText(R.string.store_school);
                    addSchool.setBackgroundColor(context.getResources().getColor(R.color.colorClickable));
                }
                Log.i("add school stuff", "id of view: " + object.getId() );
                parentFragment.updateFinishedButton();
            }
        });
    }

    //set school to be displayed and update view
    public void setSchoolsToView(String[] schoolsToView){
        valuesToDisplay = schoolsToView;
        if (schoolsToView.length > 0) { //TODO: Temporary workaround to be able to sort the list
            values = schoolsToView;
        }
        clear();
        addAll(schoolsToView);
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