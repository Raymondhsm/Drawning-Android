package com.dawn.dawning.diaryh;

import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.util.Base64;
import com.dawn.dawning.db.TabledatabaseHelper;
import com.dawn.dawning.Entity.Music;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class Findmusic {


    private TabledatabaseHelper dbHelper;
    private Boolean Exist = false;

    public List<Music> getmusics(ContentResolver contentResolver) {

        Cursor cursor = contentResolver.query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
        List<Music> musics = new ArrayList<Music>();
        for (int i = 0; i < cursor.getCount(); i++) {
            Music music = new Music();     //新建一个歌曲对象,将从cursor里读出的信息存放进去,直到取完cursor里面的内容为止.
            cursor.moveToNext();
            String title = cursor.getString((cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)));//音乐标题
            String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));//艺术家
            long duration = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));//时长
            String url = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));	//文件路径
            String album = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM)); //唱片图片
            if(album==null){album="ohfohfosfolfjajppaofpof";}
            Bitmap album2 = stringToBitmap(album);//把string转换成bitmap
            long album_id = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)); //唱片图片ID
            int isMusic = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.IS_MUSIC));//是否为音乐
            if (isMusic != 0 && duration/(1000 * 60) >= 1) {		//只把1分钟以上的音乐添加到集合当中
                music.setTitle(title);
                music.setArtist(artist);
                music.setDuration(duration);
                music.setUrl(url);
                music.setAlbum(album2);
                musics.add(music);
            }

        }
        cursor.close();
        return musics;
    }

    public static Bitmap stringToBitmap(String string) {
        Bitmap bitmap = null;
        try {
            byte[] bitmapArray = Base64.decode(string, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }


    public static String bitmapToString(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] imgBytes = baos.toByteArray();// 转为byte数组
        return Base64.encodeToString(imgBytes, Base64.DEFAULT);
    }

    /*public void setListAdpter(Context context, List<Music> musics, ListView mMusicList) {
        List<HashMap<String, String>> mp3list = new ArrayList<HashMap<String, String>>();
        MusicAdapter mAdapter = new MusicAdapter(context, musics);
        mMusicList.setAdapter(mAdapter);
    }*/

}

