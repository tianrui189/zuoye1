package com.example.homework7;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.homework7.bean.CartInfo;
import com.example.homework7.bean.GoodsInfo;
import com.example.homework7.database.CartDBHelper;
import com.example.homework7.database.GoodsDBHelper;
import com.example.homework7.utils.DateUtil;
import com.example.homework7.utils.FileUtil;
import com.example.homework7.utils.SharedUtil;
import com.example.homework7.utils.Utils;

import java.util.ArrayList;

public class GoodsListActivity extends AppCompatActivity implements View.OnClickListener {

    public final  String TAG = "GoodsListActivyt";
    private TextView tv_count;
    private LinearLayout ll_chananel;
    private  int mCount;
    private GoodsDBHelper goodsDBHelper ;
    private CartDBHelper cartDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_list);

        getSupportActionBar().hide();
        TextView textView = findViewById(R.id.tv_title);
        tv_count = findViewById(R.id.tv_count);
        ll_chananel = findViewById(R.id.ll_chananel);
        textView.setText("田蕊-商城");
        View header = findViewById(R.id.header);
//        header.findViewById(R.id.iv_cart).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(GoodsListActivity.this,GoodsDetailActivity.class);
//                startActivity(intent);
//            }
//        });
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.iv_cart){

        }
    }

    private void addToCart(long goods_id) {
        mCount++;
        tv_count.setText("" + mCount);
        SharedUtil.getIntance(this).writeShared("count", "" + mCount);
        CartInfo info = cartDBHelper.queryByGoodsId(goods_id);
        if (info != null) {
            info.count++;
            info.update_time = DateUtil.getNowDateTime("");
            cartDBHelper.update(info);
        } else {
            info = new CartInfo();
            info.goods_id = goods_id;
            info.count = 1;
            info.update_time = DateUtil.getNowDateTime("");
            cartDBHelper.insert(info);
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        // 获取共享参数保存的购物车中的商品数量
        mCount = Integer.parseInt(SharedUtil.getIntance(this).readShared("count", "0"));
        tv_count.setText("" + mCount);
        goodsDBHelper = GoodsDBHelper.getInstance(this, 1);
        goodsDBHelper.openReadLink();
        cartDBHelper = CartDBHelper.getInstance(this, 1);
        cartDBHelper.openWriteLink();
        downloadGoods();
        showGoods();
    }

    @Override
    protected void onPause() {
        super.onPause();
        goodsDBHelper.closeLink();
        cartDBHelper.closeLink();

    }

    private LinearLayout.LayoutParams mFullParams,mHalfParms;
    private void showGoods() {
        Log.d(TAG, "showGoods");
        // 移除线性布局ll_chananel下面的所有子视图
        ll_chananel.removeAllViews();
        // mFullParams这个布局参数的宽度占了一整行
        mFullParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        // mHalfParms这个布局参数的宽度与其它布局平均分
        mHalfParms = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1);
        // 给mHalfParms设置四周的空白距离
        mHalfParms.setMargins(Utils.dip2px(this, 2), Utils.dip2px(this, 2), Utils.dip2px(this, 2), Utils.dip2px(this, 2));
        // 创建一行的线性布局
        LinearLayout ll_row = newLinearLayout(LinearLayout.HORIZONTAL, 0);
        // 查询商品数据库中的所有商品记录
        ArrayList<GoodsInfo> goodsArray = goodsDBHelper.query("1=1");
        Log.d(TAG, "size:" + goodsArray.size());
        int i = 0;
        for (; i < goodsArray.size(); i++) {
            final GoodsInfo info = goodsArray.get(i);
            // 创建一个商品项的垂直线性布局，从上到下依次列出商品标题、商品图片、商品价格
            LinearLayout ll_goods = newLinearLayout(LinearLayout.VERTICAL, 1);
            ll_goods.setBackgroundColor(Color.WHITE);
            // 添加商品标题
            TextView tv_name = new TextView(this);
            tv_name.setLayoutParams(mFullParams);
            tv_name.setGravity(Gravity.CENTER);
            tv_name.setText(info.name);
            tv_name.setTextColor(Color.BLACK);
            tv_name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            // 添加商品小图
            ImageView iv_thumb = new ImageView(this);
            iv_thumb.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, Utils.dip2px(this, 150)));
            iv_thumb.setScaleType(ImageView.ScaleType.FIT_CENTER);
            iv_thumb.setImageBitmap(MainApplication.getInstance().mIconMap.get(info.rowid));
            iv_thumb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(GoodsListActivity.this, GoodsDetailActivity.class);
                    intent.putExtra("goods_id", info.rowid);
                    startActivity(intent);
                }
            });
            ll_goods.addView(iv_thumb);
            ll_goods.addView(tv_name);
            // 添加商品价格
            LinearLayout ll_bottom = newLinearLayout(LinearLayout.HORIZONTAL, 0);
            TextView tv_price = new TextView(this);
            tv_price.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 2));
            tv_price.setGravity(Gravity.CENTER);
            tv_price.setText("" + (int) info.price + "K RMB");
            tv_price.setTextColor(Color.RED);
            tv_price.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            ll_bottom.addView(tv_price);
            // 添加购物车按钮
            Button btn_add = new Button(this);
            btn_add.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 3));
            btn_add.setGravity(Gravity.CENTER);
            btn_add.setText("加入购物车");
            btn_add.setTextColor(Color.BLACK);
            btn_add.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
            btn_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addToCart(info.rowid);
                    Toast.makeText(GoodsListActivity.this,
                            "已添加一部" + info.name + "到购物车", Toast.LENGTH_SHORT).show();
                }
            });
            ll_bottom.addView(btn_add);
            ll_goods.addView(ll_bottom);
             ll_row.addView(ll_goods);
             if (i % 2 == 1) {
                ll_chananel.addView(ll_row);
                ll_row = newLinearLayout(LinearLayout.HORIZONTAL, 0);
            }
        }
        // 最后一行只有一个商品项，则补上一个空白格，然后把最后一行添加到ll_chananel
        if (i % 2 == 0) {
            ll_row.addView(newLinearLayout(LinearLayout.VERTICAL, 1));
            ll_chananel.addView(ll_row);
        }
    }

    private LinearLayout newLinearLayout(int orientation, int weight) {
        LinearLayout ll_new = new LinearLayout(this);
        ll_new.setLayoutParams((weight == 0) ? mFullParams : mHalfParms);
        ll_new.setOrientation(orientation);
        return ll_new;
    }



    private String mFirst = "true"; // 是否首次打开

    private void downloadGoods() {
         mFirst = SharedUtil.getIntance(this).readShared("first", "true");
         String path = getExternalFilesDir(
                Environment.DIRECTORY_DOWNLOADS).toString() + "/";
        if (mFirst.equals("true")) {
            ArrayList<GoodsInfo> goodsList = GoodsInfo.getDefaultList();
            for (int i = 0; i < goodsList.size(); i++) {
                GoodsInfo info = goodsList.get(i);
                long rowid = goodsDBHelper.insert(info);
                info.rowid = rowid;
                Bitmap thumb = BitmapFactory.decodeResource(getResources(), info.thumb);
                MainApplication.getInstance().mIconMap.put(rowid, thumb);
                String thumb_path = path + rowid + "_s.jpg";
                FileUtil.saveImage(thumb_path, thumb);
                info.thumb_path = thumb_path;
                Bitmap pic = BitmapFactory.decodeResource(getResources(), info.pic);
                String pic_path = path + rowid + ".jpg";
                FileUtil.saveImage(pic_path, pic);
                pic.recycle();
                info.pic_path = pic_path;
                goodsDBHelper.update(info);
            }
        } else {

            ArrayList<GoodsInfo> goodsArray = goodsDBHelper.query("1=1");
            for (int i = 0; i < goodsArray.size(); i++) {
                GoodsInfo info = goodsArray.get(i);
                Bitmap thumb = BitmapFactory.decodeFile(info.thumb_path);
                MainApplication.getInstance().mIconMap.put(info.rowid, thumb);
            }
        }
        SharedUtil.getIntance(this).writeShared("first", "false");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_cart, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_shopping) { // 点击了菜单项“去商场购物”
            Intent intent = new Intent(this, GoodsListActivity.class);
            startActivity(intent);
        } else if (id == R.id.menu_return) { // 点击了菜单项“返回”
            finish();
        }
        return true;
    }

}