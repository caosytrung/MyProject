package tech.soft.myproject.ui.adapter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.List;

import tech.soft.myproject.R;
import tech.soft.myproject.callback.IOnItemClickListener;
import tech.soft.myproject.models.MyImage;

/**
 * Created by dee on 26/03/2017.
 */

public class ImageRVAdapter extends RecyclerView.Adapter<ImageRVAdapter.MyViewHolder> {
    private IOnItemClickListener mIOnItemClickListener;
    private Context mContext;
    private List<MyImage> myImageList;

    public ImageRVAdapter(Context context,List<MyImage> myImages,IOnItemClickListener iOnItemClickListener){
        mContext = context;
        mIOnItemClickListener = iOnItemClickListener;
        myImageList = myImages;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.item_image,parent,false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.vCOntainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mIOnItemClickListener.itemClick(position);
            }
        });

        int idImage = myImageList.get(position).getId();
        Uri uri = Uri.withAppendedPath( MediaStore.Images.Media.EXTERNAL_CONTENT_URI, Integer.toString(idImage) );
       // Picasso.with(mContext).load(uri).resize(128,128).into(holder.ivImage);

        decodeFile(getRealPathFromURI(mContext,uri),holder.ivImage);

    }
    public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri,  proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    @Override
    public int getItemCount() {
        return myImageList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivImage;
        private View vCOntainer;
        public MyViewHolder(View itemView) {
            super(itemView);
            ivImage = (ImageView) itemView.findViewById(R.id.itemIv);
            vCOntainer = itemView;

        }
    }
    public  void decodeFile(String filePath, ImageView image) {
        // Decode image size
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, o);

        // The new size we want to scale to
        final int REQUIRED_SIZE = 1024;

        // Find the correct scale value. It should be the power of 2.
        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;
        while (true) {
            if (width_tmp < REQUIRED_SIZE && height_tmp < REQUIRED_SIZE)
                break;
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }

        // Decode with inSampleSize
        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        Bitmap bitmap = BitmapFactory.decodeFile(filePath, o2);

        image.setImageBitmap(bitmap);
    }
}
