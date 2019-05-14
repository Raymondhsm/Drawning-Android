package com.dawn.dawning.Entity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.widget.ImageView;

public class Music {
    private int id;
    private long album_id;
    private String title;
    private String artist;
    private long duration;
    private String url;
    private Bitmap album;

    public void setId(int id){
        this.id = id;
    }
    public long getId(){return this.id;}
    public void setTitle(String title){
        this.title = title;
    }
    public String getTitle(){return this.title;}
    public void setArtist(String artist){
        this.artist = artist;
    }
    public String getArtist(){return this.artist;}
    public void setDuration(long duration){this.duration = duration;}
    public long getDuration(){return this.duration;}
    public void setUrl(String url){this.url = url;}
    public String getUrl(){return this.url;}
    public void setAlbum_id(long album_id){this.album_id = album_id;}
    public long getAlbum_id(){return this.album_id;}
    public void setAlbum(Bitmap album){this.album = album;}
    public Bitmap getAlbum(){return this.album;}

}

