package com.example.homework8;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;

import java.util.ArrayList;

public class SplashActivity extends AppCompatActivity {
    private int[] lanuchImageArray = {
            R.drawable.guid1, R.drawable.guid2, R.drawable.guid3};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
         ViewPager vp_launch = findViewById(R.id.vp_launch);
         LaunchSimpleAdapter adapter = new LaunchSimpleAdapter(this, lanuchImageArray);
         vp_launch.setAdapter(adapter);
         vp_launch.setCurrentItem(0);
    }

    public void onClick(View v) {
        if (v.getId() == R.id.btn_start) {
            Intent intent = new Intent(this, GoodsListActivity.class);
            startActivity(intent);
        }
    }
}

class LaunchSimpleAdapter extends PagerAdapter {
    private Context mContext; // 声明一个上下文对象
    private ArrayList<View> mViewList = new ArrayList<View>();

    public LaunchSimpleAdapter(Context context, int[] imageArray) {
        mContext = context;
        for (int i = 0; i < imageArray.length; i++) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_launch, null);
            ImageView iv_launch = view.findViewById(R.id.iv_launch);
            RadioGroup rg_indicate = view.findViewById(R.id.rg_indicate);
            Button btn_start = view.findViewById(R.id.btn_start);
            iv_launch.setImageResource(imageArray[i]);
            if (i == imageArray.length - 1) {
                btn_start.setVisibility(View.VISIBLE);
                btn_start.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        context.startActivity(new Intent(context,GoodsListActivity.class));
                    }
                });
            }
            mViewList.add(view);
        }
    }

    // 获取页面项的个数
    public int getCount() {
        return mViewList.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mViewList.get(position));
    }

    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mViewList.get(position));
        return mViewList.get(position);
    }
}