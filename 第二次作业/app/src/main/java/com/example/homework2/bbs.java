package com.example.homework2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.format.DateUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import com.example.homework2.util.DateUtil;

public class bbs extends AppCompatActivity {
    private TextView tv_bbs;
    private TextView tv_control;
    private String[] mChatStr = {"好久不见","今天看了什么书？","今天天气真好","哈哈哈哈哈","我们去看电影吧。",};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bbs);

        tv_control = findViewById(R.id.tv_control);
        tv_control.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int random =(int)(Math.random()*10)%5;
                String newStr = String.format("%s\n%s %s",
                        tv_bbs.getText().toString(), DateUtil.getNowTime(),mChatStr[random]);
                tv_bbs.setText(newStr);
            }
        });
        tv_control.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                tv_bbs.setText("");
                return true;
            }
        });
        tv_bbs = findViewById(R.id.tv_bbs);
        tv_bbs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int random =(int)(Math.random()*10)%5;
                String newStr = String.format("%s\n%s %s",
                        tv_bbs.getText().toString(), DateUtil.getNowTime(),mChatStr[random]);
                tv_bbs.setText(newStr);
            }
        });
        tv_bbs.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                tv_bbs.setText("");
                return true;
            }
        });
        //设置tv_bbs内部文本的移动方式为滚动形式
        tv_bbs.setMovementMethod(new ScrollingMovementMethod());
    }


//    @Override
//    public void onClick(View v) {
//        if(v.getId() == R.id.tv_control||v.getId() ==R.id.tv_bbs){
//            int random =(int)(Math.random()*10)%5;
//            String newStr = String.format("%s\n%s %s",
//                    tv_bbs.getText().toString(), DateUtil.getNowTime(),mChatStr[random]);
//            tv_bbs.setText(newStr);
//        }
//
//    }

//    @Override
//    public boolean onLongClick(View v) {
//        if(v.getId() ==R.id.tv_control||v.getId()==R.id.tv_bbs){
//            tv_bbs.setText("");
//        }
//        return true;
//    }
}