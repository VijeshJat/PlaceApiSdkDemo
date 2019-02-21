package com.flair.placeapidemo;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.content.ContextCompat;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Vijesh Jat on 18-02-2019.
 */
public class Utility {

    //dateFormat
    public static String YYYYMMDDHHMMSSA = "yyyy-MM-dd HH:mm:ss a";
    public static String MMMDDYYYY = "MMM dd, yyyy";
    public static String YYYYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss";
    public static String DDMMYYYYHHMMA = "MM/dd/yy HH:mm a";
    public static String DDMMYYYY_hhmma = "MM/dd/yy hh:mm a";
    public static String DDMMYYYY_hhmmssa = "MM/dd/yy hh:mm:ss a";
    public static String DDMMYYYY = "dd-MM-yyyy";
    public static String HHMM = "HH:mm";
    public static String HHMMA = "hh:mm a";
    public static String MMMDDYYYYHHMMA = "MMM dd, yyyy hh:mm a";


    // TODO: 23-01-2019  comment method before go live
    public static void writeLogFileToDevice(Context context, String text) {

        int result = ContextCompat.checkSelfPermission(context, android.Manifest.permission.READ_EXTERNAL_STORAGE);
        int result2 = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (result != PackageManager.PERMISSION_GRANTED || result2 != PackageManager.PERMISSION_GRANTED) {
            return;
        }


        File tarjeta = Environment.getExternalStorageDirectory();
        File logFile = new File(tarjeta.getAbsolutePath() + "/", "WorkManager.txt");
        if (!logFile.exists()) {
            try {
                logFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            //BufferedWriter for performance, true to set append to file flag
            BufferedWriter buf = new BufferedWriter(new FileWriter(logFile, true));
            buf.append(getCurrentTime(YYYYMMDDHHMMSSA) + " -- " + text);
            buf.newLine();
            buf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static String getCurrentTime(String timeDatePattern) {
        String formattedDate = "";
        Calendar cal = Calendar.getInstance();
        Date date = cal.getTime();
        DateFormat dateFormat = new SimpleDateFormat(timeDatePattern); //  "HH:mm:ss"
        formattedDate = dateFormat.format(date);

        return formattedDate;
    }

}
