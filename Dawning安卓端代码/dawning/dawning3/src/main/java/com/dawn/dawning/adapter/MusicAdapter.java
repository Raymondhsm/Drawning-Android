package com.dawn.dawning.adapter;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;
import com.dawn.dawning.Entity.Music;
import com.dawn.dawning.R;
import com.dawn.dawning.db.TabledatabaseHelper;
import java.util.List;


public class  MusicAdapter extends ArrayAdapter<Music> {
    private Context context;
    private List<Music> musics;
    private Music music;


    private TabledatabaseHelper dbHelper;
    private Boolean Exist = false;

    public MusicAdapter(Context context,int textViewResourceId,List<Music> musics){
        super(context,textViewResourceId, musics);
        this.context = context;
        this.musics = musics;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        music = musics.get(position);
        View view = LayoutInflater.from(context).inflate(R.layout.musicitem, null);
        ImageView musicimage = (ImageView)view.findViewById(R.id.imageView);
        ImageView musicimage2 = (ImageView)view.findViewById(R.id.imageView2);
        TextView songname = (TextView)view.findViewById(R.id.songname);
        TextView singer = (TextView)view.findViewById(R.id.singer);
        musicimage.setImageResource(R.drawable.black);
        musicimage2.setImageResource(R.drawable.pic1);
        songname.setText(music.getTitle());         //显示标题
        singer.setText(music.getArtist());       //显示艺术家
        //musicimage2.setImageBitmap(music.getAlbum());//显示封面图片

        return view;
    }














}



