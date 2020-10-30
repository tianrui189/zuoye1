package com.example.tx.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tx.R;
import com.example.tx.Util.DateUtil;
import com.example.tx.Util.ViewUtil;
import com.example.tx.bean.UserInfo;
import com.example.tx.database.UserDBHelper;


public class RegisterWriteActivity extends AppCompatActivity implements View.OnClickListener{

    private UserDBHelper mHelper; // 声明一个用户数据库帮助器的对象
    private EditText ct_name;
    private EditText ct_phonenumber;
    private EditText ct_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_write);

        ct_name = findViewById(R.id.ct_name);
        ct_phonenumber = findViewById(R.id.ct_phonenumber);
        ct_password = findViewById(R.id.ct_password);

        findViewById(R.id.btn_save).setOnClickListener(this);

    }




    @Override
    protected void onStart() {
        super.onStart();
        // 获得数据库帮助器的实例
        mHelper = UserDBHelper.getInstance(this, 2);
        // 打开数据库帮助器的写连接
        mHelper.openWriteLink();
    }

    @Override
    protected void onStop() {
        super.onStop();
        // 关闭数据库连接
        mHelper.closeLink();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_save) {
            String name = ct_name.getText().toString();
            String phone = ct_phonenumber.getText().toString();
            String pwd = ct_password.getText().toString();
//            String weight = et_weight.getText().toString();
            if (TextUtils.isEmpty(name)) {
                showToast("请输入姓名");
                return;
            } else if (TextUtils.isEmpty(phone)) {
                showToast("请输入电话号码");
                return;
            } else if (TextUtils.isEmpty(pwd)) {
                showToast("请输入密码");
                return;

            }


            // 以下声明一个用户信息对象，并填写它的各字段值
            UserInfo info = new UserInfo();
            info.name = name;
            info.phone = phone;
            info.pwd = pwd;
//            info.weight = Float.parseFloat(weight);
//            info.married = bMarried;
            info.update_time = DateUtil.getNowDateTime("yyyy-MM-dd HH:mm:ss");

            // 执行数据库帮助器的插入操作
            mHelper.insert(info);
            showToast("注册成功");

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }


    // 定义编辑框的文本变化监听器
    private class HideTextWatcher implements TextWatcher {
        private EditText mView;
        private int mMaxLength;
        private CharSequence mStr;

        HideTextWatcher(EditText v) {
            super();
            mView = v;
            mMaxLength = ViewUtil.getMaxLength(v);
        }
        // 在编辑框的输入文本变化前触发
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        // 在编辑框的输入文本变化时触发
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            mStr = s;
        }
        // 在编辑框的输入文本变化后触发
        public void afterTextChanged(Editable s) {
            if (mStr == null || mStr.length() == 0)
                return;
            // 手机号码输入达到11位，或者密码/验证码输入达到6位，都关闭输入法软键盘
            if ((mStr.length() == 11 && mMaxLength == 11) ||
                    (mStr.length() == 8 && mMaxLength == 8)) {
                ViewUtil.hideOneInputMethod(RegisterWriteActivity.this, mView);
            }
        }
    }
    private void showToast(String desc) {
        Toast.makeText(this, desc, Toast.LENGTH_SHORT).show();
    }
}