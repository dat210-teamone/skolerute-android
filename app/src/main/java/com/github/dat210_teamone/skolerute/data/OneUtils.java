package com.github.dat210_teamone.skolerute.data;

import com.android.internal.util.Predicate;
import com.github.dat210_teamone.skolerute.R;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Nicolas on 21.09.2016.
 */

public final class OneUtils {
    public static <T> boolean Contains(T[] data, Predicate<T> check){
        for (T t : data){
            if (check.apply(t)){
                return true;
            }
        }
        return false;
    }

    public static <T> T Find(T[] data, Predicate<T> check){
        for (T t : data){
            if (check.apply(t)){
                return t;
            }
        }
        return null;
    }

    public static String getFileName(URL url){
        String[] test = url.getFile().split("/");
        return test[test.length - 1];
    }

    public static void copy(InputStream inputStream, OutputStream outputStream) throws Exception{
        int available = inputStream.available();
        while (available > 0){
            byte[] bytes;
            if (available > 2048) {
                bytes = new byte[2048];
            }else{
                bytes = new byte[available];
            }
            inputStream.read(bytes);
            outputStream.write(bytes);
            available = inputStream.available();
        }
    }

    public static boolean isNumber(String s){

        for (int i = 0; i < s.length(); i++)
        {
            if (!Character.isDigit(s.charAt(i)))
                return false;
        }
        return true;
    }

    public static <T> ArrayList<T> toArrayList(T[] items)
    {
        return new ArrayList<T>(Arrays.asList(items));
    }

    public static String dateFormatter(Date date){

        int month = Integer.parseInt(new SimpleDateFormat("MM").format(date));
        String dayOfWeekShort = new SimpleDateFormat("EEE").format(date);
        String dateInMonth = new SimpleDateFormat("dd").format(date);
        String year = new SimpleDateFormat("yyyy").format(date);

        String months[] = {"Januar", "Februar", "Mars", "April", "Mai", "Juni", "Juli", "August", "September", "Oktober", "November", "Desember"};
        String dayOfWeekFinal = "Mandag";

        //InterfaceManager.getContext().getResources().getString(R.string.Mon);
        switch(dayOfWeekShort){
            case "Mon":
                dayOfWeekFinal = InterfaceManager.getContext().getResources().getString(R.string.Mon);
                break;
            case "Tue":
                dayOfWeekFinal = InterfaceManager.getContext().getResources().getString(R.string.Tue);
                break;
            case "Wed":
                dayOfWeekFinal = InterfaceManager.getContext().getResources().getString(R.string.Wen);
                break;
            case "Thu":
                dayOfWeekFinal = InterfaceManager.getContext().getResources().getString(R.string.Thu);
                break;
            case "Fri":
                dayOfWeekFinal = InterfaceManager.getContext().getResources().getString(R.string.Fri);
                break;
            case "Sat":
                dayOfWeekFinal = InterfaceManager.getContext().getResources().getString(R.string.Sat);
                break;
            case "Sun":
                dayOfWeekFinal = InterfaceManager.getContext().getResources().getString(R.string.Sun);
        }

        return dayOfWeekFinal + " - " + dateInMonth + ". " + months[month-1] + " " + year;
    }

    public static <T> T firstOrNull(T[] array){
        if (array.length > 0)
            return array[0];
        else
            return null;
    }

    public static boolean sameDay(Date a, Date b) {
        Calendar cA = Calendar.getInstance();
        cA.setTime(a);
        Calendar cB = Calendar.getInstance();
        cB.setTime(b);

        return cA.get(Calendar.YEAR) == cB.get(Calendar.YEAR) && cA.get(Calendar.MONTH) == cB.get(Calendar.MONTH) && cA.get(Calendar.DATE) == cB.get(Calendar.DATE);
    }

}
