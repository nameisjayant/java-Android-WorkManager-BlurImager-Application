package com.jayant.workmanger.Utils;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.net.Uri;

import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WorkUtils {


    private static final float BITMAP_SCALE = 0.4f;
    private static final float BLUR_RADIUS = 20.5f;

    public static Bitmap blurImage(Bitmap image,Context context) {
        int width = Math.round(image.getWidth() * BITMAP_SCALE);
        int height = Math.round(image.getHeight() * BITMAP_SCALE);

        Bitmap inputBitmap = Bitmap.createScaledBitmap(image, width, height, false);
        Bitmap outputBitmap = Bitmap.createBitmap(inputBitmap);

        RenderScript rs = RenderScript.create(context);
        ScriptIntrinsicBlur theIntrinsic = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        Allocation tmpIn = Allocation.createFromBitmap(rs, inputBitmap);
        Allocation tmpOut = Allocation.createFromBitmap(rs, outputBitmap);
        theIntrinsic.setRadius(BLUR_RADIUS);
        theIntrinsic.setInput(tmpIn);
        theIntrinsic.forEach(tmpOut);
        tmpOut.copyTo(outputBitmap);

        return outputBitmap;
    }
    public static Uri WriteBitmapToFile(Context mContext, Bitmap bitmap){

        String mTimeStamp = new SimpleDateFormat("ddMMyyyy_HHmm").format(new Date());

        String mImageName = "snap_"+mTimeStamp+".jpg";

        ContextWrapper wrapper = new ContextWrapper(mContext);

        File file = wrapper.getDir("Images",Context.MODE_PRIVATE);

        file = new File(file, "snap_"+ mImageName+".jpg");

        try{

            OutputStream stream = null;

            stream = new FileOutputStream(file);

            bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);

            stream.flush();

            stream.close();

        }catch (IOException e)
        {
            e.printStackTrace();
        }

        Uri mImageUri = Uri.parse(file.getAbsolutePath());

        return mImageUri;
    }
}
