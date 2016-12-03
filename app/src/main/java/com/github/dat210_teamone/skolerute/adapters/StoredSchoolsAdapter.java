package com.github.dat210_teamone.skolerute.adapters;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.LayoutInflater;
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
import com.github.dat210_teamone.skolerute.R;
import com.github.dat210_teamone.skolerute.data.SchoolManager;
import com.github.dat210_teamone.skolerute.model.SchoolInfo;

import java.util.Date;

/**
 * Created by Alex on 289//16.
 * Part of project skolerute-android
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
        LinearLayout schoolNameContainer = (LinearLayout)rowView.findViewById(R.id.school_name_container);
        schoolName.setText(values[position]);
        MainActivity mainActivity = (MainActivity)getContext();

        //CHECKBOX
        CheckBox visibilityCheck = (CheckBox)rowView.findViewById(R.id.visibility_check);

        if(mainActivity.schoolsToView.contains(values[position])){
            visibilityCheck.setChecked(true);
        }

        visibilityCheck.setOnClickListener(v -> {
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
        });
        //CHECKBOX END

        setupPopupMenu(rowView, mainActivity, position);

        return rowView;
    }

    private void setupPopupMenu(View rowView, MainActivity mainActivity, int position){
        ImageView schoolSettingsBtn = (ImageView) rowView.findViewById(R.id.stored_schools_item_menu);
        ImageView notiBell = (ImageView) rowView.findViewById(R.id.notification_bell);
        //If notification icon should show
        if(mainActivity.schoolManager.getNotifySchool(values[position])){
            notiBell.setVisibility(View.INVISIBLE);
        } else{
            notiBell.setVisibility(View.VISIBLE);
        }

        schoolSettingsBtn.setOnClickListener(v -> {
            PopupMenu settingsMenu = new PopupMenu(context, schoolSettingsBtn);
            settingsMenu.inflate(R.menu.stored_school_popup_menu);

            MenuItem notificationItem = settingsMenu.getMenu().findItem(R.id.itemNotification);
            //If school should notify
            if(mainActivity.schoolManager.getNotifySchool(values[position])){
                notificationItem.setChecked(true);
                //TODO: hide no notification bell icon
            } else{
                //TODO: show no notification bell icon
                notificationItem.setChecked(false);
            }

            if(mainActivity.schoolManager.getGlobalNotification()){
                notificationItem.setEnabled(true);
            } else{
                notificationItem.setEnabled(false);
            }
            settingsMenu.setOnMenuItemClickListener(item -> {
                if(item.getItemId() == R.id.itemNotification ){
                    if((mainActivity.schoolManager.getNotifySchool(values[position]))){
                        item.setChecked(false);
                        mainActivity.schoolManager.removeNotifySchool(values[position]);
                        notiBell.setVisibility(View.VISIBLE);
                    } else{
                        item.setChecked(true);
                        mainActivity.schoolManager.addNotifySchool(values[position]);
                        notiBell.setVisibility(View.INVISIBLE);
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

                }

                else if (item.getItemId() == R.id.itemSchoolInfo) {
                    AlertDialog schoolInfoDialog = new AlertDialog.Builder(getContext()).create();
                    SchoolInfo info = SchoolManager.getDefault().getSchoolInfo(values[position]);
                    schoolInfoDialog.setTitle(values[position]);

                    TextView message = new TextView(getContext());
                    SpannableStringBuilder infoMessage = new SpannableStringBuilder();

                    infoMessage.append("\n      ");
                    infoMessage.append(info.getAddress());
                    infoMessage.append("\n\n      ");
                    infoMessage.append(info.getHomePage());

                    Linkify.addLinks(infoMessage, Linkify.WEB_URLS);
                    message.setText(infoMessage);
                    message.setMovementMethod(LinkMovementMethod.getInstance());
                    message.setTextSize(16.0f);
                    schoolInfoDialog.setView(message);

                    schoolInfoDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Ok",
                            (dialog, which) -> {
                                dialog.dismiss();
                            });
                    schoolInfoDialog.setIcon(R.drawable.ic_school_info);
                    schoolInfoDialog.show();
                    return true;
                }

                else if (item.getItemId() == R.id.itemRemoveSchool) {
                    SchoolManager.getDefault().removeDefault(values[position]);
                    int lol = mainActivity.schoolManager.getSelectedSchools().length;
                    if (mainActivity.schoolManager.getSelectedSchools().length < 1) {
                        mainActivity.goToAddSchools();
                    } else {
                        mainActivity.goToStoredSchools();
                    }
                }

                return false;
            });

            settingsMenu.show();
        });
    }
}