package com.github.dat210_teamone.skolerute.data;

import android.os.AsyncTask;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by Nicolas on 03.10.2016.
 * Part of project skolerute-android
 */

public class GetPageInfoTask extends AsyncTask<URL, Void, byte[]> {

    @Override
    protected byte[] doInBackground(URL... params) {
        if (params.length == 0)
            return null;
        URL mainUrl = params[0];
        ByteArrayOutputStream stream = new ByteArrayOutputStream();

        InputStream input = null;
        try {
            input = mainUrl.openStream();

            int read = 0;
            byte[] bytes = new byte[1024];
            while ((read = input.read(bytes)) > 0){
                stream.write(bytes, 0, read);
                //buffer.append(new String(bytes, 0, read));
            }
            if (read > 0)
                stream.write(bytes, 0, read);
            input.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return stream.toByteArray();
    }
}
