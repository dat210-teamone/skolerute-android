package com.github.dat210_teamone.skolerute.data;

import android.os.AsyncTask;

import com.github.dat210_teamone.skolerute.model.PageInfo;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Nicolas on 03.10.2016.
 */

public class GetPageInfoTask extends AsyncTask<URL, Void, String> {

    @Override
    protected String doInBackground(URL... params) {
        if (params.length == 0)
            return null;
        URL mainUrl = params[0];
        StringBuffer buffer = new StringBuffer();
        InputStream input = null;
        try {
            input = mainUrl.openStream();

            int read = 0;
            byte[] bytes = new byte[1024];
            while ((read = input.read(bytes)) > 0){
                buffer.append(new String(bytes, 0, read));
            }
            if (read > 0)
                buffer.append(new String(bytes, 0, read));
            input.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        String s = new String(buffer);
        return s;
    }
}
