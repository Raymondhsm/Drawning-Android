package com.dawn.dawning.util;

import android.util.Log;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class HttpUtil {
    public  static  final String emulator_host = "http://10.0.2.2:80/";
    public  static  final String real_phone_host = "http://192.168.137.1/";
    public static final String serverAddress = real_phone_host+"dawning/public/";
    public static final String resourceAddress = real_phone_host+"dawning/public/res/";
    /*
    电台模块接口
    */
    //电台主页
    public static final String PROGRAM_DAILY_RECOMMENDED = serverAddress+"getProgramDailyRecommended";
    public static final String RADIO_HOME_BANNER = serverAddress+"getRadioHomeBanners";
    //主播电台
    public static final String RADIO_DISCOVER = serverAddress + "getRadios";
    //节目分类
    public static final String PROGRAM_CATEGORY = serverAddress + "getProgramsByCategory";
    //电台详情
    public static final String RADIO_DETAIL = serverAddress + "getRadioAndPrograms/";
    //Diary
    public static final String DIARY_HOME = serverAddress+"index.php/diary/diary/getDiary";


    //HSM
    public static final String MY_RADIO = serverAddress + "myRadio";
    public static final String PROGRAM_MIX = serverAddress + "programMix";

   //

    //文章接口
    public static final String ARTICLE_REACOMMENDED = serverAddress + "getArticleRecommended";
    public static final String ARTICLE_DETAIL = serverAddress + "getArticleDetail";
    public static final String PUBLISH_ARTICLE = serverAddress + "publishArticle";
    //GET方法，第一个参数为URL地址，第二个参数为参数组装,第三个参数为回调函数
    public static void sendOkHttpGetRequest(String address, String param, okhttp3.Callback callback){
        String lastURL;
        if(param!=null){
            lastURL = address+"?token=1&"+param;
        }
        else {
            lastURL = address+"?token=1";
        }
        Log.i("access_url",lastURL);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(lastURL)
                .build();
        //提交异步请求
        client.newCall(request).enqueue(callback);
    }

    //POST方法，第一个参数为URL地址，第二个参数为POST请求的参数，第三个参数为回调函数
    public static void sendOkHttpPostRequest(String address, RequestBody requestBody, okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        String lastURL = address+"?token=1";
        Log.i("access_url",lastURL);
        Request request=new Request.Builder()
                .url(lastURL)
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(callback);
    }
}
