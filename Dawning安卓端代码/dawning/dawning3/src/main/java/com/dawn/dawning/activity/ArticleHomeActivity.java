package com.dawn.dawning.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dawn.dawning.R;
import com.dawn.dawning.adapter.MyNoteListAdapter;
import com.dawn.dawning.Entity.Note;
import com.dawn.dawning.db.NoteDao;
import com.dawn.dawning.ui.NewActivity;
import com.dawn.dawning.ui.NoteActivity;
import com.dawn.dawning.util.HttpUtil;
import com.dawn.dawning.widget.SpacesItemDecoration;
import com.google.gson.Gson;
import com.jude.rollviewpager.OnItemClickListener;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.LoopPagerAdapter;
import com.jude.rollviewpager.hintview.ColorPointHintView;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class ArticleHomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    RollPagerView mRollBanner;
    RecyclerView mRecyclerView;
    String[] mBannerLinks;

    private static final String TAG = "ArticleHomeActivity";
    private RecyclerView rv_list_main;
    private MyNoteListAdapter mNoteListAdapter;
    private List<Note> noteList;
    private NoteDao noteDao;
    private int groupId;//分类ID
    private String groupName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_home);

//        StatusBarUtil.addAppBarPaddingTop(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ArticleHomeActivity.this, NewActivity.class);
                intent.putExtra("groupName", groupName);
                intent.putExtra("flag", 0);
                startActivity(intent);
            }
        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //toolBar的logo监听,跳转到相应主页
        ImageView radioLogo = (ImageView) findViewById(R.id.radio_icon);
        ImageView articleLogo = (ImageView) findViewById(R.id.artical_icon);
        ImageView meditationLogo = (ImageView) findViewById(R.id.music_icon);
        ImageView diaryLogo = (ImageView) findViewById(R.id.diary_icon);
        radioLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("ArticleHomeActivity","nav to radioHome");
            }
        });
        radioLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(ArticleHomeActivity.this,RadioHomeActivity.class);
                //启动
                startActivity(intent);
            }
        });
        meditationLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(ArticleHomeActivity.this,ThinkHomeActivity.class);
                //启动
                startActivity(intent);
            }
        });
        diaryLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(ArticleHomeActivity.this,DiaryHomeActivity.class);
                //启动
                startActivity(intent);
            }
        });

//        ImageView program_category_logo = (ImageView)findViewById(R.id.ic_radio_classification);
//        program_category_logo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent  intent = new Intent(ArticleHomeActivity.this,ProgramCategoryActivity.class);
//                startActivity(intent);
//            }
//        });


        initBanner();
        initView();
        refreshNoteList();

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
            Intent intent =new Intent(ArticleHomeActivity.this,ProgramMixActivity.class);
            //启动
            startActivity(intent);


        } else if (id == R.id.nav_meditation_record) {
            Intent intent =new Intent(ArticleHomeActivity.this,meditationReport.class);
            //启动
            startActivity(intent);

        } else if (id == R.id.nav_mood_curve) {
            Intent intent =new Intent(ArticleHomeActivity.this,moodReport.class);
            //启动
            startActivity(intent);


        } else if(id == R.id.action_settings){
            Intent intent =new Intent(ArticleHomeActivity.this,setup.class);
            //启动
            startActivity(intent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //
    private class ImageLoopAdapter extends LoopPagerAdapter {
        String[] imgLinks;

        public ImageLoopAdapter(RollPagerView viewPager, String [] imgLinks) {
            super(viewPager);
            this.imgLinks=imgLinks;
        }

        @Override
        public View getView(ViewGroup container, int position) {
            ImageView view = new ImageView(container.getContext());
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            Glide.with(ArticleHomeActivity.this).load(HttpUtil.resourceAddress+imgLinks[position]).into(view);
            return view;
        }

        @Override
        public int getRealCount() {
            return imgLinks.length;
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        refreshNoteList();
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

    public void initBanner() {
        HttpUtil.sendOkHttpGetRequest(HttpUtil.RADIO_HOME_BANNER,null,new okhttp3.Callback(){
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
                        mRollBanner = findViewById(R.id.article_home_roll_banner);
                        mRollBanner.setAdapter(new ImageLoopAdapter(mRollBanner,mBannerLinks));
                        mRollBanner.setHintView(new ColorPointHintView(ArticleHomeActivity.this, Color.YELLOW,Color.WHITE));
                        mRollBanner.setOnItemClickListener(new OnItemClickListener() {
                            @Override
                            public void onItemClick(int position) {
                                Toast.makeText(ArticleHomeActivity.this,""+position,Toast.LENGTH_SHORT).show();
                            }
                        });
                        //mRollBanner.setPlayDelay(1000);
                        mRollBanner.resume();
                    }
                });
            }
        });
    }


    public void initArticleRecommended()
    {
        HttpUtil.sendOkHttpGetRequest(HttpUtil.ARTICLE_REACOMMENDED,null,new okhttp3.Callback(){
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("response","failed");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();
                //转换回主线程操作UI
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }
        });
    }

    //节目推荐
//    public void initProgramRecommended(){
//        HttpUtil.sendOkHttpGetRequest(HttpUtil.PROGRAM_DAILY_RECOMMENDED,null,new okhttp3.Callback(){
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Log.i("response","failed");
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                String responseData = response.body().string();
//                Gson gson = new Gson();
//                Log.i("responseData",responseData);
//                mProgramList = gson.fromJson(responseData,new TypeToken<List<Program>>(){}.getType());
//                //转换回主线程操作UI
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView_article);
//                        mRecyclerAdapter = new ProgramRecommendedAdapter(ArticleHomeActivity.this,mProgramList);
//                        //设置布局管理器，控制布局效果
//                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ArticleHomeActivity.this);
//                        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//                        mRecyclerView.setLayoutManager(linearLayoutManager);
//                        mRecyclerView.setAdapter(mRecyclerAdapter);
//                    }
//                });
//            }
//        });
//    }

    private void initView() {
        noteDao = new NoteDao(this);

        rv_list_main = (RecyclerView) findViewById(R.id.rv_list_main);
        rv_list_main.addItemDecoration(new SpacesItemDecoration(0));//设置item间距
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);//竖向列表
        rv_list_main.setLayoutManager(layoutManager);

        mNoteListAdapter = new MyNoteListAdapter();
        mNoteListAdapter.setmNotes(noteList);
        rv_list_main.setAdapter(mNoteListAdapter);

        mNoteListAdapter.setOnItemClickListener(new MyNoteListAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, Note note) {
                //showToast(note.getTitle());

                Intent intent = new Intent(ArticleHomeActivity.this, NoteActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("note", note);
                intent.putExtra("data", bundle);
                startActivity(intent);
            }
        });
    }

    //刷新笔记列表
    private void refreshNoteList(){
        if (noteDao == null)
            noteDao = new NoteDao(this);
        noteList = noteDao.queryNotesAll(groupId);
        mNoteListAdapter.setmNotes(noteList);
        mNoteListAdapter.notifyDataSetChanged();
    }
}
