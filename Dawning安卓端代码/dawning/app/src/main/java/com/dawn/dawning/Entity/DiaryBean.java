package com.dawn.dawning.Entity;

/**
 * Created by 李 on 2017/1/26.
 */
public class DiaryBean {
    private String user_id;
    private String date;
    private String title;
    private String content;
    private String tag;
    private String mood;


    public DiaryBean(String date, String title, String content, String tag , String mood) {
        this.date = date;
        this.title = title;
        this.content = content;
        this.tag = tag;
        this.mood = mood;
        this.user_id = "1";
    }
   public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMood(){
        return mood;
    }

    public void setMood(String mood){
        this.mood = mood;
    }

    public String getUser_id(){
        return user_id;
    }

    public void setUser_id(String user_id){
        this.user_id = user_id;
    }

}
