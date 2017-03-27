package tech.soft.myproject.ui.act;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.animation.OvershootInterpolator;
import android.widget.Toast;

import java.util.List;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;
import tech.soft.myproject.R;
import tech.soft.myproject.callback.IOnItemClickListener;
import tech.soft.myproject.controls.ManagerImage;
import tech.soft.myproject.models.MyImage;
import tech.soft.myproject.ui.adapter.ImageRVAdapter;
import tech.soft.myproject.ui.ftag.SecondActivity;
import tech.soft.myproject.utils.UtilsPermission;

public class MainActivity extends AppCompatActivity implements IOnItemClickListener {
    private static final String TAG = "mMainActivity";
    public static final String KEY_ID = "KEY_ID";
    private List<MyImage> myImageList;
    private ImageRVAdapter mAdapter;
    private RecyclerView rvImage;
    private ManagerImage mManagerImage;
    private boolean isPermiss;
    private static  final int PERMISSION_CODE = 1;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isPermiss = UtilsPermission.checkPermission(this,PERMISSION_CODE);

        if (isPermiss){
            initComponent();
            initView();
        }
    }

    private void xl(){
        initComponent();
        initView();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){

            case PERMISSION_CODE:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    xl();
                    return;
                } else {
                    Toast.makeText(MainActivity.this,"Yeu cau permission",Toast.LENGTH_LONG).show();
                    finish();
                }
                break;
            default:
                return;
        }
    }

    private void initView() {
        AlphaInAnimationAdapter alphaInAnimationAdapter = new AlphaInAnimationAdapter(mAdapter);
        alphaInAnimationAdapter.setDuration(2000);
        alphaInAnimationAdapter.setFirstOnly(false);
        rvImage = (RecyclerView) findViewById(R.id.rvImage);
        GridLayoutManager manager = new GridLayoutManager(this,2);
        rvImage.setLayoutManager(manager);
        rvImage.setItemAnimator(new SlideInUpAnimator(new OvershootInterpolator(1f)));
        rvImage.setAdapter(alphaInAnimationAdapter);


        rvImage.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

    }

    private void initComponent() {
        mManagerImage = new ManagerImage(this);
        myImageList = mManagerImage.getPhoneGalleryImages();
        Log.d(TAG,myImageList.size() + "");
        mAdapter = new ImageRVAdapter(this,myImageList,this);

    }

    @Override
    public void itemClick(int pos) {
        Intent intent = new Intent(this,SecondActivity.class);
        intent.putExtra(KEY_ID,pos);

        startActivity(intent);
    }
}
