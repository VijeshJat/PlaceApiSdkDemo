package com.flair.placeapidemo;

import android.support.annotation.NonNull;
import android.util.Log;

import androidx.work.Worker;

/**
 * Created by Vijesh Jat on 18-02-2019.
 */
public class WorkManagerOne extends Worker {
    @NonNull
    @Override
    public Result doWork() {

        for (int i = 0; i <6; i++) {

            try {
                Thread.sleep(20000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Log.e("TESTING"," This is from work one and values of i - "+i);
            Utility.writeLogFileToDevice(getApplicationContext()," This is from work one and values of i - "+i);
        }
        return Result.SUCCESS;
    }
}
