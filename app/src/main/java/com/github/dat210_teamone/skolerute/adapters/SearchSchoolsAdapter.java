package com.github.dat210_teamone.skolerute.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.github.dat210_teamone.skolerute.R;
import com.github.dat210_teamone.skolerute.data.OneUtils;

/**
 * Created by Alex on 1010//16.
 */


// NO LONGER IN USE

class SearchSchoolsAdapter extends ArrayAdapter<String> {

    private final Context context;
    private String[] values;
    private String[] valuesToDisplay;

    public SearchSchoolsAdapter(Context context, String[] values) {
        super(context, -1, OneUtils.toArrayList(values));
        this.context = context;
        this.values = values;
        this.valuesToDisplay = values;
    }

    public class SearchSchoolObject {

        private Boolean alreadyStored;

        SearchSchoolObject (Boolean alreadyStored) {
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

        return inflater.inflate(R.layout.add_schools_layout, parent, false);
    }

    //set school to be displayed and update view
    public void setSchoolsToView(String[] schoolsToView){
        valuesToDisplay = schoolsToView;
        if (schoolsToView.length > 1) { //TODO: Temporary workaround to be able to sort the list
            values = schoolsToView;
        }
        clear();
        addAll(schoolsToView);
        this.notifyDataSetChanged();
    }

    //used when checking if school should be displayed
    private boolean shouldSchoolNameBeDisplayed(String schoolName){
        for (String aValuesToDisplay : valuesToDisplay) {
            if (schoolName == aValuesToDisplay) {
                return true;
            }
        }
        return false;
    }



}
