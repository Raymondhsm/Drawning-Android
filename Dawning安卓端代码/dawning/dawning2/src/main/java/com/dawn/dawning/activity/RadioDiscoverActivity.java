package com.dawn.dawning.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.dawn.dawning.Entity.Radio;
import com.dawn.dawning.R;
import com.dawn.dawning.adapter.RadioAdapter;
import com.dawn.dawning.util.HttpUtil;
import com.dawn.dawning.widget.ProgramDetailDecoration;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class RadioDiscoverActivity extends AppCompatActivity {
    RecyclerView mRecyclerView;
    RadioAdapter mRecyclerAdapter;
    List<Radio> mRadioList;
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio_discover);

//        StatusBarUtil.addAppBarPaddingTop(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();//返回
            }
        });
        //设置电台
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView_radio_discover);
        //设置布局管理器，控制布局效果
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(RadioDiscoverActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        initRadio();
    }

    //节目推荐
    public void initRadio(){
        HttpUtil.sendOkHttpGetRequest(HttpUtil.RADIO_DISCOVER,null,new okhttp3.Callback(){
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("response","failed");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();
                Gson gson = new Gson();
                Log.i("responseData",responseData);
                mRadioList = gson.fromJson(responseData,new TypeToken<List<Radio>>(){}.getType());
                //转换回主线程操作UI
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mRecyclerAdapter = new RadioAdapter(RadioDiscoverActivity.this,mRadioList);
                        mRecyclerView.setAdapter(mRecyclerAdapter);
                        mRecyclerView.addItemDecoration(new ProgramDetailDecoration(RadioDiscoverActivity.this));
                    }
                });
            }
        });
    }
}
