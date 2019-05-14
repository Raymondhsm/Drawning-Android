package com.dawn.dawning.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.dawn.dawning.Entity.Program;
import com.dawn.dawning.Entity.Radio;
import com.dawn.dawning.R;
import com.dawn.dawning.adapter.ProgramDetailAdapter;
import com.dawn.dawning.adapter.ProgramRecommendedAdapter;
import com.dawn.dawning.adapter.RadioAdapter;
import com.dawn.dawning.util.HttpUtil;
import com.dawn.dawning.widget.ProgramDetailDecoration;
import com.dawn.dawning.widget.StatusBarUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class ProgramCategoryActivity extends AppCompatActivity {
    RecyclerView mRecyclerView;
    ProgramRecommendedAdapter mRecyclerAdapter;
    String currentCategory;
    List<Program> mProgramList;
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_category);
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


//        StatusBarUtil.addAppBarPaddingTop(this);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView_program_category);
        //设置布局管理器，控制布局效果
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ProgramCategoryActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);


        //设置spinner
        String[] items = getResources().getStringArray(R.array.program_category_spinner);
        currentCategory = items[0];
        ArrayAdapter<String> categoryAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, items);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        final Spinner spinner = (Spinner) findViewById(R.id.spinner_category);
        spinner .setAdapter(categoryAdapter);
        spinner.setSelection(0);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                //因为一进来Spinner就会自动赋值然后调用此方法，然而此时Adapter还没创建，会报错。
                if(mRecyclerAdapter==null)
                    return;
                String[] categories = getResources().getStringArray(R.array.program_category_spinner);
                Toast.makeText(ProgramCategoryActivity.this, "你点击的是:"+categories[pos], Toast.LENGTH_SHORT).show();
                currentCategory = spinner.getSelectedItem().toString();
                HttpUtil.sendOkHttpGetRequest(HttpUtil.PROGRAM_CATEGORY,"category="+currentCategory,
                        new okhttp3.Callback(){
                            @Override
                            public void onFailure(Call call, IOException e) {
                                Log.i("response","failed");
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {
                                String responseData = response.body().string();
                                Gson gson = new Gson();
                                List<Program> temp = gson.fromJson(responseData,new TypeToken<List<Program>>(){}.getType());
                                //注意更新数据的用法
                                mProgramList.clear();
                                mProgramList.addAll(temp);
                                //转换回主线程操作UI
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mRecyclerAdapter.notifyDataSetChanged();
                                    }
                                });
                            }
                        });
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });
        //
        initData();
    }

    public void initData(){
        HttpUtil.sendOkHttpGetRequest(HttpUtil.PROGRAM_CATEGORY,"category="+currentCategory,
                new okhttp3.Callback(){
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("response","failed");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();
                Gson gson = new Gson();
                Log.i("responseData",responseData);
                mProgramList = gson.fromJson(responseData,new TypeToken<List<Program>>(){}.getType());
                //转换回主线程操作UI
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mRecyclerAdapter = new ProgramRecommendedAdapter(ProgramCategoryActivity.this,mProgramList);
                        mRecyclerView.setAdapter(mRecyclerAdapter);
                    }
                });
            }
        });
    }


}
