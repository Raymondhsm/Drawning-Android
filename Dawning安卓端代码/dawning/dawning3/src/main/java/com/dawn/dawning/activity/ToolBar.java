package com.dawn.dawning.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toolbar;

import com.dawn.dawning.R;

public class ToolBar extends AppCompatActivity{

    //android.support.v7.widget.AppCompatImageView user_icon = (android.support.v7.widget.AppCompatImageView)findViewById(R.id.user_icon);
    android.support.v7.widget.AppCompatImageView radio_icon = (android.support.v7.widget.AppCompatImageView)findViewById(R.id.radio_icon);
    android.support.v7.widget.AppCompatImageView article_icon = (android.support.v7.widget.AppCompatImageView)findViewById(R.id.artical_icon);
    android.support.v7.widget.AppCompatImageView diary_icon = (android.support.v7.widget.AppCompatImageView)findViewById(R.id.diary_icon);
    android.support.v7.widget.AppCompatImageView music_icon = (android.support.v7.widget.AppCompatImageView)findViewById(R.id.music_icon);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_radio_home);

        /*user_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
*/
        radio_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ToolBar.this,RadioHomeActivity.class);
            }
        });

        article_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(ToolBar.this,);
            }
        });

        music_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ToolBar.this,ThinkHomeActivity.class);
            }
        });

        diary_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ToolBar.this,DiaryHomeActivity.class);
            }
        });


    }
}
