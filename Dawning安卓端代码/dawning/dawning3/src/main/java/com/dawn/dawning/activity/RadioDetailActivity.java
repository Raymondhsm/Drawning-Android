package com.dawn.dawning.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dawn.dawning.Entity.ProgramDetail;
import com.dawn.dawning.R;
import com.dawn.dawning.adapter.ProgramDetailAdapter;
import com.dawn.dawning.util.HttpUtil;
import com.dawn.dawning.widget.AppBarLayoutOverScrollViewBehavior;
import com.dawn.dawning.widget.ProgramDetailDecoration;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class RadioDetailActivity extends AppCompatActivity {
    AppBarLayout mAppBarLayout;
    Toolbar mToolbar;
//    CollapsingToolbarLayout mCollapsingToolbarLayout;
    ConstraintLayout mConstraintLayout;
    ImageView imageView;
    RecyclerView mRecyclerView;
    ProgramDetailAdapter mRecyclerAdapter;
    List<ProgramDetail> mProgramDetailList;
    JsonObject mRadioInfo;
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio_detail);
        //设置状态栏透明度
//        initTransparentStatusBar();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar = toolbar;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();//返回
            }
        });

        mAppBarLayout= (AppBarLayout)findViewById(R.id.appBarLayout);
//        mCollapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.collapsing_layout);
        mConstraintLayout = (ConstraintLayout)findViewById(R.id.appBar_constrainLayout);
        imageView = (ImageView)findViewById(R.id.collapsing_layout_image);

        //设置下拉图片放大的行为
        CoordinatorLayout.LayoutParams params = new CoordinatorLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setBehavior(new AppBarLayoutOverScrollViewBehavior());
        mAppBarLayout.setLayoutParams(params);

        mRecyclerView = (RecyclerView) findViewById(R.id.radio_detail_recyclerView);
        //设置布局管理器，控制布局效果
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(RadioDetailActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);


        //设置丄滑时透明度跟随变化
//        mAppBarLayout.setNestedScrollingEnabled(true);
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
//                mCollapsingToolbarLayout.setContentScrim(R.drawable.banner1);
//                changeAlpha(getResources().getColor(R.color.colorPrimary),Math.abs(verticalOffset*1.0f)/appBarLayout.getTotalScrollRange())
                float fraction = Math.abs(verticalOffset*1.0f)/appBarLayout.getTotalScrollRange();
                imageView.setAlpha(1-0.5f*fraction);
                mConstraintLayout.setAlpha(1.0f-fraction);
                if(fraction==1)
                    mToolbar.setTitle(mRadioInfo.get("radio_name").getAsString());
                else mToolbar.setTitle(R.string.title_activity_radio_detail);
            }
        });

        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_radio_detail,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.share:
                break;
                default:
        }
        return true;
    }

//    public int changeAlpha(int color, float fraction) {
//        int red = Color.red(color);
//        int green = Color.green(color);
//        int blue = Color.blue(color);
//        int alpha = (int) (Color.alpha(color) * fraction);
//        return Color.argb(alpha, red, green, blue);
//    }

    public void initTransparentStatusBar(){
        //5.0版本以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    public void init(){
        String radioId = getIntent().getIntExtra("radio_id",-1)+"";
        Log.i("radioId",radioId);
        HttpUtil.sendOkHttpGetRequest(HttpUtil.RADIO_DETAIL+radioId,null,new okhttp3.Callback(){
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("response","failed");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();
                Gson gson =  new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
                JsonObject dataObj =  new JsonParser().parse(responseData).getAsJsonObject();
                mRadioInfo = dataObj.getAsJsonObject("radio_info");
                JsonArray programs = dataObj.getAsJsonArray("programs");
                mProgramDetailList = gson.fromJson(programs.toString(), new TypeToken<List<ProgramDetail>>() {}.getType());

                //转换回主线程操作UI
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //初始化电台信息
                        ImageView radio_cover = (ImageView) findViewById(R.id.collapsing_layout_image);
                        TextView radioName = (TextView) findViewById(R.id.collapsing_layout_radio_name);
                        radioName.setText(mRadioInfo.get("radio_name").getAsString());
                        String cover_link = mRadioInfo.get("cover_link").getAsString();
                        Glide.with(RadioDetailActivity.this).load(HttpUtil.resourceAddress+cover_link).into(radio_cover);
                        mRecyclerAdapter = new ProgramDetailAdapter(RadioDetailActivity.this,mProgramDetailList);
                        //初始化节目信息
                        mRecyclerView.setAdapter(mRecyclerAdapter);
                        mRecyclerView.addItemDecoration(new ProgramDetailDecoration(RadioDetailActivity.this));
                    }
                });
            }
        });

    }

}
