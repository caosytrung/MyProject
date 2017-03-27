package tech.soft.myproject.ui.ftag;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import java.util.List;

import tech.soft.myproject.R;
import tech.soft.myproject.callback.IOnItemClickListener;
import tech.soft.myproject.controls.ManagerImage;
import tech.soft.myproject.models.MyImage;
import tech.soft.myproject.ui.act.MainActivity;
import tech.soft.myproject.ui.adapter.ImageRVAdapter;
import tech.soft.myproject.ui.adapter.ImageViewPagerAdapter;

/**
 * Created by dee on 26/03/2017.
 */

public class SecondActivity extends AppCompatActivity implements IOnItemClickListener {
    private List<MyImage> myImageList;
    private ImageRVAdapter mImageRVAdapter;
    private ImageViewPagerAdapter mImageViewPagerAdapter;
    private ManagerImage mManagerImage;
    private RecyclerView rvSencond;
    private ViewPager vpMain;
    private  int currentPos;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        currentPos = getIntent().getIntExtra(MainActivity.KEY_ID,0);
        initComponent();
        initView();
    }

    private void initView() {


        vpMain = (ViewPager) findViewById(R.id.vPmain);
        vpMain.setAdapter(mImageViewPagerAdapter);
        mImageViewPagerAdapter.notifyDataSetChanged();

        rvSencond = (RecyclerView) findViewById(R.id.rvSecond);

        vpMain.setCurrentItem(currentPos);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);

        rvSencond = (RecyclerView) findViewById(R.id.rvSecond);
        rvSencond.setLayoutManager(manager);

        rvSencond.setAdapter(mImageRVAdapter);
        mImageRVAdapter.notifyDataSetChanged();
        rvSencond.scrollToPosition(currentPos);
        vpMain.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                rvSencond.scrollToPosition(position);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void initComponent() {
        mManagerImage = new ManagerImage(this);
        myImageList = mManagerImage.getPhoneGalleryImages();
        mImageViewPagerAdapter = new ImageViewPagerAdapter(this,myImageList);

        mImageRVAdapter = new ImageRVAdapter(this,myImageList,this);


    }

    @Override
    public void itemClick(int pos) {
        vpMain.setCurrentItem(pos);
    }
}
