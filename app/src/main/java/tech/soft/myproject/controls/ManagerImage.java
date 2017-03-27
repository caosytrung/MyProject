package tech.soft.myproject.controls;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import tech.soft.myproject.models.MyImage;

/**
 * Created by dee on 26/03/2017.
 */

public class ManagerImage {
    private Context mContext;

    public  ManagerImage(Context context){
        mContext = context;
    }

    public List<MyImage> getPhoneGalleryImages() {
        List<MyImage> myImageList = new ArrayList<>();

    String[] projection = new String[] {
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
            MediaStore.Images.Media.DATE_TAKEN
    };

    // content:// style URI for the "primary" external storage volume
    Uri images = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

    // Make the query.
    String[] query = new String[]{MediaStore.Images.Thumbnails._ID};

    Cursor cursor = mContext.getContentResolver().query(images,query,null,null,null);

        Log.d("zzz",cursor.getCount() + "");
        int indexID = cursor.getColumnIndex(MediaStore.Images.Thumbnails._ID);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            int id = cursor.getInt(indexID);
            myImageList.add(new MyImage(id));
            cursor.moveToNext();
        }

        Log.d("asdasda",myImageList.size() + "");
        cursor.close();

        return myImageList;


    }
}
