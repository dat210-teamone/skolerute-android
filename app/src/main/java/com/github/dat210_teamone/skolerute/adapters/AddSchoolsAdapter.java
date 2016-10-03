package com.github.dat210_teamone.skolerute.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.github.dat210_teamone.skolerute.R;
import com.github.dat210_teamone.skolerute.data.SchoolManager;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Alex on 289//16.
 */

public class AddSchoolsAdapter extends ArrayAdapter<String> {

    private final Context context;
    private final String[] values;
    private Boolean alreadyStored;

    public AddSchoolsAdapter(Context context, String[] values) {
        super(context, -1, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.add_schools_layout, parent, false);
        TextView schoolName = (TextView) rowView.findViewById(R.id.school_name);
        schoolName.setText(values[position]);
        alreadyStored = SchoolManager.getDefault().checkName(values[position]); // Replace with method
        Button addSchool = (Button) rowView.findViewById(R.id.add_button);
        if (alreadyStored) {
            addSchool.setText("Fjern");
        }
        addSchool.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!alreadyStored) {
                    SchoolManager.getDefault().addDefault(values[position]);
                    alreadyStored = true;
                    addSchool.setText("Fjern");
                } else {
                    SchoolManager.getDefault().removeDefault(values[position]);
                    alreadyStored = false;
                    addSchool.setText("Lagre");
                }
            }
        });

        return rowView;
    }
}