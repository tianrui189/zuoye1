package com.example.homework8;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.homework8.bean.CartInfo;
import com.example.homework8.bean.GoodsInfo;
import com.example.homework8.database.CartDBHelper;
import com.example.homework8.database.GoodsDBHelper;
import com.example.homework8.utils.DateUtil;
import com.example.homework8.utils.SharedUtil;

public class GoodsDetailActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tv_title;
    private TextView tv_count;
    private TextView tv_goods_price;
    private TextView tv_goods_desc;
    private ImageView iv_goods_pic;
    private int mCount; // 购物车中的商品数量
    private long mGoodsId; // 当前商品的商品编号
    private GoodsDBHelper mGoodsHelper; // 声明一个商品数据库的帮助器对象
    private CartDBHelper mCartHelper; // 声明一个购物车数据库的帮助器对象

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_detail);

        tv_title = findViewById(R.id.tv_title);
        tv_count = findViewById(R.id.tv_count);
        tv_goods_price = findViewById(R.id.tv_goods_price);
        tv_goods_desc = findViewById(R.id.tv_goods_desc);
        iv_goods_pic = findViewById(R.id.iv_goods_pic);

        findViewById(R.id.iv_cart).setOnClickListener(this);
        findViewById(R.id.btn_add_cart).setOnClickListener(this);
    }


    // 把指定编号的商品添加到购物车
    private void addToCart(long goods_id) {
        mCount++;
        tv_count.setText("" + mCount);
        SharedUtil.getIntance(this).writeShared("count", "" + mCount);
        CartInfo info = mCartHelper.queryByGoodsId(goods_id);
        if (info != null) { // 购物车已存在该商品记录
            info.count++; // 该商品的数量加一
            info.update_time = DateUtil.getNowDateTime("");
            mCartHelper.update(info);
        } else {
            info = new CartInfo();
            info.goods_id = goods_id;
            info.count = 1;
            info.update_time = DateUtil.getNowDateTime("");
            mCartHelper.insert(info);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCount = Integer.parseInt(SharedUtil.getIntance(this).readShared("count", "0"));
        tv_count.setText("" + mCount);
        mGoodsHelper = GoodsDBHelper.getInstance(this, 1);
        mGoodsHelper.openReadLink();
        mCartHelper = CartDBHelper.getInstance(this, 1);
        mCartHelper.openWriteLink();
        showDetail();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mGoodsHelper.closeLink();
        mCartHelper.closeLink();
    }

    private void showDetail() {
        mGoodsId = getIntent().getLongExtra("goods_id", 0L);
        if (mGoodsId > 0) {
            GoodsInfo info = mGoodsHelper.queryById(mGoodsId);
            tv_title.setText(info.name);
            tv_goods_desc.setText(info.desc);
            tv_goods_price.setText("" + info.price);
            Bitmap pic = BitmapFactory.decodeFile(info.pic_path);
            iv_goods_pic.setImageBitmap(pic);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_shopping) { // 点击了菜单项“去商场购物”
            Intent intent = new Intent(this, GoodsListActivity.class);
            startActivity(intent);
        } else if (id == R.id.menu_cart) { // 点击了菜单项“返回”
            finish();
        }else if (id == R.id.menu_return) { // 点击了菜单项“返回”
            finish();
        }
        return true;
    }

    @Override
    public void onClick(View v) {

    }
}