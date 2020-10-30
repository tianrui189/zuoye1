package com.example.tx.ui;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tx.R;
import com.example.tx.Util.ViewUtil;

// 主activity
public class MainActivity extends AppCompatActivity {

    private Switch sw_ios; // 声明一个开关按钮对象
    private TextView tv_ios_result; // 声明一个文本视图对象



    private RadioGroup rg_login; // 声明一个单选组对象
    private RadioButton rb_password; // 声明一个单选按钮对象
    private RadioButton rb_verifycode; // 声明一个单选按钮对象
    private EditText et_phone; // 声明一个编辑框对象
    private TextView tv_password; // 声明一个文本视图对象
    private EditText et_password; // 声明一个编辑框对象
    private Button btn_forget; // 声明一个忘记密码按钮控件对象
    private Button btn_login; // 声明一个登录按钮控件对象
//    private CheckBox ck_remember; // 声明一个复选框对象
    private Button btn_reg; // 声明一个登录按钮控件对象

    private int mRequestCode = 0; // 跳转页面时的请求代码
    private int mType = 2; // 用户类型
    private boolean bRemember = false; // 是否记住密码
    private String mPassword = "18990230"; // 默认密码
    private String mVerifyCode; // 验证码
    private SharedPreferences mShared;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initTypeSpinner();


        rg_login = findViewById(R.id.rg_login);
        rb_password = findViewById(R.id.rb_password);
        rb_verifycode = findViewById(R.id.rb_verifycode);
        et_phone = findViewById(R.id.et_phone);
        tv_password = findViewById(R.id.tv_password);
        et_password = findViewById(R.id.et_password);
        btn_forget = findViewById(R.id.btn_forget);
        sw_ios = findViewById(R.id.sw_ios);
        tv_ios_result = findViewById(R.id.tv_ios_result);
        btn_login = findViewById(R.id.btn_login);
        btn_reg = findViewById(R.id.btn_regist);


