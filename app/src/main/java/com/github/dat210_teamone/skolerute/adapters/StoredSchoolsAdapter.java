package com.github.dat210_teamone.skolerute.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.stored_schools_layout, parent, false);
        TextView schoolName = (TextView) rowView.findViewById(R.id.school_name);
        //      TextView nextDate = (TextView) rowView.findViewById(R.id.next_date);
        LinearLayout schoolNameContainer = (LinearLayout)rowView.findViewById(R.id.school_name_container);
        schoolName.setText(values[position]);
        MainActivity mainActivity = (MainActivity)getContext();

        //CHECKBOX
        CheckBox visibilityCheck = (CheckBox)rowView.findViewById(R.id.visibility_check);

        if(mainActivity.schoolsToView.contains(values[position])){
            visibilityCheck.setChecked(true);
        }

        visibilityCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mainActivity.goToCalendarView();
                if (visibilityCheck.isChecked()) {
                    if(mainActivity.schoolsToView.contains(values[position])){
                        mainActivity.schoolsToView.remove(values[position]);
                    } else {
                        mainActivity.schoolsToView.add(values[position]);
                    }
                } else {
                    if(mainActivity.schoolsToView.contains(values[position])){
                        mainActivity.schoolsToView.remove(values[position]);
                    }
                }
                if (mainActivity.calendarViewToggle.getTag() == "list_view") {
                    mainActivity.viewCalendarList();
                }
            }
        });
        //CHECKBOX END

        //SCHOOLSETTINGS
        ImageView schoolSettingsBtn = (ImageView) rowView.findViewById(R.id.stored_schools_item_menu);

        /*//POPUP
        PopupMenu settingsMenu = new PopupMenu(context, schoolSettingsBtn); // Usikker på om dette er slik du gjør det.
        //POPUP END*/


        schoolSettingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu settingsMenu = new PopupMenu(context, schoolSettingsBtn);
                //settingsMenu.setOnMenuItemClickListener(context);
               /* if(mainActivity.schoolManager.getNotifySchool(values[position])){
                    settingsMenu.getMenu().findItem(R.id.itemNotification).setChecked(true);
                }*/
                settingsMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getItemId() == R.id.itemNotification ){
                            if(item.isChecked()){
                                Log.i("checked status", "item is checked!");
                                item.setChecked(false);
                            } else{
                                Log.i("checked status", "item is NOT checked!");
                                item.setChecked(true);
                            }
                        }

                        // start http://stackoverflow.com/a/31727213
                        // Keep the popup menu open
                        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
                        item.setActionView(new View(context));
                        item.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
                            @Override
                            public boolean onMenuItemActionExpand(MenuItem item) {
                                return false;
                            }

                            @Override
                            public boolean onMenuItemActionCollapse(MenuItem item) {
                                return false;
                            }
                        });
                        //end http://stackoverflow.com/a/31727213

                        return false;
                    }
                });
                settingsMenu.inflate(R.menu.stored_school_popup_menu);
                settingsMenu.show();
            }
        });
        //SCHOOLSETTINGS END

       /* schoolName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.clearCheckedSchools();
                mainActivity.schoolsToView.add(values[position]);
                mainActivity.refreshCheckedSchools();
            }
        }); */

        //       String displayDate = "Neste fridag: " + dateFormatter(dates[position]);
        //      nextDate.setText(displayDate);


        return rowView;
    }
}