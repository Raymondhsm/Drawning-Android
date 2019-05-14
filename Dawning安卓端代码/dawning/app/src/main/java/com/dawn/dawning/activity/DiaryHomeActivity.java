package com.dawn.dawning.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.dawn.dawning.R;
import com.dawn.dawning.Entity.DiaryBean;
import com.dawn.dawning.db.DiaryDatabaseHelper;
import com.dawn.dawning.diaryh.StartUpdateDiaryEvent;
import com.dawn.dawning.diaryh.AppManager;
import com.dawn.dawning.diaryh.GetDate;
import com.dawn.dawning.util.HttpUtil;
import com.dawn.dawning.diaryh.SpHelper;
import com.dawn.dawning.diaryh.StatusBarCompat;
import com.dawn.dawning.adapter.DiaryAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Response;

public class  DiaryHomeActivity extends AppCompatActivity {

    @Bind(R.id.common_iv_back)
    ImageView mCommonIvBack;
    @Bind(R.id.common_tv_title)
    TextView mCommonTvTitle;
    @Bind(R.id.common_iv_test)
    ImageView mCommonIvTest;
    @Bind(R.id.common_title_ll)
    LinearLayout mCommonTitleLl;
    @Bind(R.id.main_iv_circle)
    ImageView mMainIvCircle;
    @Bind(R.id.main_tv_date)
    TextView mMainTvDate;
    @Bind(R.id.main_tv_content)
    TextView mMainTvContent;
    @Bind(R.id.item_ll_control)
    LinearLayout mItemLlControl;

    @Bind(R.id.main_rv_show_diary)
    RecyclerView mMainRvShowDiary;
    @Bind(R.id.main_fab_enter_edit)
    FloatingActionButton mMainFabEnterEdit;
    @Bind(R.id.main_rl_main)
    RelativeLayout mMainRlMain;
    @Bind(R.id.item_first)
    LinearLayout mItemFirst;
    @Bind(R.id.main_ll_main)
    LinearLayout mMainLlMain;
    private List<DiaryBean> mDiaryBeanList;
    //SQLlite
    private DiaryDatabaseHelper mHelper;

    private static String IS_WRITE = "true";

    private int mEditPosition = -1;

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    /**
     * 标识今天是否已经写了日记
     */
    private boolean isWrite = false;
    private static TextView mTvTest;

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, DiaryHomeActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //将当前Activity添加到栈中
        AppManager.getAppManager().addActivity(this);
        //视图绑定
        ButterKnife.bind(this);
        //设置状态栏颜色
        StatusBarCompat.compat(this, Color.parseColor("#161414"));
        //数据库
        mHelper = new DiaryDatabaseHelper(this, "Diary.db", null, 1);

        //
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        EventBus.getDefault().register(this);
        SpHelper spHelper = SpHelper.getInstance(this);
        getDiaryBeanList();
        initTitle();
        //给日记列表设置布局管理
        mMainRvShowDiary.setLayoutManager(new LinearLayoutManager(this));
        //给布局管理设置Adapter
        mMainRvShowDiary.setAdapter(new DiaryAdapter(this, mDiaryBeanList));
        mTvTest = new TextView(this);
        mTvTest.setText("hello world");
    }

    private void initTitle() {
        mMainTvDate.setText("今天，" + GetDate.getDate());
        mCommonTvTitle.setText("日记");
        mCommonIvBack.setVisibility(View.VISIBLE);
        mCommonIvTest.setVisibility(View.INVISIBLE);

    }

    private List<DiaryBean> getDiaryBeanList() {

        mDiaryBeanList = new ArrayList<>();
        final List<DiaryBean> diaryList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = mHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query("Diary", null, null, null, null, null, null);
        int a = cursor.getCount();
        if (!cursor.moveToFirst()) {
            HttpUtil.sendOkHttpGetRequest(HttpUtil.DIARY_HOME, null, new okhttp3.Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.i("response", "failed");
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String responseData = response.body().string();
                    Gson gson = new Gson();
                    Log.i("responseData", responseData);
                    mDiaryBeanList = gson.fromJson(responseData, new TypeToken<List<DiaryBean>>() {
                    }.getType());
                    //转换回主线程操作
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            int b = mDiaryBeanList.size();
                        }
                    });
                }
            });
        }


        //检查“今天”有没有写日记，如果写了去掉mItemFirst
        if (cursor.moveToFirst()) {
            do {
                String date = cursor.getString(cursor.getColumnIndex("date"));
                String dateSystem = GetDate.getDate().toString();
                if (date.equals(dateSystem)) {
                    mMainLlMain.removeView(mItemFirst);
                    break;
                }
            } while (cursor.moveToNext());
        }

        //将数据库内容加载到List中
        if (cursor.moveToFirst()) {
            do {
                String date = cursor.getString(cursor.getColumnIndex("date"));
                String title = cursor.getString(cursor.getColumnIndex("title"));
                String content = cursor.getString(cursor.getColumnIndex("content"));
                String tag = cursor.getString(cursor.getColumnIndex("tag"));
                String mood = cursor.getString(cursor.getColumnIndex("mood"));
                mDiaryBeanList.add(new DiaryBean(date, title, content, tag, mood));
            } while (cursor.moveToNext());
        }
        cursor.close();
        //最新的日记在最上方
        for (int i = mDiaryBeanList.size() - 1; i >= 0; i--) {
            diaryList.add(mDiaryBeanList.get(i));
        }

        mDiaryBeanList = diaryList;
        return mDiaryBeanList;
    }

    @Subscribe
    public void startUpdateDiaryActivity(StartUpdateDiaryEvent event) {
        String title = mDiaryBeanList.get(event.getPosition()).getTitle();
        String content = mDiaryBeanList.get(event.getPosition()).getContent();
        String tag = mDiaryBeanList.get(event.getPosition()).getTag();
        String mood = mDiaryBeanList.get(event.getPosition()).getMood();
        UpdateDiaryActivity.startActivity(this, title, content, tag, mood);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @OnClick(R.id.main_fab_enter_edit)
    public void onClick() {
        AddDiaryActivity.startActivity(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        AppManager.getAppManager().AppExit(this);
    }
}