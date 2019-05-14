package com.dawn.dawning.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.dawn.dawning.R;
import com.dawn.dawning.common.ToastUtil;

public class setup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        Switch swMessage = (Switch)findViewById(R.id.switchMessagePush);
        TextView tvSkin = (TextView)findViewById(R.id.setupPersonalizedSkin);
        TextView tvCleanCache = (TextView)findViewById(R.id.setupCleanCache);
        TextView tvAboutUs = (TextView)findViewById(R.id.setupAboutUs);
        TextView tvChangeAccount = (TextView)findViewById(R.id.setupChangeAccount);

        tvSkin.setClickable(true);
        tvCleanCache.setClickable(true);
        tvAboutUs.setClickable(true);
        tvChangeAccount.setClickable(true);

        swMessage.setOnCheckedChangeListener(messagePushClickListener);
        tvSkin.setOnClickListener(personalSkinClickListener);
        tvCleanCache.setOnClickListener(cleanCacheClickListener);
        tvAboutUs.setOnClickListener(aboutUsClickListener);
        tvChangeAccount.setOnClickListener(changeAccountClickListener);

    }
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }
    final CompoundButton.OnCheckedChangeListener messagePushClickListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            ToastUtil toastUtil=new ToastUtil();

            if(isChecked) {
                toastUtil.Short(setup.this, "已开启消息通知")
                        .show();
            }else{
                toastUtil.Short(setup.this, "已关闭消息通知")
                        .show();
            }
        }
    };

    final View.OnClickListener personalSkinClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ToastUtil toastUtil=new ToastUtil();
            toastUtil.Short(setup.this,"还没有空做了，等一等吧")
                    .show();
        }
    };

    final View.OnClickListener cleanCacheClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ToastUtil toastUtil=new ToastUtil();
            toastUtil.Short(setup.this,"意念缓存已清空")
                    .show();
        }
    };

    final View.OnClickListener aboutUsClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ToastUtil toastUtil=new ToastUtil();
            toastUtil.Short(setup.this,"别看了，我们是一个传说")
                    .show();
        }
    };

    final View.OnClickListener changeAccountClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ToastUtil toastUtil=new ToastUtil();
            toastUtil.Short(setup.this,"这个还不想做了，再说了")
                    .show();
        }
    };



}