        // 给rg_login设置单选监听器
        rg_login.setOnCheckedChangeListener(new RadioListener());
        // 给et_phone添加文本变更监听器
        et_phone.addTextChangedListener(new HideTextWatcher(et_phone));
        // 给et_password添加文本变更监听器
        et_password.addTextChangedListener(new HideTextWatcher(et_password));
        sw_ios.setOnCheckedChangeListener(new CheckListener());
        refreshResult(sw_ios);
        //从share_login.xml中获取共享参数对象
        mShared = getSharedPreferences("share_login", MODE_PRIVATE);
        // 获取共享参数中保存的手机号码
        String phone = mShared.getString("phone", "");
        // 获取共享参数中保存的密码
        String password = mShared.getString("password", "");
        et_phone.setText(phone); // 给手机号码编辑框填写上次保存的手机号
        et_password.setText(password); // 给密码编辑框填写上次保存的密码
        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegisterWriteActivity.class);
                startActivity(intent);

            }
        });
        btn_forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = et_phone.getText().toString();

                if (phone.length() < 11) { // 手机号码不足11位
                    Toast.makeText(MainActivity.this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (rb_password.isChecked()) { // 选择了密码方式校验，此时要跳到找回密码页面
                    Intent intent = new Intent(MainActivity.this, LoginForgetActivity.class);
                    // 携带手机号码跳转到找回密码页面
                    intent.putExtra("phone", phone);
                    startActivityForResult(intent, mRequestCode);
                } else if (rb_verifycode.isChecked()) { // 选择了验证码方式校验，此时要生成六位随机数字验证码
                    // 生成六位随机数字的验证码,结果用0填充
                    mVerifyCode = String.format("%06d", (int) ((Math.random() * 9 + 1) * 100000));
                    // 弹出提醒对话框，提示用户六位验证码数字
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("请记住验证码");
                    builder.setMessage("手机号" + phone + "，本次验证码是" + mVerifyCode + "，请输入验证码");
                    builder.setPositiveButton("好的", null);
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            }
        });
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = et_phone.getText().toString();

                if (phone.length() < 11) { // 手机号码不足11位
                    Toast.makeText(MainActivity.this, "请输入正确的手机号", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (rb_password.isChecked()) { // 密码方式校验
                    //输入的密码跟mPassword比较
                    if (!et_password.getText().toString().equals(mPassword)) {
                        Toast.makeText(MainActivity.this, "请输入正确的密码", Toast.LENGTH_SHORT).show();
                    } else { // 密码校验通过
                        loginSuccess(); // 提示用户登录成功
                    }
                } else if (rb_verifycode.isChecked()) { // 验证码方式校验
                    if (!et_password.getText().toString().equals(mVerifyCode)) {
                        Toast.makeText(MainActivity.this, "请输入正确的验证码", Toast.LENGTH_SHORT).show();
                    } else { // 验证码校验通过
                        loginSuccess(); // 提示用户登录成功
                    }
                }
            }
        });
    }


    private String[] typeArray = {"个人用户", "公司用户", "18990278-田蕊"};
    // 初始化下拉框
    private void initTypeSpinner() {
        // 声明一个下拉列表的数组适配器
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(this, R.layout.item_select, typeArray);
        // 设置数组适配器的布局样式
        typeAdapter.setDropDownViewResource(R.layout.item_dropdown);
        // 从布局文件中获取名叫sp_dropdown的下拉框
        Spinner sp_type = findViewById(R.id.sp_type);
        // 设置下拉框的标题
        sp_type.setPrompt("请选择用户类型");
        // 设置下拉框的数组适配器
        sp_type.setAdapter(typeAdapter);
        // 设置下拉框默认显示第一项

        sp_type.setSelection(mType);
        // 给下拉框设置选择监听器，一旦用户选中某一项，就触发监听器的onItemSelected方法
        sp_type.setOnItemSelectedListener(new TypeSelectedListener());
    }

    class TypeSelectedListener implements AdapterView.OnItemSelectedListener {
        /* 选择事件的处理方法
        adapter:适配器
        view:视图
        position:第几项
        id:id
        */
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            mType = arg2;
            //获取选择的项的值


        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }


    private class RadioListener implements RadioGroup.OnCheckedChangeListener {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (checkedId == R.id.rb_password) { // 选择了密码登录
                tv_password.setText("登录密码：");
                et_password.setHint("请输入密码");
                btn_forget.setText("忘记密码");
//                sw_ios.setVisibility(View.VISIBLE);

            } else if (checkedId == R.id.rb_verifycode) { // 选择了验证码登录
                tv_password.setText("　验证码：");
                et_password.setHint("请输入验证码");
                btn_forget.setText("获取验证码");
//                sw_ios.setVisibility(View.INVISIBLE);

            }
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
                ViewUtil.hideOneInputMethod(MainActivity.this, mView);
            }
        }
    }
    // 忘记密码修改后，从后一个页面携带参数返回当前页面时触发
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == mRequestCode && data != null) {
            // 用户密码已改为新密码，故更新密码变量
            mPassword = data.getStringExtra("new_password");
        }
    }

    // 从修改密码页面返回登录页面，要清空密码的输入框



    @Override
    protected void onRestart() {
        et_password.setText("");
        super.onRestart();
    }

    private void refreshResult(CompoundButton buttonView) {
        String result = String.format(
                (buttonView.isChecked()) ? "开" : "关");
        if (buttonView.getId() == R.id.sw_ios) {
            tv_ios_result.setText(result);
        }

    }

    // 选择事件的处理方法
    private class CheckListener implements CompoundButton.OnCheckedChangeListener {
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (buttonView.getId() == R.id.sw_ios) {
                bRemember = isChecked;
                refreshResult(buttonView);
            }
        }
    }
    // 定义是否记住密码的勾选监听器
//    private class CheckListener implements CompoundButton.OnCheckedChangeListener {
//        @Override
//        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//            if (buttonView.getId() == R.id.sw_ios) {
//                bRemember = isChecked;
//            }
//        }
//    }
    // 校验通过，登录成功
    private void loginSuccess() {


        if (bRemember) {
            //把手机号码和密码都保存到共享参数中
            SharedPreferences.Editor editor = mShared.edit(); // 获得编辑器的对象
            editor.putString("phone", et_phone.getText().toString()); // 添加名叫phone的手机号码
            editor.putString("password", et_password.getText().toString()); // 添加名叫password的密码
            editor.commit(); // 提交编辑器中的修改
        }

        String desc = String.format("您的手机号码是%s，类型是%s。恭喜你通过登录验证，点击“确定”按钮返回上个页面",
                et_phone.getText().toString(), typeArray[mType]);
        // 弹出提醒对话框，提示用户登录成功
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("登录成功");
        builder.setMessage(desc);
        builder.setPositiveButton("确定返回", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton("我再看看", null);
        AlertDialog alert = builder.create();
        alert.show();
    }
}