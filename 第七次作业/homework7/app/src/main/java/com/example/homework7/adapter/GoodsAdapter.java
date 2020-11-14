package com.example.homework7.adapter;

import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.homework7.MainApplication;
import com.example.homework7.R;
import com.example.homework7.bean.GoodsInfo;

import java.util.ArrayList;

public class GoodsAdapter extends BaseAdapter implements View.OnClickListener {
    private final String TAG = "GoodsAdapter";
    private Context mContext;
    private ArrayList<GoodsInfo> mGoodsArray;

    public GoodsAdapter (Context context,ArrayList<GoodsInfo> goodsInfos ) {

    }
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if(convertView == null){
            holder = new ViewHolder();
//            convertView = LayoutInflater. from(mContext). inflate(R.layout.item_goods, null);
//            holder.tv_name = convertView. findViewById(R.id.tv_name);
//            holder.iv_thumb = convertView. findViewById(R.id.iv_thumb);
//            holder.tv_price = convertView. findViewById(R.id.tv.price);
//            holder.btn_add = convertView. findViewById(R.id.btn_add) ;
            convertView. setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();

        }

        final GoodsInfo info = mGoodsArray.get(position);
        Log.d(TAG,  "info , name: "+info. name);
        holder. tv_name.setText(info.name);
//        holder. iv_thumb. setImageBitmap(MainApplication .getInstance(). mIconMap. get(info. rowid));
        holder.tv_price. setText("" + (int) info.price);
        holder.btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
//        mAddCartListener.addToCart(info. rowid);
        Toast .makeText (mContext , "已添加一部" + info.name + "到购物车",Toast.LENGTH_LONG). show();
        return convertView;
    }

    @Override
    public void onClick(View view) {

    }
    public  final  class ViewHolder {
        public TextView tv_name,tv_price ;
        public ImageView iv_thumb;

        public Button btn_add;


    }
}
