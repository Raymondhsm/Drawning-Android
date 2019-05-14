package com.dawn.dawning.activity;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dawn.dawning.R;
import com.dawn.dawning.diaryh.MusicService;
import com.dawn.dawning.diaryh.Findmusic;
import com.dawn.dawning.Entity.Music;
import com.dawn.dawning.db.TabledatabaseHelper;
import com.dawn.dawning.adapter.MusicAdapter;


import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ThinkHomeActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String TAG="ThinkHomeActivity";
    private String response;

    private int count;

    TextView selected;
    Button playlist;
    Button play;
    Button playnext;
    Button playlast;
    ListView listView;

    private TextView textView2;
    private TextView textView;
    private MusicService musicService;
    private TabledatabaseHelper dbHelper;
    private String CurrentTitle = "CurrentTitle";
    GestureDetector mGestureDetector;
    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            musicService = ((MusicService.MyBinder) service).getService();
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    };

    private List<Music> musics;
    private Boolean Exist = false;
    MusicAdapter adapter;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,};

    int rlTopShareHeight;
    int rlButtomShareHeight;
    public RelativeLayout top_select;
    public RelativeLayout play_list;
    public ObjectAnimator topPullAnimation;
    public ObjectAnimator topUpAnimation;
    public ObjectAnimator bottomUpAnimation;
    public ObjectAnimator bottomPullAnimation;

    public static Bitmap getImage(String path){
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(path).openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestMethod("GET");
            System.out.println("tdw1");
            if(conn.getResponseCode() == 200){
                InputStream inputStream = conn.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                return bitmap;
            }
        } catch (Exception e) {			e.printStackTrace();		}
        return null;

    }

    private void initView() {

        //主体歌单内容
        ImageView pic1 = (ImageView)findViewById(R.id.pic1);
        ImageView pic2 = (ImageView)findViewById(R.id.pic2);
        ImageView pic3 = (ImageView)findViewById(R.id.pic3);
        ImageView pic4 = (ImageView)findViewById(R.id.pic4);
        ImageView pic5 = (ImageView)findViewById(R.id.pic5);
        ImageView pic6 = (ImageView)findViewById(R.id.pic6);
        ImageView pic7 = (ImageView)findViewById(R.id.pic7);

        //pic1.setImageBitmap(getImage("http://192.168.137.1/cover/a.png"));

        //播放栏内容
        selected = (TextView)findViewById(R.id.selected);
        textView2 = (TextView)findViewById(R.id.textView2);
        textView = (TextView)findViewById(R.id.textView);
        play = (Button)findViewById(R.id.play);
        playnext = (Button) findViewById(R.id.playnext);
        playlast = (Button) findViewById(R.id.playlast);
        playlist = (Button) findViewById(R.id.playlist);
        Intent intent = new Intent(this, MusicService.class);
        bindService(intent, conn, Context.BIND_AUTO_CREATE);

        //playlist内容
        Button ibtnBottomClose = (Button) findViewById(R.id.playlistclose);
        play_list = (RelativeLayout) findViewById(R.id.play_list);

        Button topclose = (Button) findViewById(R.id.topclose);
        top_select = (RelativeLayout)findViewById(R.id.top_select);
        //解决在onCreate中不能获取高度的问题
         top_select.post(new Runnable() {
            @Override
            public void run() {
                rlTopShareHeight = top_select.getHeight();
                initAnimation();
            }
        });

        play_list.post(new Runnable() {
            @Override
            public void run() {
                rlButtomShareHeight = play_list.getHeight();
                initAnimation();
            }
        });

        //播放栏内容
        playlast.setOnClickListener(this);
        playnext.setOnClickListener(this);
        play.setOnClickListener(this);
        playlist.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                adapter.notifyDataSetChanged();//更新列表
                if(!bottomUpAnimation.isRunning()) {
                    bottomUpAnimation.start();
                }
            }
        });
        bindService(intent, conn, Context.BIND_AUTO_CREATE);

        IntentFilter filter = new IntentFilter();
        filter.addAction("gettitle");
        filter.addAction("pauseimage");
        filter.addAction("playimage");
        filter.addAction("nextsong");
        registerReceiver(broadcastReceiver, filter);

        dbHelper = new TabledatabaseHelper(this,"login.db",null,1);

        mGestureDetector = new GestureDetector(this,new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                if((e1.getRawX() - e2.getRawX()) >200){

                    return true;
                }
                if((e2.getRawX() - e1.getRawX()) >200){
                    Intent intent = new Intent(ThinkHomeActivity.this,ThinkHomeActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.animator.lefttodleft,R.animator.righttoleft);
                    return true;
                }
                return super.onFling(e1, e2, velocityX, velocityY);
            }
        });

        //playlist内容
        dbHelper = new TabledatabaseHelper(this,"login.db",null,1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.query("login",null,null,null,null,null,null);
        musics = new ArrayList<Music>();
        musics.clear();
        count = cursor.getCount();
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToNext();
            String title = cursor.getString(cursor.getColumnIndex("title"));
            String artist = cursor.getString(cursor.getColumnIndex("artist"));
            String url = cursor.getString(cursor.getColumnIndex("url"));
            Music music = new Music();
            music.setTitle(title);
            music.setArtist(artist);
            music.setUrl(url);
            musics.add(music);
            Log.e("huizhong", "music adds succeedly");
        }
        cursor.close();

        Button button = (Button)findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                musicService.start();
            }
        });

        Button button6 = (Button)findViewById(R.id.button6);
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.delete("login",null,null);
                Log.e("huizhong","count = "+count);
                for (int i = 1; i <= count; i++) {
                    musics.remove(0);
                }
                adapter.notifyDataSetChanged();//更新列表
            }
        });


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_EXTERNAL_STORAGE);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_EXTERNAL_STORAGE);
        }

        adapter = new MusicAdapter(ThinkHomeActivity.this, R.layout.musicitem, musics); //新建想对应的适配器
        // adapter = new ArrayAdapter<String>(playlist.this,android.R.layout.simple_list_item_1,list);     //用字符串适配器试验
        listView = (ListView) findViewById(R.id.listView2);
        listView.setAdapter(adapter);

        this.registerForContextMenu(listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Music music = musics.get(position);
                String url = music.getUrl();
                String title = music.getTitle();
                String artist = music.getArtist();

                Intent intent = new Intent("startnew");
                intent.putExtra("url", url);
                intent.putExtra("title", title);
                intent.putExtra("artist", artist);

                final Intent eintent = new Intent(createExplicitFromImplicitIntent(ThinkHomeActivity.this, intent));
                bindService(eintent, conn, Service.BIND_AUTO_CREATE);
                startService(eintent);
            }
        });


    }

    /*初始化Animation*/
    private void initAnimation() {
        /*顶部动画*/
        //打开动画
        topPullAnimation = ObjectAnimator.ofFloat(
                top_select,"translationY",rlTopShareHeight);
        topPullAnimation.setDuration(1000);
        topPullAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        //关闭动画
        topUpAnimation = ObjectAnimator.ofFloat(
                top_select,"translationY",-rlTopShareHeight);
        topUpAnimation.setDuration(500);
        topUpAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        topUpAnimation.start();

        /*底部动画*/
        //打开动画
        bottomUpAnimation = ObjectAnimator.ofFloat(
                play_list, "translationY", -rlButtomShareHeight);
        topUpAnimation.setDuration(500);
        topUpAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        topUpAnimation.start();
        //关闭动画
        bottomPullAnimation = ObjectAnimator.ofFloat(
                play_list, "translationY", rlButtomShareHeight);
        topPullAnimation.setDuration(1000);
        topPullAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();//初始页面

    }

    /*单击事件*/
    public void onClick(View v) {

        switch(v.getId()) {
            /*case R.id.pic1:
                Intent intent10 = new Intent(MainActivity.this,play_page.class);
                startActivity(intent10);
                break;
            case R.id.pic2:
                Intent intent11 = new Intent(MainActivity.this,play_page.class);
                startActivity(intent11);
                break;
            case R.id.pic3:
                Intent intent12 = new Intent(MainActivity.this,play_page.class);
                startActivity(intent12);
                break;
            case R.id.pic4:
                Intent intent13 = new Intent(MainActivity.this,play_page.class);
                startActivity(intent13);
                break;
            case R.id.pic5:
                Intent intent14 = new Intent(MainActivity.this,play_page.class);
                startActivity(intent14);
                break;
            case R.id.pic6:
                Intent intent15 = new Intent(MainActivity.this,play_page.class);
                startActivity(intent15);
                break;
            case R.id.pic7:
                Intent intent16 = new Intent(MainActivity.this,play_page.class);
                startActivity(intent16);
                break;*/
            case R.id.pic8:
                Intent intent17= new Intent(ThinkHomeActivity.this,LocalMusicActivity.class);
                startActivity(intent17);
                //finish();//关闭主页
                break;
            case R.id.btn1:
                if(!topUpAnimation.isRunning()) {
                    topUpAnimation.start();
                }
                selected.setText("自我成长");
                break;
            case R.id.btn2:
                if(!topUpAnimation.isRunning()) {
                    topUpAnimation.start();
                }
                selected.setText("情绪管理");
                break;
            case R.id.btn3:
                if(!topUpAnimation.isRunning()) {
                    topUpAnimation.start();
                }
                selected.setText("压力调节");
                break;
            case R.id.btn21:
                if(!topUpAnimation.isRunning()) {
                    topUpAnimation.start();
                }
                selected.setText("人际沟通");
                break;
            case R.id.btn22:
                if(!topUpAnimation.isRunning()) {
                    topUpAnimation.start();
                }
                selected.setText("情感故事");
                break;
            case R.id.btn23:
                if(!topUpAnimation.isRunning()) {
                    topUpAnimation.start();
                }
                selected.setText("奋斗故事");
                break;
            case R.id.btn31:
                if(!topUpAnimation.isRunning()) {
                    topUpAnimation.start();
                }
                selected.setText("职场管理");
                break;
            case R.id.btn32:
                if(!topUpAnimation.isRunning()) {
                    topUpAnimation.start();
                }
                selected.setText("亲子家庭");
                break;
            case R.id.btn33:
                if(!topUpAnimation.isRunning()) {
                    topUpAnimation.start();
                }
                selected.setText("心理科普");
                break;
            case R.id.topclose:
                //click close btn
                if(!topUpAnimation.isRunning()) {
                    topUpAnimation.start();
                }
                selected.setText("全部");
                break;
            case R.id.topshow:
                //click share btn
                if(!topPullAnimation.isRunning()) {
                    topPullAnimation.start();
                }
                break;
            case R.id.play:
                musicService.start();
                break;
            case R.id.playnext:
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                Cursor cursor = db.query("login",null,null,null,null,null,null);
                cursor.moveToFirst();
                do{
                    Log.e("huizhong","CurrentTitle = "+CurrentTitle);  //播放列表不会有同名歌曲，所以根据标题对比
                    if(CurrentTitle.equals(cursor.getString(cursor.getColumnIndex("title")))) {
                        Log.e("huizhong","找到匹配");
                        cursor.moveToNext();
                        if(cursor.isAfterLast()) {
                            Log.e("huizhong","当前歌曲在最后一行返回第一行");
                            cursor.moveToFirst();
                            String url = cursor.getString(cursor.getColumnIndex("url"));
                            String title = cursor.getString(cursor.getColumnIndex("title"));
                            String artist = cursor.getString(cursor.getColumnIndex("artist"));
                            Intent intent2 = new Intent("startnew");
                            intent2.putExtra("url",url);
                            intent2.putExtra("title",title);
                            intent2.putExtra("artist",artist);
                            final Intent eiiintent = new Intent(createExplicitFromImplicitIntent(ThinkHomeActivity.this,intent2));
                            bindService(eiiintent,conn, Service.BIND_AUTO_CREATE);
                            startService(eiiintent);
                            break;
                        }   else{
                            Log.e("huizhong","当前歌曲不是在最后一行");
                            String url = cursor.getString(cursor.getColumnIndex("url"));
                            String title = cursor.getString(cursor.getColumnIndex("title"));
                            String artist = cursor.getString(cursor.getColumnIndex("artist"));
                            cursor.moveToLast();
                            Intent intent2 = new Intent("startnew");
                            intent2.putExtra("url",url);
                            intent2.putExtra("title",title);
                            intent2.putExtra("artist",artist);
                            final Intent eiintent = new Intent(createExplicitFromImplicitIntent(ThinkHomeActivity.this,intent2));
                            bindService(eiintent,conn, Service.BIND_AUTO_CREATE);
                            startService(eiintent);
                            break;
                        }
                    }
                }while(cursor.moveToNext());
                cursor.close();
                break;
            case R.id.playlast:
                SQLiteDatabase dbb = dbHelper.getWritableDatabase();
                Cursor cursorr = dbb.query("login",null,null,null,null,null,null);
                cursorr.moveToFirst();
                do{
                    if(CurrentTitle.equals(cursorr.getString(cursorr.getColumnIndex("title")))) {

                        cursorr.moveToPrevious();
                        if(cursorr.isBeforeFirst()){
                            cursorr.moveToLast();
                            String url = cursorr.getString(cursorr.getColumnIndex("url"));
                            String title = cursorr.getString(cursorr.getColumnIndex("title"));
                            String artist = cursorr.getString(cursorr.getColumnIndex("artist"));
                            Intent intent8 = new Intent("startnew");
                            intent8.putExtra("url",url);
                            intent8.putExtra("title",title);
                            intent8.putExtra("artist",artist);
                            final Intent eeiintent = new Intent(createExplicitFromImplicitIntent(ThinkHomeActivity.this,intent8));
                            bindService(eeiintent,conn, Service.BIND_AUTO_CREATE);
                            startService(eeiintent);
                            break;
                        }
                        else{
                            String url = cursorr.getString(cursorr.getColumnIndex("url"));
                            String title = cursorr.getString(cursorr.getColumnIndex("title"));
                            String artist = cursorr.getString(cursorr.getColumnIndex("artist"));
                            cursorr.moveToNext();
                            Intent intent8 = new Intent("startnew");
                            intent8.putExtra("url",url);
                            intent8.putExtra("title",title);
                            intent8.putExtra("artist",artist);
                            final Intent eeiintent = new Intent(createExplicitFromImplicitIntent(ThinkHomeActivity.this,intent8));
                            bindService(eeiintent,conn, Service.BIND_AUTO_CREATE);
                            startService(eeiintent);
                            break;
                        }

                    }
                }while(cursorr.moveToNext());
                cursorr.close();
                break;
            /*case R.id.playlist:
                //click close btn
                MainActivity.adapter.notifyDataSetChanged();//更新列表
                if(!bottomUpAnimation.isRunning()) {
                    bottomUpAnimation.start();
                }
                break;*/
            case R.id.playlistclose:
                //click close btn
                if(!bottomPullAnimation.isRunning()) {
                    bottomPullAnimation.start();
                }
                break;
            default:
                break;
        }
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("seekbarmaxprogress")) {

            } else if (intent.getAction().equals("seekbarprogress")) {

            } else if (intent.getAction().equals("pauseimage")) {
                play.setBackgroundResource(R.drawable.pause);
            } else if (intent.getAction().equals("playimage")) {
                play.setBackgroundResource(R.drawable.play);
            } else if (intent.getAction().equals("gettitle")) {
                CurrentTitle = intent.getStringExtra("title");
                Log.e("huizhong","CurrentTitle = "+CurrentTitle);
                textView2.setText(intent.getStringExtra("artist"));
                textView.setText(intent.getStringExtra("title"));}
            else if (intent.getAction().equals("nextsong")) {
                Log.e("huizhong","歌曲播放结束，接收到广播，发送下一首歌曲");
                SQLiteDatabase dbb = dbHelper.getWritableDatabase();
                Cursor cursorr = dbb.query("login",null,null,null,null,null,null);
                cursorr.moveToFirst();
                do{
                    Log.e("huizhong","CurrentTitle = "+CurrentTitle);  //播放列表不会有同名歌曲，所以根据标题对比
                    if(CurrentTitle.equals(cursorr.getString(cursorr.getColumnIndex("title")))) {
                        Log.e("huizhong","找到匹配");
                        cursorr.moveToNext();
                        if(cursorr.isAfterLast()) {
                            Log.e("huizhong","当前歌曲在最后一行返回第一行");
                            cursorr.moveToFirst();
                            String url = cursorr.getString(cursorr.getColumnIndex("url"));
                            String title = cursorr.getString(cursorr.getColumnIndex("title"));
                            String artist = cursorr.getString(cursorr.getColumnIndex("artist"));
                            Intent intent2 = new Intent("startnew");
                            intent2.putExtra("url",url);
                            intent2.putExtra("title",title);
                            intent2.putExtra("artist",artist);
                            final Intent eiiintent = new Intent(createExplicitFromImplicitIntent(ThinkHomeActivity.this,intent2));
                            bindService(eiiintent,conn, Service.BIND_AUTO_CREATE);
                            startService(eiiintent);
                            break;
                        }   else {
                            Log.e("huizhong", "当前歌曲不是在最后一行");
                            String url = cursorr.getString(cursorr.getColumnIndex("url"));
                            String title = cursorr.getString(cursorr.getColumnIndex("title"));
                            String artist = cursorr.getString(cursorr.getColumnIndex("artist"));
                            cursorr.moveToLast();
                            Intent intent2 = new Intent("startnew");
                            intent2.putExtra("url", url);
                            intent2.putExtra("title", title);
                            intent2.putExtra("artist", artist);
                            final Intent eiintent = new Intent(createExplicitFromImplicitIntent(ThinkHomeActivity.this, intent2));
                            bindService(eiintent, conn, Service.BIND_AUTO_CREATE);
                            startService(eiintent);
                            break;
                        }
                    }
                }while(cursorr.moveToNext());
                cursorr.close();
            }
        }
    };
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(conn);
        unregisterReceiver(broadcastReceiver);
    }
    public static Intent createExplicitFromImplicitIntent(Context context, Intent implicitIntent) {
        // Retrieve all services that can match the given intent
        PackageManager pm = context.getPackageManager();
        List<ResolveInfo> resolveInfo = pm.queryIntentServices(implicitIntent, 0);
        // Make sure only one match was found
        if (resolveInfo == null || resolveInfo.size() != 1) {
            return null;
        }
        // Get component info and create ComponentName
        ResolveInfo serviceInfo = resolveInfo.get(0);
        String packageName = serviceInfo.serviceInfo.packageName;
        String className = serviceInfo.serviceInfo.name;
        ComponentName component = new ComponentName(packageName, className);
        // Create a new intent. Use the old one for extras and such reuse
        Intent explicitIntent = new Intent(implicitIntent);
        // Set the component to be explicit
        explicitIntent.setComponent(component);
        return explicitIntent;
    }

    public void onCreateContextMenu(ContextMenu menu, View view, ContextMenu.ContextMenuInfo menuInfo){
        menu.add(0,1,0,"删除");
    }
    public boolean onContextItemSelected(MenuItem item){
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        switch(item.getItemId()){
            case 1:
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                String title = ((TextView)menuInfo.targetView.findViewById(R.id.songname)).getText().toString();

                db.delete("login", "title =?", new String[]{title+""});
                Log.e("huizhong","删除SQL项成功" );
                musics.remove(menuInfo.position);
                adapter.notifyDataSetChanged();
                break;
        }
        return true;
    }

}