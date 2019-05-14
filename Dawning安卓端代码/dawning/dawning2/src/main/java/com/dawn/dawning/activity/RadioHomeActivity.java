package com.dawn.dawning.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.dawn.dawning.Entity.Program;
import com.dawn.dawning.R;
import com.dawn.dawning.adapter.ProgramRecommendedAdapter;
import com.dawn.dawning.util.HttpUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jude.rollviewpager.OnItemClickListener;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.LoopPagerAdapter;
import com.jude.rollviewpager.hintview.ColorPointHintView;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class RadioHomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    RollPagerView mRollBanner;
    RecyclerView mRecyclerView;
    ProgramRecommendedAdapter mRecyclerAdapter;
    List<Program> mProgramList;
    String[] mBannerLinks;

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio_home);

//        StatusBarUtil.addAppBarPaddingTop(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //toolBar的logo监听,跳转到相应主页
        ImageView radioLogo = (ImageView) findViewById(R.id.toolbar_radio);
        ImageView articleLogo = (ImageView) findViewById(R.id.toolbar_article);
        ImageView meditationLogo = (ImageView) findViewById(R.id.toolbar_meditation);
        ImageView diaryLogo = (ImageView) findViewById(R.id.toolbar_diary);
        radioLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("RadioHomeActivity","nav to radioHome");
            }
        });
        articleLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        meditationLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        diaryLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        ImageView program_category_logo = (ImageView)findViewById(R.id.ic_program_classification);
        program_category_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent  intent = new Intent(RadioHomeActivity.this,ProgramCategoryActivity.class);
                startActivity(intent);
            }
        });

        ImageView radio_discover_logo = (ImageView)findViewById(R.id.ic_artist_radio);
        radio_discover_logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent  intent = new Intent(RadioHomeActivity.this,RadioDiscoverActivity.class);
                startActivity(intent);
            }
        });

        initBanner();
        initProgramRecommended();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.radio_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_my_mix) {
            // Handle the camera action
        } else if (id == R.id.nav_my_favorite) {

        } else if (id == R.id.nav_meditation_record) {

        } else if (id == R.id.nav_mood_curve) {

        } else if (id == R.id.nav_my_article) {

        } else if (id == R.id.nav_my_program) {

        } else if (id == R.id.nav_my_music){

        } else if(id ==R.id.nav_download_manage){

        } else if(id == R.id.action_settings){

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //
    private class ImageLoopAdapter extends LoopPagerAdapter {
        String[] imgLinks;

        public ImageLoopAdapter(RollPagerView viewPager,String [] imgLinks) {
            super(viewPager);
            this.imgLinks=imgLinks;
        }

        @Override
        public View getView(ViewGroup container, int position) {
            ImageView view = new ImageView(container.getContext());
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            Glide.with(RadioHomeActivity.this).load(HttpUtil.resourceAddress+imgLinks[position]).into(view);
            return view;
        }

        @Override
        public int getRealCount() {
            return imgLinks.length;
        }
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        mRollBanner.resume();
    }

    @Override
    protected void onStop(){
        super.onStop();
        mRollBanner.pause();
    }

    //Banner初始化
    public void initBanner() {
        HttpUtil.sendOkHttpGetRequest(HttpUtil.RADIO_HOME_BANNER,null,new
                okhttp3.Callback(){
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("response","failed");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();
                Gson gson = new Gson();
                Log.i("responseData",responseData);
                mBannerLinks = gson.fromJson(responseData,String[].class);
                //转换回主线程操作UI
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //图片轮播
                        mRollBanner = findViewById(R.id.radio_home_roll_banner);
                        mRollBanner.setAdapter(new ImageLoopAdapter(mRollBanner,mBannerLinks));
                        mRollBanner.setHintView(new ColorPointHintView(RadioHomeActivity.this, Color.YELLOW,Color.WHITE));
                        mRollBanner.setOnItemClickListener(new OnItemClickListener() {
                            @Override
                            public void onItemClick(int position) {
                                Toast.makeText(RadioHomeActivity.this,""+position,Toast.LENGTH_SHORT).show();
                            }
                        });
                        //mRollBanner.setPlayDelay(1000);
                        mRollBanner.resume();
                    }
                });
            }
        });
    }

    //节目推荐
    public void initProgramRecommended(){
        HttpUtil.sendOkHttpGetRequest(HttpUtil.PROGRAM_DAILY_RECOMMENDED,null,new okhttp3.Callback(){
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
                        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView_program);
                        mRecyclerAdapter = new ProgramRecommendedAdapter(RadioHomeActivity.this,mProgramList);
                        //设置布局管理器，控制布局效果
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(RadioHomeActivity.this);
                        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                        mRecyclerView.setLayoutManager(linearLayoutManager);
                        mRecyclerView.setAdapter(mRecyclerAdapter);
                    }
                });
            }
        });
    }

    public void testHttp(){
        //                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        HttpURLConnection connection = null;
//                        BufferedReader reader =null;
//                        try {
//                            URL url = new URL(HttpUtil.address);
//                            connection = (HttpURLConnection) url.openConnection();
//                            InputStream inputStream = connection.getInputStream();
//                            reader = new BufferedReader(new InputStreamReader(inputStream));
//                            StringBuilder response = new StringBuilder();
//                            String line;
//                            while((line = reader.readLine())!=null){
//                                response.append(line);
//                            }
//                            Log.i("response",response.toString());
//                        }
//                        catch (Exception e){
//                            Log.i("httphahah","fail");
//                            e.printStackTrace();
//                        }
//                        finally {
//                            if (reader !=null){
//                                try{
//                                    reader.close();
//                                }catch (IOException e){
//                                    e.printStackTrace();
//                                }
//                            }
//                            if(connection!=null){
//                                connection.disconnect();
//                            }
//                        }
//                    }
//                }).start();
    }
}
