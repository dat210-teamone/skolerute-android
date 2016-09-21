package com.github.dat210_teamone.skolerute;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import com.github.dat210_teamone.skolerute.data.DummyStorage;
import com.github.dat210_teamone.skolerute.model.SchoolInfo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class NearestSchool extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearest_school);

        DummyStorage alfa=new DummyStorage();
        SchoolInfo[] beta=alfa.getSchoolInfo();
        String[] noe= new String[beta.length];
        for(int x=0; x<beta.length; x++){
            noe[x]=beta[x].getSchoolName();
        }

        ListView listet=(ListView)findViewById(R.id.listview);
        ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < noe.length; ++i) {
            list.add(noe[i]);
        }
        StableArrayAdapter adapter = new StableArrayAdapter(this,
                android.R.layout.simple_list_item_1, list);
        listet.setAdapter(adapter);
    }
    private class StableArrayAdapter extends ArrayAdapter<String> {

        HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

        public StableArrayAdapter(Context context, int textViewResourceId,
                                  List<String> objects) {
            super(context, textViewResourceId, objects);
            for (int i = 0; i < objects.size(); ++i) {
                mIdMap.put(objects.get(i), i);
            }
        }

        @Override
        public long getItemId(int position) {
            String item = getItem(position);
            return mIdMap.get(item);
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

    }

}

