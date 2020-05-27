package com.jayant.workmanger.Workers;


import android.content.Context;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.jayant.workmanger.R;
import com.jayant.workmanger.Utils.WorkUtils;

public class BlurWorker extends Worker {
    private static final String TAG = "main";
    public static  Bitmap output;
    public static Uri outputUri;
    public BlurWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        try{
            Bitmap picture= BitmapFactory.decodeResource(getApplicationContext().getResources(),
                    R.drawable.jayant);
           output= WorkUtils.blurImage(picture,getApplicationContext());
            Log.d(TAG, "image blurred successfully "+output);
            outputUri=WorkUtils.WriteBitmapToFile(getApplicationContext(),output);
            Log.d(TAG, "Write to file "+outputUri);

        }
        catch (Throwable t)
        {
            Log.d(TAG, "Exception "+t.getMessage());
            return Result.failure();
        }
        return Result.success();
    }
}
