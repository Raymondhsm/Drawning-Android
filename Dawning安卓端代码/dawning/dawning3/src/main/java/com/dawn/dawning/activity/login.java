package com.dawn.dawning.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.Button;
import android.view.View.OnClickListener;

import com.dawn.dawning.R;
public class login extends AppCompatActivity {



  //  Button btnLogin = (Button)findViewById(R.id.btnLogin);




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        Button btnLogin = findViewById(R.id.btnLogin);
//        btnLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(login.this, RadioHomeActivity.class);
//            }
//        });

        btnLogin.setOnClickListener(new OnClickListener() {

                     @Override
             public void onClick(View v) {

                                 // 给bnt1添加点击响应事件
                                Intent intent =new Intent(login.this,ThinkHomeActivity.class);
                                //启动
                                 startActivity(intent);
                            }
        });

    }
}
